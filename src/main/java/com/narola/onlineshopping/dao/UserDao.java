package com.narola.onlineshopping.dao;

import com.narola.onlineshopping.config.DatabaseConfig;
import com.narola.onlineshopping.config.DatabaseHelperClass;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.User;
import com.narola.onlineshopping.model.UserRole;

import java.sql.*;

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
}
