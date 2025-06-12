//////////////////////////////
// Module 03 Course Project //
//                          //
// Devin Gast               //
// Rasmussen University     //
// Advanced Java Programming//
// Professor Kumar          //
//////////////////////////////

package courseProjects;

import java.util.Scanner;              // to read user input
import java.util.logging.Logger;       // to write logs

public class OrderSystemMenu {

    // get the logger from our MyLogger class
    private static Logger log = MyLogger.getLogger();

    public static void main(String[] args) {
        log.info("program started");
        showMainMenu(); // show the menu when program starts
    }

    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0; // set a default value so Java doesn't complain

        // loop the menu until the user chooses to exit
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
                choice = scanner.nextInt();
                scanner.nextLine(); // clear the leftover newline
                log.info("user selected option " + choice);
            } else {
                System.out.println("invalid input. please enter a number.");
                scanner.nextLine(); // clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    log.info("user selected to view products");
                    viewProducts();
                    break;
                case 2:
                    log.info("user selected to view cart / place order");
                    viewCartAndPlaceOrder();
                    break;
                case 3:
                    log.info("user selected to view order history");
                    viewOrderHistory();
                    break;
                case 4:
                    log.warning("user accessed manage products menu");
                    manageProducts();
                    break;
                case 5:
                    log.info("user logged out");
                    logout();
                    break;
                case 6:
                    log.info("user exited the program");
                    System.out.println("exiting program. goodbye!");
                    break;
                default:
                    log.warning("invalid menu choice entered: " + choice);
                    System.out.println("invalid option. try again.");
            }

        } while (choice != 6);
    }

    // --- stub methods for each menu option ---

    public static void viewProducts() {
        System.out.println("\n[viewing products... (stub)]");
        // todo: pull product list from database
    }

    public static void viewCartAndPlaceOrder() {
        System.out.println("\n[viewing cart / placing order... (stub)]");
        // todo: show cart and allow order confirmation
    }

    public static void viewOrderHistory() {
        System.out.println("\n[viewing order history... (stub)]");
        // todo: query orders by user
    }

    public static void manageProducts() {
        System.out.println("\n[managing products... (stub)]");
        // todo: create submenu for adding, editing, deleting products
    }

    public static void logout() {
        System.out.println("\n[logging out... returning to main menu]");
        // todo: clear user session later
    }
}
