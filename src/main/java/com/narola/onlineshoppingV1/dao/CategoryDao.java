package com.narola.onlineshoppingV1.dao;


import com.narola.onlineshoppingV1.config.DatabaseConfig;
import com.narola.onlineshoppingV1.config.DatabaseHelperClass;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.model.Category;
import com.narola.onlineshoppingV1.session.LoggedInUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    public static boolean doCategoriesExists() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String countQuery = "select COUNT(*) from category where is_delete = false";
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
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static List<Category> getAllCategories() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from category where is_delete = false";
            stmt = con.prepareStatement(selectQuery);
            resultSet = stmt.executeQuery();

            List<Category> categories = new ArrayList<>();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt(1));
                category.setCategoryName(resultSet.getString(2));
                category.setDelete(resultSet.getBoolean(3));
                category.setCreatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                category.setUpdatedOn(resultSet.getTimestamp(5).toLocalDateTime());
                category.setCreatedBy(resultSet.getInt(6));
                category.setUpdatedBy(resultSet.getInt(7));
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching categories.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
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

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding category.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static boolean doCategoryExists(int id) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            //TODO : Use count query
            String countQuery = "select COUNT(id) from category where id = ? and is_delete = false";
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
            DatabaseHelperClass.closePreparedStatement(stmt);
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
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static void deleteCategory(int categoryId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String deleteQuery = "update category set is_delete = true, updated_by = ?, updated_at = default where id = ?";
            stmt = con.prepareStatement(deleteQuery);
            stmt.setInt(1, LoggedInUser.currentUser.getUserId());
            stmt.setInt(2, categoryId);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting category.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }
}
