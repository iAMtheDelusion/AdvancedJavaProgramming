//////////////////////////////
// Module 05 Course Project //
//                          //
// Devin Gast               //
// Rasmussen University     //
// Advanced Java Programming//
// Professor Kumar          //
//////////////////////////////

package courseProjects;

import java.util.Scanner; // lets us get user input
import java.util.logging.Logger; // used for logging stuff
import java.util.ArrayList; // lets us make lists
import java.util.Arrays; // helps us quickly load the product list
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class OrderSystemMenu {

    private static Logger log = MyLogger.getLogger(); // grabs logger from mylogger

    private static Scanner scanner = new Scanner(System.in); // scanner to read user input
    
 // this stores the product ids the user added
    static ArrayList<Integer> cart = new ArrayList<>();

    // this is set after successful login
    public static int loggedInUserId = -1;


    // fake list of available products
    static ArrayList<String> products = new ArrayList<>(Arrays.asList(
        "laptop - $999", "keyboard - $49", "mouse - $25", "monitor - $199"
    ));

    // saved order history
    static ArrayList<String> orderHistory = new ArrayList<>();

    public static void main(String[] args) {
        log.info("program started"); // log that the app started
        LoginManager.showLoginScreen(); // take user to login screen first
    }

    // this is the menu the user sees after login
    public static void showMainMenu() {
        int choice = 0; // user’s menu pick

        do {
            System.out.println("\n=== swiftorders: main menu ===");
            System.out.println("1. view products");
            System.out.println("2. view cart / place order");
            System.out.println("3. view order history");
            System.out.println("4. manage products");
            System.out.println("5. logout");
            System.out.println("6. exit");
            System.out.print("choose an option: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt(); // get their number input
                scanner.nextLine(); // clean up the leftover input
                log.info("user selected option " + choice); // log it
            } else {
                System.out.println("please enter a number."); // not a number? tell them
                scanner.nextLine(); // clear bad input
                continue; // skip to top
            }

            switch (choice) {
                case 1:
                    log.info("user selected to view products");
                    viewProducts(); // show product list
                    break;
                case 2:
                    log.info("user selected to view cart / place order");
                    viewCartAndPlaceOrder(); // let them order
                    break;
                case 3:
                    log.info("user selected to view order history");
                    viewOrderHistory(); // show past orders
                    break;
                case 4:
                    log.warning("user accessed manage products menu");
                    manageProducts(); // placeholder for admin stuff
                    break;
                case 5:
                    log.info("user logged out");
                    return; // go back to login
                case 6:
                    log.info("user exited the program");
                    System.out.println("exiting program. goodbye!");
                    System.exit(0); // shut it down
                default:
                    log.warning("invalid menu choice: " + choice);
                    System.out.println("invalid option. try again.");
            }

        } while (choice != 6); // loop ends if they hit 6
    }

    // lets user view products and add one to cart
    public static void viewProducts() {
        System.out.println("\n=== available products ===");

        try {
            // connect to the database
            Connection conn = DBConnection.getConnection();

            // get all products that still have stock
            String sql = "SELECT * FROM products WHERE quantity > 0";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            // this keeps track of product ids we show
            ArrayList<Integer> productIds = new ArrayList<>();

            int optionNumber = 1; // just used for display

            // go through each product
            while (result.next()) {
                int id = result.getInt("product_id");
                String name = result.getString("name");
                String desc = result.getString("description");
                int stock = result.getInt("quantity");
                double price = result.getDouble("price");

                // print out info
                System.out.println(optionNumber + ". " + name + " - $" + price + " (" + stock + " in stock)");
                System.out.println("    " + desc); // print description on second line

                productIds.add(id); // remember which one this is
                optionNumber++;
            }

            // if no products showed up
            if (productIds.size() == 0) {
                System.out.println("no products available.");
                return;
            }

            // ask the user what they want to do
            System.out.print("type the number to add to cart, or 0 to go back: ");
            int pick = scanner.nextInt();
            scanner.nextLine(); // clear the leftover input

            if (pick > 0 && pick <= productIds.size()) {
                int chosenId = productIds.get(pick - 1); // find the real product id
                cart.add(chosenId); // save that id into the cart
                log.info("added to cart: product_id = " + chosenId);
                System.out.println("product added to cart.");
            } else if (pick == 0) {
                System.out.println("going back to menu...");
            } else {
                System.out.println("not a valid option.");
            }

            conn.close(); // always close the database

        } catch (SQLException e) {
            System.out.println("something went wrong while showing products.");
            e.printStackTrace();
        }
    }


    // shows what's in the cart and places the order
    public static void viewCartAndPlaceOrder() {
        // first check if the cart is empty
        if (cart.isEmpty()) {
            System.out.println("\nyour cart is empty.");
            return;
        }

        System.out.println("\n=== your cart ===");

        try {
            Connection conn = DBConnection.getConnection();

            // here, we are going to display the product names from the ids in the cart
            for (int id : cart) {
                String sql = "SELECT name FROM products WHERE product_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);

                ResultSet result = stmt.executeQuery();

                if (result.next()) {
                    System.out.println("- " + result.getString("name"));
                }
            }

            // ask the user if they want to place the order
            System.out.print("place this order? (yes/no): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("yes")) {
                // 1. create the order row
                String insertOrder = "INSERT INTO orders (user_id) VALUES (?)";
                PreparedStatement orderStmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
                orderStmt.setInt(1, loggedInUserId);
                orderStmt.executeUpdate();

                // get the order id we just created
                ResultSet keys = orderStmt.getGeneratedKeys();
                keys.next();
                int orderId = keys.getInt(1);

                // 2. loop through cart and create order_items rows + subtract stock
                for (int id : cart) {
                    // insert into order_items
                    String insertItem = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
                    PreparedStatement itemStmt = conn.prepareStatement(insertItem);
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, id);
                    itemStmt.setInt(3, 1); // we assume quantity = 1 for now
                    itemStmt.executeUpdate();

                    // subtract 1 from stock in products table
                    String updateStock = "UPDATE products SET quantity = quantity - 1 WHERE product_id = ?";
                    PreparedStatement stockStmt = conn.prepareStatement(updateStock);
                    stockStmt.setInt(1, id);
                    stockStmt.executeUpdate();
                }

                log.info("order placed by user_id = " + loggedInUserId);
                System.out.println("your order was placed successfully!");

                cart.clear(); // empty the cart after order is done

            } else {
                System.out.println("order canceled.");
            }

            conn.close(); // close it all down

        } catch (SQLException e) {
            System.out.println("something went wrong placing the order.");
            e.printStackTrace();
        }
    }


    // shows all past orders
    public static void viewOrderHistory() {
        System.out.println("\n=== order history ===");

        if (orderHistory.isEmpty()) {
            System.out.println("no orders yet.");
            return;
        }

        for (String order : orderHistory) {
            System.out.println("- " + order); // show each past item
        }

        log.info("order history viewed");
    }

    public static void manageProducts() { // manage the various products
        ProductManager.showProductMenu(); // hand it off to the new class
    }
}
