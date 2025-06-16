//////////////////////////////
// Module 04 Course Project //
//                          //
// devin gast               //
// rasmussen university     //
// advanced java programming//
// professor kumar          //
//////////////////////////////

package courseProjects;

import java.util.Scanner; // lets us get user input
import java.util.logging.Logger; // used for logging stuff
import java.util.ArrayList; // lets us make lists
import java.util.Arrays; // helps us quickly load the product list

public class OrderSystemMenu {

    private static Logger log = MyLogger.getLogger(); // grabs logger from mylogger

    private static Scanner scanner = new Scanner(System.in); // scanner to read user input

    // fake list of available products
    static ArrayList<String> products = new ArrayList<>(Arrays.asList(
        "laptop - $999", "keyboard - $49", "mouse - $25", "monitor - $199"
    ));

    // user's shopping cart
    static ArrayList<String> cart = new ArrayList<>();

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

        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i)); // show the list numbered
        }

        System.out.print("type the number to add to cart, or 0 to go back: ");
        int pick = scanner.nextInt();
        scanner.nextLine(); // clean up input

        if (pick > 0 && pick <= products.size()) {
            String item = products.get(pick - 1);
            cart.add(item); // add the product to the cart
            log.info("added to cart: " + item);
            System.out.println(item + " added to your cart!");
        } else if (pick == 0) {
            System.out.println("going back to menu...");
        } else {
            System.out.println("invalid choice.");
        }
    }

    // shows what's in the cart and places the order
    public static void viewCartAndPlaceOrder() {
        if (cart.isEmpty()) {
            System.out.println("\ncart is empty."); // nothing to order
            return;
        }

        System.out.println("\n=== your cart ===");
        for (String item : cart) {
            System.out.println("- " + item); // list each item
        }

        System.out.print("would you like to place this order? (yes/no): ");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            orderHistory.addAll(cart); // save the items to history
            log.info("order placed: " + cart.toString());
            cart.clear(); // empty the cart
            System.out.println("order placed successfully!");
        } else {
            System.out.println("order not placed.");
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

    // placeholder for admin stuff like adding/removing products
    public static void manageProducts() {
        System.out.println("\n[managing products... (stub)]");
        // in the future this could add/edit/delete items from the products list
    }
}
