package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String HOST = "jdbc:mysql://localhost:3306/onlineshopping";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection con;

    static {

        try {
            con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
