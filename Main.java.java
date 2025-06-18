package courseProjects;

import java.util.Scanner; // lets us get input from the user
import java.util.logging.Logger; // for logging

public class OrderSystemMenu {

    // gets our logger from mylogger file
    private static Logger log = MyLogger.getLogger();

    // used to get input from the user
    private static Scanner scanner = new Scanner(System.in);

    // user id for whoever logs in
    public static int loggedInUserId = -1;

    // where the app starts
    public static void main(String[] args) {
        log.info("program started");
        LoginManager.showLoginScreen(); // take user to login screen
    }

    // menu that shows after login
    public static void showMainMenu() {
        int choice = 0;

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
                scanner.nextLine();
                log.info("user picked option " + choice);
            } else {
                System.out.println("please type a number");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    ProductViewer.viewProducts(scanner, loggedInUserId); // product browser
                    break;
                case 2:
                    CartManager.viewCartAndPlaceOrder(scanner, loggedInUserId); // view and order
                    break;
                case 3:
                    OrderHistory.viewOrderHistory(scanner, loggedInUserId); // show orders
                    break;
                case 4:
                    ProductManager.showProductMenu(); // admin stuff
                    break;
                case 5:
                    System.out.println("logging out...");
                    return;
                case 6:
                    System.out.println("bye!");
                    System.exit(0);
                default:
                    System.out.println("invalid choice.");
            }

        } while (choice != 6);
    }
}
