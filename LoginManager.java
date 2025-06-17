package courseProjects; // this is the folder it's in

import java.util.Scanner; // lets us ask the user stuff
import java.sql.*; // lets us talk to the database

public class LoginManager {

    // scanner to get input from user
    private static Scanner scanner = new Scanner(System.in);

    // this is what shows up when the app starts
    public static void showLoginScreen() {
        while (true) {
            System.out.println("\n=== swiftorders login ===");
            System.out.println("1. login");
            System.out.println("2. register new user");
            System.out.println("3. exit");
            System.out.print("pick an option: ");

            String choice = scanner.nextLine(); // get what they typed

            // figure out what they picked
            switch (choice) {
                case "1":
                    int userId = login(); // try to login and get user id
                    if (userId != -1) {
                        // save the id to use later when ordering
                        OrderSystemMenu.loggedInUserId = userId;
                        System.out.println("login successful!");
                        OrderSystemMenu.showMainMenu(); // show the menu
                    } else {
                        System.out.println("login failed. try again.");
                    }
                    break;

                case "2":
                    registerUser(); // take them to sign-up
                    break;

                case "3":
                    System.out.println("bye!");
                    System.exit(0); // close program
                    break;

                default:
                    System.out.println("invalid choice."); // not 1/2/3
            }
        }
    }

    // checks if the username and password match
    private static int login() {
        System.out.print("username: ");
        String username = scanner.nextLine(); // get name

        System.out.print("password: ");
        String password = scanner.nextLine(); // get password

        try (Connection conn = DBConnection.getConnection()) {
            // look for a matching user in the database
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery(); // run the check

            if (rs.next()) {
                // if it found someone, get their user_id
                return rs.getInt("user_id");
            } else {
                // login failed
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("error during login.");
            e.printStackTrace();
            return -1;
        }
    }

    // lets someone make a new user account
    private static void registerUser() {
        System.out.print("choose a username: ");
        String username = scanner.nextLine(); // get new name

        System.out.print("choose a password: ");
        String password = scanner.nextLine(); // get new password

        try (Connection conn = DBConnection.getConnection()) {
            // insert the new user into the database
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate(); // save it

            System.out.println("user registered! you can log in now.");

        } catch (SQLException e) {
            System.out.println("something went wrong.");
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("that username already exists!");
            } else {
                e.printStackTrace();
            }
        }
    }
}
