package com.narola.onlineshoppingV1.view;

import com.narola.onlineshoppingV1.dao.PaymentDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.input.InputHandler;

public class PaymentView {
    public void displaySuccessMessage(String message) {
        System.out.println(message);
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public void displayInfoMessage(String message) {
        System.out.println(message);
    }

    public int getPaymentMethodIdFromUser() {
        try {
            Display.printPaymentMethods(PaymentDao.getPaymentMethods());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please select payment method: ");
        return InputHandler.getIntInput();
    }

    public int getBankIdFromUser() {
        try {
            Display.printBankNames(PaymentDao.getBankNames());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please select bank id: ");
        return InputHandler.getIntInput();
    }
}
