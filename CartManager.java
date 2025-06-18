package courseProjects;

import java.sql.*;
import java.util.*;

public class CartManager {

    private static Map<Integer, Integer> cart = new HashMap<>();

    public static void addToCart(int productId, int quantity) {
        cart.put(productId, cart.getOrDefault(productId, 0) + quantity);
        System.out.println("added to cart.");
    }

    public static void viewCartAndPlaceOrder(Scanner scanner, int userId) {
        if (cart.isEmpty()) {
            System.out.println("cart is empty.");
            return;
        }

        System.out.println("\n=== your cart ===");

        try (Connection conn = DBConnection.getConnection()) {
            double total = 0.0;

            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                int productId = entry.getKey();
                int qty = entry.getValue();

                String sql = "SELECT name, price FROM products WHERE product_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, productId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    double subtotal = qty * price;

                    System.out.println("- " + name + " x" + qty + " = $" + subtotal);
                    total += subtotal;
                }
            }

            System.out.println("total: $" + total);
            System.out.print("place this order? (yes/no): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("yes")) {
                placeOrder(conn, userId);
                System.out.println("order placed!");
                cart.clear();
            } else {
                System.out.println("order not placed.");
            }

        } catch (SQLException e) {
            System.out.println("something broke in cart manager");
            e.printStackTrace();
        }
    }

    private static void placeOrder(Connection conn, int userId) throws SQLException {
        String insertOrder = "INSERT INTO orders (user_id, order_date) VALUES (?, NOW())";
        PreparedStatement orderStmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
        orderStmt.setInt(1, userId);
        orderStmt.executeUpdate();

        ResultSet keys = orderStmt.getGeneratedKeys();
        keys.next();
        int orderId = keys.getInt(1);

        String insertItem = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
        PreparedStatement itemStmt = conn.prepareStatement(insertItem);

        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            itemStmt.setInt(1, orderId);
            itemStmt.setInt(2, entry.getKey());
            itemStmt.setInt(3, entry.getValue());
            itemStmt.executeUpdate();

            String updateStock = "UPDATE products SET quantity = quantity - ? WHERE product_id = ?";
            PreparedStatement stockStmt = conn.prepareStatement(updateStock);
            stockStmt.setInt(1, entry.getValue());
            stockStmt.setInt(2, entry.getKey());
            stockStmt.executeUpdate();
        }
    }
}
