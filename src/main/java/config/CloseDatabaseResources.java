package config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CloseDatabaseResources {
    public static void closePreparedStatement(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new SQLException("Exception occurred while closing statement.", e);
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) throws SQLException {
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new SQLException("Exception occurred while ResultSet.", e);
            }
        }
    }
}
