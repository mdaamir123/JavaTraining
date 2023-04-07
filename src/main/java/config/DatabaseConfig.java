package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

    private static final String HOST = "jdbc:mysql://localhost:3306/onlineshopping";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection con;

    public Connection getConnection() {
        try {
            con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

}
