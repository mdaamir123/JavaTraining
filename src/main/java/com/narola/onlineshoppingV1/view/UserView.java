package com.narola.onlineshoppingV1.view;

import com.narola.onlineshoppingV1.dao.LocationDao;
import com.narola.onlineshoppingV1.dao.UserDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.input.InputHandler;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class UserView {
    public void displayInfoMessage(String message) {
        System.out.println(message);
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public int getVerificationCodeFromUser() {
        displayInfoMessage("Please enter verification code: ");
        return InputHandler.getIntInput();
    }

    public int getAddressOperationOptionFromUser() {
        System.out.println(ADD_ADDRESS + " :Add Address");
        System.out.println(SELECT_EXISTING_ADDRESS + ": Select Address");
        System.out.println(BACK_TO_MENU_CART_OPTIONS + ": Back");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }

    public String getAddressLine1FromUser() {
        System.out.println("Enter Address_Line_1: ");
        return InputHandler.getStrInput();
    }

    public String getAddressLine2FromUser() {
        System.out.println("Enter Address_Line_2: ");
        return InputHandler.getStrInput();
    }

    public String getLandMarkFromUser() {
        System.out.println("Enter landmark: ");
        return InputHandler.getStrInput();
    }

    public String getPinCodeFromUser() {
        System.out.println("Enter pincode: ");
        return InputHandler.getStrInput();
    }

    public int getCityIdFromUser() {
        try {
            Display.printCities(LocationDao.getCities());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter city id: ");
        return InputHandler.getIntInput();
    }

    public int getStateIdFromUser() {
        try {
            Display.printStates(LocationDao.getStates());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter state id: ");
        return InputHandler.getIntInput();
    }

    public int getAddressIdFromUser() {
        try {
            Display.printUserAddress(UserDao.getUserAddresses());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please select address id: ");
        return InputHandler.getIntInput();
    }
}
