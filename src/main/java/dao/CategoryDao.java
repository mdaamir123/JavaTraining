package dao;

import config.DatabaseConfig;
import login.UserCredential;
import session.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class CategoryDao {
    private static Connection con = DatabaseConfig.getConnection();
    private static Scanner sc = new Scanner(System.in);

    public static void addCategory() {
        try {
            String query = "insert into category (category_name, created_by, updated_by) values (?, ?, ?)";
            String category = sc.nextLine();
            String username = CurrentUser.getCurrentUser();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, category);
            stmt.setString(2, username);
            stmt.setString(3, username);
            stmt.executeUpdate();
            System.out.println("Successfully inserted !!!");
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCategory() {

    }

}
