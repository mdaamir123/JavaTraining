package com.narola.onlineshopping.dao;

import com.narola.onlineshopping.config.DatabaseHelperClass;
import com.narola.onlineshopping.config.DatabaseConfig;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.CartItems;
import com.narola.onlineshopping.session.LoggedInUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    public static boolean isCartEmpty(int userId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from cart_items where user_id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, userId);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static List<CartItems> getCartItems() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        List<CartItems> cartItemsList = new ArrayList<>();
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select p.id, p.product_title, p.price, p.brand, c.quantity, c.created_at, c.updated_at," +
                    "c.created_by, c.updated_by, c.id from product p join cart_items c on p.id = c.product_id where user_id = ? and quantity > 0";

            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, LoggedInUser.getCurrentUser().getUserId());
            resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                CartItems cartItem = new CartItems();
                cartItem.setProductId(resultSet.getInt(1));
                cartItem.setProductTitle(resultSet.getString(2));
                cartItem.setPrice(resultSet.getFloat(3));
                cartItem.setBrand(resultSet.getString(4));
                cartItem.setQuantity(resultSet.getInt(5));
                cartItem.setCreatedOn(resultSet.getTimestamp(6).toLocalDateTime());
                cartItem.setUpdatedOn(resultSet.getTimestamp(7).toLocalDateTime());
                cartItem.setCreatedBy(resultSet.getInt(8));
                cartItem.setUpdatedBy(resultSet.getInt(9));
                cartItem.setCartId(resultSet.getInt(10));
                cartItemsList.add(cartItem);
            }
            return cartItemsList;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static void addItemToCart(int userId, int productId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            if (doItemExists(userId, productId)) {
                updateProductQuantity(userId, productId, 1);
            } else {
                String insertQuery = "insert into cart_items(user_id, product_id, created_by, updated_by) values (?,?,?,?)";
                stmt = con.prepareStatement(insertQuery);
                stmt.setInt(1, userId);
                stmt.setInt(2, productId);
                stmt.setInt(3, userId);
                stmt.setInt(4, LoggedInUser.getCurrentUser().getUserId());
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding item to cart.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static void updateProductQuantity(int userId, int productId, int quantity) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update cart_items set quantity = quantity + ?, updated_at = default, updated_by = ? where user_id = ? and product_id = ?";
            stmt = con.prepareStatement(updateQuery);
            stmt.setInt(1, quantity);
            stmt.setInt(2, LoggedInUser.getCurrentUser().getUserId());
            stmt.setInt(3, userId);
            stmt.setInt(4, productId);
            stmt.executeUpdate();
            if (quantity < 0) {
                deleteItemIfZeroQuantity(userId, productId);
            }
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while modifying cart item quantity.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static void deleteItemIfZeroQuantity(int userId, int productId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String deleteQuery = "delete from cart_items where user_id = ? and product_id = ? and quantity = 0";
            stmt = con.prepareStatement(deleteQuery);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while modifying cart item quantity.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static boolean doItemExists(int userId, int productId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String countQuery = "select count(*) from cart_items where user_id = ? and product_id = ? and quantity > 0";
            stmt = con.prepareStatement(countQuery);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            resultSet = stmt.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static void deleteItemFromCart(int userId, int productId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            if (!doItemExists(userId, productId)) {
                System.out.println("No such item exists in your cart.");
                return;
            }
            String deleteQuery = "delete from cart_items where user_id = ? and product_id = ?";
            stmt = con.prepareStatement(deleteQuery);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching cart items.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }
}



