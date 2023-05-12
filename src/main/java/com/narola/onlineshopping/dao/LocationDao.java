package com.narola.onlineshopping.dao;

import com.narola.onlineshopping.config.DatabaseConfig;
import com.narola.onlineshopping.config.DatabaseHelperClass;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.City;
import com.narola.onlineshopping.model.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationDao {
    public static List<State> getStates() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from state";
            stmt = con.prepareStatement(selectQuery);
            resultSet = stmt.executeQuery();
            List<State> stateList = new ArrayList<>();
            while (resultSet.next()) {
                State state = new State();
                state.setStateId(resultSet.getInt(1));
                state.setStateName(resultSet.getString(2));
                state.setCreatedOn(resultSet.getTimestamp(3).toLocalDateTime());
                state.setUpdatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                state.setCreatedBy(resultSet.getInt(5));
                state.setUpdatedBy(resultSet.getInt(6));
                stateList.add(state);
            }
            return stateList;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching states.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static List<City> getCities() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from city";
            stmt = con.prepareStatement(selectQuery);
            resultSet = stmt.executeQuery();
            List<City> cityList = new ArrayList<>();
            while (resultSet.next()) {
                City city = new City();
                city.setCityId(resultSet.getInt(1));
                city.setCityName(resultSet.getString(2));
                city.setCreatedOn(resultSet.getTimestamp(3).toLocalDateTime());
                city.setUpdatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                city.setCreatedBy(resultSet.getInt(5));
                city.setUpdatedBy(resultSet.getInt(6));
                cityList.add(city);
            }
            return cityList;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching cities.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static boolean doStateExists(int stateId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from state where id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, stateId);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching state.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static boolean doCityExists(int cityId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from city where id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, cityId);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching city.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }
}
