package dao;

import config.DatabaseConfig;
import model.User;
import model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class AuthenticationDao {
    public static void signupUser(User user) {
        try {
            Random rand = new Random();
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query1 = "insert into user(first_name, last_name, email, password, verification_pin) values (?,?,?,?,?)";

            PreparedStatement stmt1 = con.prepareStatement(query1);
            stmt1.setString(1, user.getFirstName());
            stmt1.setString(2, user.getLastName());
            stmt1.setString(3, user.getEmail());
            stmt1.setString(4, user.getPassword());
            stmt1.setInt(5, rand.nextInt(900000) + 100000);
            stmt1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static  User isValidUser(User user) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select * from user where email = ? and password = ?";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
                user.setUserRoleId(resultSet.getInt(6));
                user.setVerificationPin(resultSet.getInt(7));
                user.setUserVerified(resultSet.getBoolean(8));
                user.setCreatedOn(resultSet.getTimestamp(9).toLocalDateTime());
                user.setUpdatedOn(resultSet.getTimestamp(10).toLocalDateTime());
                user.setCreatedBy(resultSet.getString(11));
                user.setUpdatedBy(resultSet.getString(12));
            }

            String query2 = "select role from user_roles where id = ?";
            PreparedStatement stmt2 = con.prepareStatement(query2, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt2.setInt(1, user.getUserRoleId());
            ResultSet resultSet2 = stmt2.executeQuery();

            while (resultSet2.next()) {
                userRole.setUserRoleId(user.getUserRoleId());
                userRole.setUserRoleName(resultSet2.getString(1));
                user.setUserRole(userRole);
            }
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
            String query = "update user set is_verified = true, verification_pin = null, updated_at = default where id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, user.getUserId());
            stmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
