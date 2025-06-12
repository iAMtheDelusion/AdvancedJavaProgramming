//////////////////////////////
// Module 03 Course Project //
//                          //
// devin gast               //
// rasmussen university     //
// advanced java programming//
// professor kumar          //
//////////////////////////////

package courseProjects;

import java.util.Scanner;
import java.util.logging.Logger;

public class OrderSystemMenu {

    // logger setup from our mylogger class
    private static Logger log = MyLogger.getLogger();

    // scanner to get user input
    private static Scanner scanner = new Scanner(System.in);

    // program starts here
    public static void main(String[] args) {
        log.info("program started");
        
        // instead of showing login stuff here, we let loginmanager handle it
        LoginManager.showLoginScreen();
    }

    // this is the main menu after login
    public static void showMainMenu() {
        int choice = 0; // default menu option

        // keep showing the menu until they pick "exit"
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
                scanner.nextLine(); // clear leftover input
                log.info("user selected option " + choice);
            } else {
                System.out.println("please enter a number.");
                scanner.nextLine(); // clear bad input
                continue;
            }

            // do something based on their menu choice
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
                    return; // send them back to login screen
                case 6:
                    log.info("user exited the program");
                    System.out.println("exiting program. goodbye!");
                    System.exit(0); // shut down
                default:
                    log.warning("invalid menu choice: " + choice);
                    System.out.println("invalid option. try again.");
            }

        } while (choice != 6);
    }

    // --- placeholder menu actions ---

    public static void viewProducts() {
        System.out.println("\n[viewing products... (stub)]");
    }

    public static void viewCartAndPlaceOrder() {
        System.out.println("\n[viewing cart / placing order... (stub)]");
    }

    public static void viewOrderHistory() {
        System.out.println("\n[viewing order history... (stub)]");
    }

    public static void manageProducts() {
        System.out.println("\n[managing products... (stub)]");
    }
}
