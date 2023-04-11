package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    public static DatabaseConfig config = null;

    private static  String HOST;
    private static  String USERNAME;
    private static  String PASSWORD;
    private static Connection con;

    private DatabaseConfig() {
    }

    public Connection getConnection() {
        try {
            if (con == null) {
                con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

//    public static Connection getNewConnection() {
//        try {
//            return DriverManager.getConnection(HOST, USERNAME, PASSWORD);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static DatabaseConfig getInstance() {
        if (config == null) {
            config = new DatabaseConfig();
        }
        return config;
    }

    public static void checkDatabaseConnection(String database, String mysqlUsername, String mysqlPassword) throws SQLException {
        HOST = "jdbc:mysql://localhost:3306/" + database;
        USERNAME = mysqlUsername;
        PASSWORD = mysqlPassword;
        con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
    }
}
