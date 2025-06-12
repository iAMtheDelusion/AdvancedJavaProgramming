//////////////////////////////
// module 03 course project //
//                          //
// devin gast               //
// rasmussen university     //
// advanced java programming//
// professor kumar          //
//////////////////////////////

package courseProjects; // this is the package name, has to match your folder in eclipse

import java.util.Scanner; // lets us read input from the user
import java.util.logging.Logger; // for logging stuff like actions and errors

public class OrderSystemMenu { // this is the main class that runs the menu

    private static Logger log = MyLogger.getLogger(); // this grabs our logger from the mylogger class

    private static Scanner scanner = new Scanner(System.in); // scanner lets us get input from the user

    public static void main(String[] args) { // this is where the program actually starts
        log.info("program started"); // log that the program started
        LoginManager.showLoginScreen(); // show the login screen first, handled in another file
    }

    public static void showMainMenu() { // this method shows the main menu
        int choice = 0; // this holds the user’s menu choice, starting at 0

        do { // this loop keeps going until the user exits
            System.out.println("\n=== swiftorders: main menu ==="); // title of the menu
            System.out.println("1. view products"); // first menu option
            System.out.println("2. view cart / place order"); // second option
            System.out.println("3. view order history"); // third option
            System.out.println("4. manage products"); // fourth option, probably for admin
            System.out.println("5. logout"); // goes back to login screen
            System.out.println("6. exit"); // shuts the whole thing down
            System.out.print("choose an option: "); // prompt the user

            if (scanner.hasNextInt()) { // check if they typed a number
                choice = scanner.nextInt(); // grab the number
                scanner.nextLine(); // clear the leftover line from pressing enter
                log.info("user selected option " + choice); // log the choice they picked
            } else { // if they typed something that’s not a number
                System.out.println("please enter a number."); // tell them what went wrong
                scanner.nextLine(); // clear the bad input
                continue; // go back to the top of the menu
            }

            // this switch decides what to do based on the choice
            switch (choice) {
                case 1: // if they chose 1
                    log.info("user selected to view products"); // log it
                    viewProducts(); // run the viewProducts method
                    break;
                case 2: // if they chose 2
                    log.info("user selected to view cart / place order"); // log it
                    viewCartAndPlaceOrder(); // show the cart and let them confirm
                    break;
                case 3: // if they chose 3
                    log.info("user selected to view order history"); // log it
                    viewOrderHistory(); // show them their past orders
                    break;
                case 4: // if they chose 4
                    log.warning("user accessed manage products menu"); // log this as a warning (admin-ish)
                    manageProducts(); // go to product management screen
                    break;
                case 5: // if they chose 5
                    log.info("user logged out"); // log that they logged out
                    return; // stop this method and go back to login
                case 6: // if they chose 6
                    log.info("user exited the program"); // log they’re done
                    System.out.println("exiting program. goodbye!"); // tell them bye
                    System.exit(0); // shut the whole program down
                default: // if they typed something weird
                    log.warning("invalid menu choice: " + choice); // log that it was invalid
                    System.out.println("invalid option. try again."); // let them know
            }

        } while (choice != 6); // loop keeps going until they type 6 to exit
    }

    // just a fake method for now, will eventually pull from the product database
    public static void viewProducts() {
        System.out.println("\n[viewing products... (stub)]"); // placeholder message
    }

    // fake method for viewing cart and confirming an order
    public static void viewCartAndPlaceOrder() {
        System.out.println("\n[viewing cart / placing order... (stub)]"); // placeholder
    }

    // fake method for showing past orders
    public static void viewOrderHistory() {
        System.out.println("\n[viewing order history... (stub)]"); // placeholder
    }

    // fake method for managing the product list
    public static void manageProducts() {
        System.out.println("\n[managing products... (stub)]"); // placeholder
    }
}
