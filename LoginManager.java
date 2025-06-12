package courseProjects;

import java.util.Scanner;
import java.util.logging.Logger;

public class LoginManager {

    // this is the logger from our mylogger class
    private static Logger log = MyLogger.getLogger();

    // scanner to read what the user types
    private static Scanner input = new Scanner(System.in);

    // this is the first screen the user will see
    public static void showLoginScreen() {
        int choice = 0; // will store the user's menu choice

        // keep showing the login menu until they log in or exit
        do {
            System.out.println("\n=== login screen ===");
            System.out.println("1. register");
            System.out.println("2. login");
            System.out.println("3. exit");
            System.out.print("choose an option: ");

            if (input.hasNextInt()) {
                choice = input.nextInt(); // get the number they typed
                input.nextLine(); // clear leftover line from input
                log.info("login menu choice: " + choice); // log what they chose
            } else {
                System.out.println("please enter a number."); // they typed something weird
                input.nextLine(); // clear the bad input
                continue;
            }

            // now handle what they picked
            if (choice == 1) {
                registerUser(); // go to register screen
            } else if (choice == 2) {
                boolean success = loginUser(); // try to log them in
                if (success) {
                    log.info("login success");
                    OrderSystemMenu.showMainMenu(); // take them to the main menu
                } else {
                    log.warning("login failed");
                    System.out.println("login failed. try again.");
                }
            } else if (choice == 3) {
                log.info("user exited login screen");
                System.out.println("goodbye!");
                System.exit(0); // quit the program
            } else {
                System.out.println("invalid option. try again."); // not 1, 2, or 3
            }

        } while (choice != 3); // keep going unless they chose exit
    }

    // this is a placeholder for registering a new user
    public static void registerUser() {
        System.out.println("\n[registering new user]");
        System.out.print("enter a username: ");
        String username = input.nextLine();

        System.out.print("enter a password: ");
        String password = input.nextLine();

        // normally this would be saved to the database
        log.info("new user registered: " + username);
        System.out.println("account made!");
    }

    // this is a placeholder for login, always yes for now.
    public static boolean loginUser() {
        System.out.println("\n[login]");
        System.out.print("enter username: ");
        String username = input.nextLine();

        System.out.print("enter password: ");
        String password = input.nextLine();

        // normally this would check the database
        log.info("login attempt by: " + username);
        return true; // always lets them in for now
    }
}
