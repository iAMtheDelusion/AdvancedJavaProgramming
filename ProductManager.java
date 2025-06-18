package courseProjects;

import java.sql.*;
import java.util.Scanner;

public class ProductManager {

    public static void showProductMenu() {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("\n=== product manager ===");
            System.out.println("1. view all products");
            System.out.println("2. add product");
            System.out.println("3. edit product");
            System.out.println("4. delete product");
            System.out.println("5. go back to main menu");
            System.out.print("choose an option: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear input

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addProduct(scanner);
                    break;
                case 3:
                    editProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("invalid choice.");
            }

        } while (choice != 5);
    }

    private static void viewProducts() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- all products ---");

            while (rs.next()) {
                System.out.println(rs.getInt("product_id") + ": " +
                    rs.getString("name") + " - $" + rs.getDouble("price") +
                    " [" + rs.getInt("quantity") + " in stock]");
                System.out.println("    " + rs.getString("description"));
            }

        } catch (SQLException e) {
            System.out.println("error viewing products.");
            e.printStackTrace();
        }
    }

    private static void addProduct(Scanner scanner) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("product name: ");
            String name = scanner.nextLine();

            System.out.print("description: ");
            String description = scanner.nextLine();

            System.out.print("price: ");
            double price = scanner.nextDouble();

            System.out.print("quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // clear input

            String sql = "INSERT INTO products (name, description, price, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();

            System.out.println("product added!");

        } catch (SQLException e) {
            System.out.println("error adding product.");
            e.printStackTrace();
        }
    }

    private static void editProduct(Scanner scanner) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("enter product ID to edit: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // clear

            System.out.print("new name: ");
            String name = scanner.nextLine();

            System.out.print("new description: ");
            String desc = scanner.nextLine();

            System.out.print("new price: ");
            double price = scanner.nextDouble();

            System.out.print("new quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();

            String sql = "UPDATE products SET name = ?, description = ?, price = ?, quantity = ? WHERE product_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, desc);
            stmt.setDouble(3, price);
            stmt.setInt(4, qty);
            stmt.setInt(5, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("product updated!");
            } else {
                System.out.println("product not found.");
            }

        } catch (SQLException e) {
            System.out.println("error updating product.");
            e.printStackTrace();
        }
    }

    private static void deleteProduct(Scanner scanner) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.print("enter product ID to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // clear input

            String sql = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("product deleted.");
            } else {
                System.out.println("product not found.");
            }

        } catch (SQLException e) {
            System.out.println("error deleting product.");
            e.printStackTrace();
        }
    }
}