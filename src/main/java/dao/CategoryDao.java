package dao;

import config.DatabaseConfig;
import model.Category;
import session.LoggedInUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryDao {

    private static Scanner sc = new Scanner(System.in);

    //TODO : Give proper method name
    public static boolean checkIfCategoriesExists() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            //TODO : Use count query
            String countQuery = "select COUNT(*) from category";
            PreparedStatement stmt = con.prepareStatement(countQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            stmt.close();
            rs.close();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Category> getAllCategories() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from category";
            PreparedStatement stmt = con.prepareStatement(selectQuery);
            ResultSet rs = stmt.executeQuery();

            List<Category> categories = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt(1));
                category.setCategoryName(rs.getString(2));
                category.setCreatedOn(rs.getTimestamp(3).toLocalDateTime());
                category.setUpdatedOn(rs.getTimestamp(4).toLocalDateTime());
                category.setCreatedBy(rs.getInt(5));
                category.setUpdatedBy(rs.getInt(6));
                categories.add(category);
            }

            stmt.close();
            rs.close();
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addCategory(String newCategory) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into category (category_name, created_by, updated_by) values (?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(insertQuery);
            stmt.setString(1, newCategory);
            stmt.setInt(2, LoggedInUser.currentUser.getUserId());
            stmt.setInt(3, LoggedInUser.currentUser.getUserId());
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfCategoryExists(int id) {
        try {

            Connection con = DatabaseConfig.getInstance().getConnection();
            //TODO : Use count query
            String countQuery = "select COUNT(id) from category where id = ?";
            PreparedStatement stmt = con.prepareStatement(countQuery);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            stmt.close();
            rs.close();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateCategory(int id, String newCategory) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update category set category_name = ?, updated_at = default, updated_by = ? where id =" + id;
            PreparedStatement stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, newCategory);
            stmt.setInt(2, LoggedInUser.currentUser.getUserId());
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
