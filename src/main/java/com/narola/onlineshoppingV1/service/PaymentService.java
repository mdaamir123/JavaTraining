package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.dao.PaymentDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.enums.PaymentCredentials;
import com.narola.onlineshoppingV1.enums.PaymentMethods;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.input.InputHandler;
import com.narola.onlineshoppingV1.model.Order;
import com.narola.onlineshoppingV1.model.PaymentCredential;
import com.narola.onlineshoppingV1.model.UserPaymentCredential;
import com.narola.onlineshoppingV1.validation.InputValidator;
import com.narola.onlineshoppingV1.view.PaymentView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentService {
    private PaymentView paymentView = new PaymentView();

    public List<UserPaymentCredential> handlePaymentManagement(Order order) {
        int selectedPaymentMethod = selectPaymentMethod(order);
        List<UserPaymentCredential> userPaymentCredentialList = makePayment(selectedPaymentMethod, order);
        return userPaymentCredentialList;
    }

    private int selectPaymentMethod(Order order) {
        try {
            int paymentMethodId = paymentView.getPaymentMethodIdFromUser();
            while (!PaymentDao.doPaymentMethodExists(paymentMethodId)) {
                paymentView.displayErrorMessage("Please select valid payment method: ");
                paymentMethodId = paymentView.getPaymentMethodIdFromUser();
            }
            order.setPaymentMethodId(paymentMethodId);
            return paymentMethodId;
        } catch (DAOLayerException e) {
            e.printStackTrace();
            handlePaymentManagement(order);
        }
        return 0;
    }

    private List<UserPaymentCredential> makePayment(int paymentMethodId, Order order) {
        try {
            if (paymentMethodId == PaymentMethods.COD.getValue()) {
                return Collections.emptyList();
            }

            if (paymentMethodId == PaymentMethods.NETBANKING.getValue()) {
                int bankId =paymentView.getBankIdFromUser();
                while (!PaymentDao.doBankExists(bankId)) {
                    paymentView.displayErrorMessage("Enter valid bank id: ");
                    bankId = paymentView.getBankIdFromUser();
                }
                order.setBankId(bankId);
            }
            List<UserPaymentCredential> userPaymentCredentialList = new ArrayList<>();
            List<PaymentCredential> paymentCredentialList = PaymentDao.getPaymentCredentialsRequired(paymentMethodId);
            for (PaymentCredential paymentCredential : paymentCredentialList) {
                UserPaymentCredential userPaymentCredential = new UserPaymentCredential();
                userPaymentCredential.setCredentialId(paymentCredential.getPaymentCredentialId());

                paymentView.displayInfoMessage("Please enter your " + paymentCredential.getPaymentCredentialName());
                if (paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_TYPE.getValue()) {
                    Display.printCreditCardTypes(PaymentDao.getCreditCardTypes());
                }
                String credential = InputHandler.getStrInput();

                if (paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_EXPIRATION_DATE.getValue() ||
                        paymentCredential.getPaymentCredentialId() == PaymentCredentials.DEBIT_CARD_EXPIRATION_DATE.getValue()) {
                    while (!InputValidator.isValidDateFormat(credential)) {
                        paymentView.displayErrorMessage("Please enter Expiration Date in (YYYY/MM) format.");
                        credential = InputHandler.getStrInput();
                    }
                } else if (paymentCredential.getPaymentCredentialId() == PaymentCredentials.DEBIT_CARD_NUMBER.getValue()
                        || paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_NUMBER.getValue()
                        || paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_CVV_CODE.getValue()
                        || paymentCredential.getPaymentCredentialId() == PaymentCredentials.DEBIT_CARD_CVV_CODE.getValue()) {
                    while (!InputValidator.isInputInteger(credential)) {
                        paymentView.displayErrorMessage("Please enter valid input.");
                        credential = InputHandler.getStrInput();
                    }
                } else if (paymentCredential.getPaymentCredentialId() == PaymentCredentials.CREDIT_CARD_TYPE.getValue()) {
                    boolean isValid = false;
                    while (!isValid) {
                        if (!InputValidator.isInputInteger(credential)) {
                            paymentView.displayErrorMessage("Please enter valid input.");
                            credential = InputHandler.getStrInput();
                            continue;
                        }
                        if (!PaymentDao.doCreditCardTypeExists(Integer.parseInt(credential))) {
                            paymentView.displayErrorMessage("Please enter valid credit card type.");
                            credential = InputHandler.getStrInput();
                            continue;
                        }
                        order.setCreditCardTypeId(Integer.parseInt(credential));
                        break;
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
