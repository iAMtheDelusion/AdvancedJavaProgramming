package courseProjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // change these if your setup is different
    private static final String URL = "jdbc:mysql://localhost:3306/order_system";
    private static final String USER = "root"; // use your MySQL Workbench username
    private static final String PASS = "password"; // use your actual MySQL password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
