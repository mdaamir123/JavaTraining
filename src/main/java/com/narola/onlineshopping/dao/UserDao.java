package com.narola.onlineshopping.dao;

import com.narola.onlineshopping.config.DatabaseConfig;
import com.narola.onlineshopping.config.DatabaseHelperClass;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.User;
import com.narola.onlineshopping.model.UserAddress;
import com.narola.onlineshopping.model.UserRole;
import com.narola.onlineshopping.session.LoggedInUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserDao {

    public static void addUser(User user) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into user(first_name, last_name, email, password, verification_pin) values (?,?,?,?,?)";
            stmt = con.prepareStatement(insertQuery);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getVerificationPin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOLayerException("Exception occurred while registering user.", e);
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while registering user.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static User isValidUser(User user) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select u.id, u.first_name, u.last_name, u.email, u.user_role, u.verification_pin,u.is_verified, u.created_at, " +
                    "u.updated_at, u.created_by, u.updated_by, ur.role " +
                    "from user u join user_roles ur where u.email = ? and u.password = ? and u.user_role = ur.id";
            stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            UserRole userRole = new UserRole();
            resultSet.previous();
            while (resultSet.next()) {
                user.setUserId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setUserRoleId(resultSet.getInt(5));
                user.setVerificationPin(resultSet.getInt(6));
                user.setUserVerified(resultSet.getBoolean(7));
                user.setCreatedOn(resultSet.getTimestamp(8).toLocalDateTime());
                user.setUpdatedOn(resultSet.getTimestamp(9).toLocalDateTime());
                user.setCreatedBy(resultSet.getInt(10));
                user.setUpdatedBy(resultSet.getInt(11));
                userRole.setUserRoleId(resultSet.getInt(5));
                userRole.setUserRoleName(resultSet.getString(12));
                user.setUserRole(userRole);
            }
            return user;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while validating user.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }


    public static void updateVerifiedUser(User user) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update user set is_verified = true, verification_pin = null, updated_at = default where id = ?";
            stmt = con.prepareStatement(updateQuery);
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while verifying the user.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(stmt);
        }
    }

    public static boolean doEmailExists(String email) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from user where email = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setString(1, email);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while verifying the user.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static List<UserAddress> getUserAddresses() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select ua.id, ua.user_id, ua.address_line_1, ua.address_line_2, ua.landmark, ua.pincode, ua.city_id, c.city_name, ua.state_id, s.state_name, ua.created_at, ua.updated_at, ua.created_by, ua.updated_by from user_address ua join city c on ua.city_id = c.id join state s on ua.state_id = s.id where user_id = ?";
            stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, LoggedInUser.getCurrentUser().getUserId());
            resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                return Collections.emptyList();
            }

            resultSet.previous();
            List<UserAddress> userAddressList = new ArrayList<>();
            while (resultSet.next()) {
                UserAddress userAddress = new UserAddress();
                userAddress.setUserAddressId(resultSet.getInt(1));
                userAddress.setUserId(resultSet.getInt(2));
                userAddress.setAddressLine1(resultSet.getString(3));
                userAddress.setAddressLine2(resultSet.getString(4));
                userAddress.setLandmark(resultSet.getString(5));
                userAddress.setPincode(resultSet.getString(6));
                userAddress.setCityId(resultSet.getInt(7));
                userAddress.setCityName(resultSet.getString(8));
                userAddress.setStateId(resultSet.getInt(9));
                userAddress.setStateName(resultSet.getString(10));
                userAddress.setCreatedOn(resultSet.getTimestamp(11).toLocalDateTime());
                userAddress.setUpdatedOn(resultSet.getTimestamp(12).toLocalDateTime());
                userAddress.setCreatedBy(resultSet.getInt(13));
                userAddress.setUpdatedBy(resultSet.getInt(14));
                userAddressList.add(userAddress);
            }
            return userAddressList;

        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching user address.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static int addUserAddress(UserAddress userAddress) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into user_address " +
                    "(user_id, address_line_1, address_line_2, landmark, pincode, city_id, state_id, created_by, updated_by) " +
                    "values (?,?,?,?,?,?,?,?,?)";
            stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, LoggedInUser.getCurrentUser().getUserId());
            stmt.setString(2, userAddress.getAddressLine1());
            stmt.setString(3, userAddress.getAddressLine2());
            stmt.setString(4, userAddress.getLandmark());
            stmt.setString(5, userAddress.getPincode());
            stmt.setInt(6, userAddress.getCityId());
            stmt.setInt(7, userAddress.getStateId());
            stmt.setInt(8, LoggedInUser.getCurrentUser().getUserId());
            stmt.setInt(9, LoggedInUser.getCurrentUser().getUserId());
            stmt.executeUpdate();
            generatedKeys = stmt.getGeneratedKeys();
            int addressId=  -1;
            if(generatedKeys.next()) {
                addressId = generatedKeys.getInt(1);
            }
            return addressId;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while inserting user address.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(generatedKeys, stmt);
        }
    }

    public static boolean doUserAddressExists(int addressId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from user_address where id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, addressId);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching the user address.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }
}
