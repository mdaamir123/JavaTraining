package dao;

import authMenus.SignupMenu;
import config.DatabaseConfig;
import model.User;
import model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

public class AuthenticationDao {

    public static void signupUser(User user) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into user(first_name, last_name, email, password, verification_pin) values (?,?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(insertQuery);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setInt(5, user.getVerificationPin());
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLIntegrityConstraintViolationException sqle) {
            System.out.println("Email already exists. Kindly try again with another email.");
            SignupMenu.displaySignupMenu();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static  User isValidUser(User user) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select u.id, u.first_name, u.last_name, u.email, u.user_role, u.verification_pin,u.is_verified, u.created_at, u.updated_at, u.created_by, u.updated_by, ur.role from user u join user_roles ur where u.email = ? and u.password = ? and u.user_role = ur.id";
            PreparedStatement stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            ResultSet resultSet = stmt.executeQuery();

            if(!resultSet.next()) {
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
            stmt.close();
            resultSet.close();
            return user;
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void updateVerifiedUser(User user) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update user set is_verified = true, verification_pin = null, updated_at = default where id = ?";
            PreparedStatement stmt = con.prepareStatement(updateQuery);
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
