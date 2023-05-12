package com.narola.onlineshopping.dao;

import com.narola.onlineshopping.config.DatabaseConfig;
import com.narola.onlineshopping.config.DatabaseHelperClass;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.CartItem;
import com.narola.onlineshopping.model.Order;
import com.narola.onlineshopping.model.UserPaymentCredential;
import com.narola.onlineshopping.session.LoggedInUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    public static void addOrder(Order order, List<UserPaymentCredential> userPaymentCredentialList) throws DAOLayerException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            con.setAutoCommit(false);
            String insertQuery = "insert into `order` (user_id, address_id, payment_method_id, total_amount, bank_id, created_by, updated_by) values (?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, LoggedInUser.getCurrentUser().getUserId());
            stmt.setInt(2, order.getUserAddressId());
            stmt.setInt(3, order.getPaymentMethodId());
            stmt.setFloat(4, CartDao.getCartTotal());
            if (order.getBankId() != null) {
                stmt.setInt(5, order.getBankId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }
            stmt.setInt(6, LoggedInUser.getCurrentUser().getUserId());
            stmt.setInt(7, LoggedInUser.getCurrentUser().getUserId());
            stmt.executeUpdate();
            generatedKeys = stmt.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
                for (UserPaymentCredential userPaymentCredential : userPaymentCredentialList) {
                    storeUserPaymentCredentials(orderId, userPaymentCredential);
                }
                List<CartItem> cartItemList = CartDao.getCartItems();
                for (CartItem cartItem : cartItemList) {
                    addOrderedItems(orderId, cartItem.getCartId());
                }
                CartDao.updateIsOrderedInCartItem();
            }
        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new DAOLayerException("Exception occurred while placing an order.", e);
        } finally {
            if (con != null) {
                try {
                    con.commit();
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DatabaseHelperClass.closePreparedStatement(generatedKeys, stmt);
        }
    }

    public static void storeUserPaymentCredentials(int orderId, UserPaymentCredential userPaymentCredential) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into user_payment_credentials(order_id, credential_id, credential_value, created_by, updated_by) values (?,?,?,?,?)";
            stmt = con.prepareStatement(insertQuery);
            stmt.setInt(1, orderId);
            stmt.setInt(2, userPaymentCredential.getCredentialId());
            stmt.setString(3, userPaymentCredential.getCredentialValue());
            stmt.setInt(4, LoggedInUser.getCurrentUser().getUserId());
            stmt.setInt(5, LoggedInUser.getCurrentUser().getUserId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while storing payment credential.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static void addOrderedItems(int orderId, int cartId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into ordered_items (order_id, cart_id, price, created_by, updated_by) values (?,?,?,?,?)";
            stmt = con.prepareStatement(insertQuery);
            stmt.setInt(1, orderId);
            stmt.setInt(2, cartId);
            stmt.setFloat(3, ProductDao.getProductPrice(cartId));
            stmt.setInt(4, LoggedInUser.getCurrentUser().getUserId());
            stmt.setInt(5, LoggedInUser.getCurrentUser().getUserId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while storing ordered items.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static List<Order> getOrders() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select id, total_amount, order_date from `order` where user_id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, LoggedInUser.getCurrentUser().getUserId());
            resultSet = stmt.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt(1));
                order.setTotalAmount(resultSet.getFloat(2));
                order.setOrderDate(resultSet.getTimestamp(3).toLocalDateTime());
                orderList.add(order);
            }
            return orderList;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching orders.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }
}
