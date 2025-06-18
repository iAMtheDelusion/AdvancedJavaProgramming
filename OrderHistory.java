package courseProjects;

import java.sql.*;
import java.util.*;
import java.io.*;

public class OrderHistory {

    public static void viewOrderHistory(Scanner scanner, int userId) {
        System.out.println("\n=== your order history ===");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            List<String> exportLines = new ArrayList<>();
            boolean hasOrders = false;

            while (rs.next()) {
                hasOrders = true;
                int orderId = rs.getInt("order_id");
                Timestamp date = rs.getTimestamp("order_date");

                System.out.println("\nOrder #" + orderId + " - " + date);
                exportLines.add("Order #" + orderId + " - " + date);

                String itemSql = "SELECT p.name, p.price, oi.quantity " +
                        "FROM order_items oi " +
                        "JOIN products p ON oi.product_id = p.product_id " +
                        "WHERE oi.order_id = ?";
                PreparedStatement itemStmt = conn.prepareStatement(itemSql);
                itemStmt.setInt(1, orderId);
                ResultSet itemRs = itemStmt.executeQuery();

                double total = 0.0;

                while (itemRs.next()) {
                    String name = itemRs.getString("name");
                    double price = itemRs.getDouble("price");
                    int qty = itemRs.getInt("quantity");
                    double line = qty * price;

                    System.out.println("  - " + name + " x" + qty + " = $" + line);
                    exportLines.add(name + "," + qty + "," + price + "," + line);
                    total += line;
                }

                System.out.println("  Total: $" + total);
                exportLines.add("Total: $" + total);
                exportLines.add("");
            }

            if (!hasOrders) {
                System.out.println("no orders yet.");
                return;
            }

            System.out.print("would you like to export to CSV? (yes/no): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("yes")) {
                exportToCSV(userId, exportLines);
            }

        } catch (SQLException e) {
            System.out.println("something went wrong showing orders.");
            e.printStackTrace();
        }
    }

    // this method saves the order history as a .csv file
    private static void exportToCSV(int userId, List<String> lines) {
        String filename = "order_history_user" + userId + ".csv";
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (String line : lines) {
                writer.println(line);
            }
            System.out.println("exported to " + filename);
        } catch (IOException e) {
            System.out.println("couldn’t write the csv.");
        }
    }
}
