package com.narola.onlineshopping.dao;

import com.narola.onlineshopping.config.DatabaseConfig;
import com.narola.onlineshopping.config.DatabaseHelperClass;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    public static List<Bank> getBankNames() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from bank";
            stmt = con.prepareStatement(selectQuery);
            resultSet = stmt.executeQuery();
            List<Bank> bankList = new ArrayList<>();
            while (resultSet.next()) {
                Bank bank = new Bank();
                bank.setBankId(resultSet.getInt(1));
                bank.setBankName(resultSet.getString(2));
                bank.setCreatedOn(resultSet.getTimestamp(3).toLocalDateTime());
                bank.setUpdatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                bank.setCreatedBy(resultSet.getInt(5));
                bank.setUpdatedBy(resultSet.getInt(6));
                bankList.add(bank);
            }
            return bankList;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching banks.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static boolean doBankExists(int bankId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from bank where id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, bankId);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching the bank.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static List<PaymentMethod> getPaymentMethods() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from payment_methods";
            stmt = con.prepareStatement(selectQuery);
            resultSet = stmt.executeQuery();
            List<PaymentMethod> paymentMethodsList = new ArrayList<>();
            while (resultSet.next()) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setPaymentMethodId(resultSet.getInt(1));
                paymentMethod.setPaymentMethodName(resultSet.getString(2));
                paymentMethod.setCreatedOn(resultSet.getTimestamp(3).toLocalDateTime());
                paymentMethod.setUpdatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                paymentMethod.setCreatedBy(resultSet.getInt(5));
                paymentMethod.setUpdatedBy(resultSet.getInt(6));
                paymentMethodsList.add(paymentMethod);
            }
            return paymentMethodsList;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching payment methods.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static boolean doPaymentMethodExists(int paymentMethodId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from payment_methods where id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, paymentMethodId);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching payment method.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static List<CreditCardType> getCreditCardTypes() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from credit_card_types";
            stmt = con.prepareStatement(selectQuery);
            resultSet = stmt.executeQuery();
            List<CreditCardType> creditCardTypesList = new ArrayList<>();
            while (resultSet.next()) {
                CreditCardType creditCardType = new CreditCardType();
                creditCardType.setCreditCardTypeId(resultSet.getInt(1));
                creditCardType.setCreditCardTypeName(resultSet.getString(2));
                creditCardType.setCreatedOn(resultSet.getTimestamp(3).toLocalDateTime());
                creditCardType.setUpdatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                creditCardType.setCreatedBy(resultSet.getInt(5));
                creditCardType.setUpdatedBy(resultSet.getInt(6));
                creditCardTypesList.add(creditCardType);
            }
            return creditCardTypesList;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching credit card types.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static boolean doCreditCardTypeExists(int creditCardTypeId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select count(*) from credit_card_types where id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, creditCardTypeId);
            resultSet = stmt.executeQuery();
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching credit card type.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }

    public static List<PaymentCredential> getPaymentCredentialsRequired(int paymentMethodId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select pc.id, pc.credential_name, pc.created_at, " +
                    "pc.updated_at, pc.created_by, pc.updated_by from payment_credentials pc join \n" +
                    "payment_methods pm on pc.payment_method_id = pm.id where pm.id = ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, paymentMethodId);
            resultSet = stmt.executeQuery();
            List<PaymentCredential> paymentCredentialList = new ArrayList<>();
            while (resultSet.next()) {
                PaymentCredential paymentCredential = new PaymentCredential();
                paymentCredential.setPaymentCredentialId(resultSet.getInt(1));
                paymentCredential.setPaymentCredentialName(resultSet.getString(2));
                paymentCredential.setCreatedOn(resultSet.getTimestamp(3).toLocalDateTime());
                paymentCredential.setUpdatedOn(resultSet.getTimestamp(4).toLocalDateTime());
                paymentCredential.setCreatedBy(resultSet.getInt(5));
                paymentCredential.setUpdatedBy(resultSet.getInt(6));
                paymentCredentialList.add(paymentCredential);
            }
            return paymentCredentialList;
        } catch (Exception e) {
            throw new DAOLayerException("Error occurred while fetching payment credentials.", e);
        } finally {
            DatabaseHelperClass.closePreparedStatement(resultSet, stmt);
        }
    }
}
