package dao;

import config.CloseDatabaseResources;
import config.DatabaseConfig;
import exception.DAOLayerException;
import model.Category;
import session.LoggedInUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    //TODO : Give proper method name
    public static boolean checkIfCategoriesExists() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            //TODO : Use count query
            String countQuery = "select COUNT(*) from category";
            stmt = con.prepareStatement(countQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching categories.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Category> getAllCategories() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from category";
            stmt = con.prepareStatement(selectQuery);
            resultSet = stmt.executeQuery();

            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt(1));
                category.setCategoryName(resultSet.getString(2));
                category.setCreatedOn(resultSet.getTimestamp(3).toLocalDateTime());
                category.setUpdatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                category.setCreatedBy(resultSet.getInt(5));
                category.setUpdatedBy(resultSet.getInt(6));
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching categories.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addCategory(String newCategory) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into category (category_name, created_by, updated_by) values (?, ?, ?)";
            stmt = con.prepareStatement(insertQuery);
            stmt.setString(1, newCategory);
            stmt.setInt(2, LoggedInUser.currentUser.getUserId());
            stmt.setInt(3, LoggedInUser.currentUser.getUserId());
            stmt.executeUpdate();
            stmt.close();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding category.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkIfCategoryExists(int id) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            //TODO : Use count query
            String countQuery = "select COUNT(id) from category where id = ?";
            stmt = con.prepareStatement(countQuery);
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while checking category.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateCategory(int id, String newCategory) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update category set category_name = ?, updated_at = default, " +
                    "updated_by = ? where id =" + id;
            stmt = con.prepareStatement(updateQuery);
            stmt.setString(1, newCategory);
            stmt.setInt(2, LoggedInUser.currentUser.getUserId());
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating category.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
