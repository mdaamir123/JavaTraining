package dao;

import config.DatabaseConfig;
import session.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryDao {
    private static Scanner sc = new Scanner(System.in);
    private static DatabaseConfig config = new DatabaseConfig();

    public static boolean checkIfCategoriesExists() {
        try {
            Connection con = config.getConnection();
            String query = "select * from category";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                con.close();
                return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<List<String>> getAllCategories() {
        try {
            Connection con = config.getConnection();
            String query = "select * from category";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<List<String>> resultSet = new ArrayList<>();
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(rs.getInt(1)));
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                resultSet.add(list);
            }
            con.close();
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addCategory() {
        try {
            Connection con = config.getConnection();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfCategoryExists(int id) {
        try {
            Connection con = config.getConnection();
            String query = "select id from category where id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rss = stmt.executeQuery();

            if (!rss.next()) {
                con.close();
                return false;
            }

            con.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateCategory(int id, String newCategory) {
        try {
            Connection con = config.getConnection();
            String query3 = "update category set category_name = ?, updated_at = default, updated_by = ? where id =" + id;
            PreparedStatement stmt3 = con.prepareStatement(query3);
            stmt3.setString(1, newCategory);
            stmt3.setString(2, CurrentUser.getCurrentUser());
            stmt3.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
