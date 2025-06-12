//////////////////////////////
// Module 02 Course Project //
//                          //
//Devin Gast                //
//Rasmussen University      //
//Advanced Java Programming //
//Professor Kumar           //
//////////////////////////////

import java.util.Scanner; // need this to get user input

public class OrderSystemMenu {

    public static void main(String[] args) {
        // program starts here. show the main menu.
        showMainMenu();
    }

    public static void showMainMenu() {
        // scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice; // holds the user's menu choice

        // loop to keep showing the menu until the user exits
        do {
            System.out.println("\n=== swiftorders: main menu ===");
            System.out.println("1. view products");
            System.out.println("2. view cart / place order");
            System.out.println("3. view order history");
            System.out.println("4. manage products"); // probably for admins
            System.out.println("5. logout");
            System.out.println("6. exit");
            System.out.print("choose an option: ");

            choice = scanner.nextInt(); // read the number
            scanner.nextLine(); // consume the leftover newline character

            // perform action based on user's choice
            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    viewCartAndPlaceOrder();
                    break;
                case 3:
                    viewOrderHistory();
                    break;
                case 4:
                    manageProducts();
                    break;
                case 5:
                    logout();
                    break;
                case 6:
                    System.out.println("exiting program. goodbye!");
                    break;
                default: // invalid input
                    System.out.println("invalid option. try again.");
            }

        } while (choice != 6); // loop continues until choice is 6 (exit)
    }

    // --- these are just placeholders ---

    public static void viewProducts() {
        System.out.println("\n[viewing products... (stub)]");
        // todo: implement fetching products from a database
    }

    public static void viewCartAndPlaceOrder() {
        System.out.println("\n[viewing cart / placing order... (stub)]");
        // todo: implement showing cart, confirming order, and updating database
    }

    public static void viewOrderHistory() {
        System.out.println("\n[viewing order history... (stub)]");
        // todo: implement fetching order history by user ID
    }

    public static void manageProducts() {
        System.out.println("\n[managing products... (stub)]");
        // todo: implement submenu for add/edit/delete products
    }

    public static void logout() {
        System.out.println("\n[logging out... returning to main menu.]");
        // todo: clear user session when login is added
    }
}
