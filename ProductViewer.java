package courseProjects;

import java.sql.*;
import java.util.*;

public class ProductViewer {

    public static void viewProducts(Scanner scanner, int userId) {
        System.out.println("\n=== available products ===");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products WHERE quantity > 0";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();

            ArrayList<Integer> productIds = new ArrayList<>();
            int optionNumber = 1;

            while (result.next()) {
                int id = result.getInt("product_id");
                String name = result.getString("name");
                String desc = result.getString("description");
                int stock = result.getInt("quantity");
                double price = result.getDouble("price");

                System.out.println(optionNumber + ". " + name + " - $" + price + " (" + stock + " left)");
                System.out.println("    " + desc);

                productIds.add(id);
                optionNumber++;
            }

            if (productIds.isEmpty()) {
                System.out.println("no products found.");
                return;
            }

            System.out.print("type the number to add to cart, or 0 to go back: ");
            int pick = scanner.nextInt();
            scanner.nextLine();

            if (pick > 0 && pick <= productIds.size()) {
                int chosenId = productIds.get(pick - 1);

                System.out.print("how many would you like to add? ");
                int qty = scanner.nextInt();
                scanner.nextLine();

                CartManager.addToCart(chosenId, qty);
            } else if (pick == 0) {
                System.out.println("going back.");
            } else {
                System.out.println("invalid pick.");
            }

        } catch (SQLException e) {
            System.out.println("error while showing products");
            e.printStackTrace();
        }
    }
}
