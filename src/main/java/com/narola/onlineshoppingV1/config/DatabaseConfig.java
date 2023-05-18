package com.narola.onlineshoppingV1.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {

    public static DatabaseConfig config = null;
    private static  String HOST = "jdbc:mysql://localhost:3306/" + System.getenv("db.name")+"?useSSL=false";
    private static  String USERNAME = System.getenv("db.username");
    private static  String PASSWORD = System.getenv("db.password").trim();
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

    public static DatabaseConfig getInstance() {
        if (config == null) {
            config = new DatabaseConfig();
        }
        return config;
    }

}
