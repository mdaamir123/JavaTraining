package com.narola.onlineshopping.service.payment;

import com.narola.onlineshopping.dao.PaymentDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.enums.PaymentCredentials;
import com.narola.onlineshopping.enums.PaymentMethods;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.model.Order;
import com.narola.onlineshopping.model.PaymentCredential;
import com.narola.onlineshopping.model.UserPaymentCredential;
import com.narola.onlineshopping.validation.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentManager {
    public static List<UserPaymentCredential> handlePaymentManagement(Order order) {
        int selectedPaymentMethod = selectPaymentMethod(order);
        List<UserPaymentCredential> userPaymentCredentialList = makePayment(selectedPaymentMethod, order);
        return userPaymentCredentialList;
    }

    public static int selectPaymentMethod(Order order) {
        try {
            Display.printPaymentMethods(PaymentDao.getPaymentMethods());
            System.out.println("Please select payment method: ");
            int paymentMethodId = InputHandler.getIntInput();
            while (!PaymentDao.doPaymentMethodExists(paymentMethodId)) {
                System.out.println("Please select valid payment method: ");
                paymentMethodId = InputHandler.getIntInput();
            }
            order.setPaymentMethodId(paymentMethodId);
            return paymentMethodId;
        } catch (DAOLayerException e) {
            e.printStackTrace();
            handlePaymentManagement(order);
        }
        return 0;
    }

    public static List<UserPaymentCredential> makePayment(int paymentMethodId, Order order) {
        try {
            if (paymentMethodId == PaymentMethods.COD.getValue()) {
                return Collections.emptyList();
            }

            if (paymentMethodId == PaymentMethods.NETBANKING.getValue()) {
                Display.printBankNames(PaymentDao.getBankNames());
                System.out.println("Please select bank id: ");
                int bankId = InputHandler.getIntInput();
                while (!PaymentDao.doBankExists(bankId)) {
                    System.out.println("Enter valid bank id: ");
                    bankId = InputHandler.getIntInput();
                }
                order.setBankId(bankId);
            }
            List<UserPaymentCredential> userPaymentCredentialList = new ArrayList<>();
            List<PaymentCredential> paymentCredentialList = PaymentDao.getPaymentCredentialsRequired(paymentMethodId);
            for (PaymentCredential paymentCredential : paymentCredentialList) {
                UserPaymentCredential userPaymentCredential = new UserPaymentCredential();
                userPaymentCredential.setCredentialId(paymentCredential.getPaymentCredentialId());

                System.out.println("Please enter your " + paymentCredential.getPaymentCredentialName());
                String credential = InputHandler.getStrInput();

                if (paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_EXPIRATION_DATE.getValue() ||
                        paymentCredential.getPaymentCredentialId() == PaymentCredentials.DEBIT_CARD_EXPIRATION_DATE.getValue()) {
                    while (!Validation.isValidDateFormat(credential)) {
                        System.out.println("Please enter Expiration Date in (YYYY/MM) format.");
                        credential = InputHandler.getStrInput();
                    }
                } else if (paymentCredential.getPaymentCredentialId() == PaymentCredentials.DEBIT_CARD_NUMBER.getValue()
                        || paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_NUMBER.getValue()
                        || paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_CVV_CODE.getValue()
                        || paymentCredential.getPaymentCredentialId() == PaymentCredentials.DEBIT_CARD_CVV_CODE.getValue()) {
                    while (!Validation.isInputInteger(credential)) {
                        System.out.println("Please enter valid input.");
                        credential = InputHandler.getStrInput();
                    }
                }
                userPaymentCredential.setCredentialValue(credential);
                userPaymentCredentialList.add(userPaymentCredential);
            }
            return userPaymentCredentialList;
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
