package com.narola.onlineshoppingV1.config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelperClass {


    public static void closePreparedStatement(ResultSet resultSet, PreparedStatement statement) {
        closeResultSet(resultSet);
        closePreparedStatement(statement);
    }

    public static void closePreparedStatement(PreparedStatement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Nothing to do.
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Nothing to do.
            }
        }
    }
}
