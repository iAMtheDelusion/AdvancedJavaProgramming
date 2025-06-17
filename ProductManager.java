package courseProjects; // same package

import java.sql.*; // for database stuff
import java.util.Scanner; // for input

public class ProductManager {

    // scanner to ask user stuff
    private static Scanner scanner = new Scanner(System.in);

    // menu for managing products
    public static void showProductMenu() {
        while (true) {
            System.out.println("\n=== manage products ===");
            System.out.println("1. view all products");
            System.out.println("2. add new product");
            System.out.println("3. update product quantity");
            System.out.println("4. delete a product");
            System.out.println("5. go back");
            System.out.print("pick something: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showAllProducts();
                    break;
                case "2":
                    addNewProduct();
                    break;
                case "3":
                    updateProductQuantity();
                    break;
                case "4":
                    deleteProduct();
                    break;
                case "5":
                    return; // go back to main menu
                default:
                    System.out.println("not a valid option.");
            }
        }
    }

    // shows every product in the db
    private static void showAllProducts() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== product list ===");

            while (rs.next()) {
                int id = rs.getInt("product_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                System.out.println(id + ". " + name + " - $" + price + " (stock: " + quantity + ")");
            }

        } catch (SQLException e) {
            System.out.println("error showing products");
            e.printStackTrace();
        }
    }

    // adds a new product
    private static void addNewProduct() {
        System.out.print("product name: ");
        String name = scanner.nextLine();

        System.out.print("price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();

            System.out.println("product added!");

        } catch (SQLException e) {
            System.out.println("couldn't add product.");
            e.printStackTrace();
        }
    }

    // updates how many of a product we have
    private static void updateProductQuantity() {
        System.out.print("product id to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("new quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE products SET quantity = ? WHERE product_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("quantity updated.");
            } else {
                System.out.println("product not found.");
            }

        } catch (SQLException e) {
            System.out.println("error updating quantity.");
            e.printStackTrace();
        }
    }

    // deletes a product
    private static void deleteProduct() {
        System.out.print("product id to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM products WHERE product_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("product deleted.");
            } else {
                System.out.println("no product found with that id.");
            }

        } catch (SQLException e) {
            System.out.println("error deleting product.");
            e.printStackTrace();
        }
    }
}
