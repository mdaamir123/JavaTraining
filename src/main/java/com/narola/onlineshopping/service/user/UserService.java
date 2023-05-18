package com.narola.onlineshopping.service.user;

import com.narola.onlineshopping.OnlineShoppingApplication;
import com.narola.onlineshopping.dao.LocationDao;
import com.narola.onlineshopping.dao.UserDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.exception.MailException;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.menu.SignupMenu;
import com.narola.onlineshopping.model.Order;
import com.narola.onlineshopping.model.User;
import com.narola.onlineshopping.model.UserAddress;
import com.narola.onlineshopping.service.email.MailService;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.validation.UserValidator;

import static com.narola.onlineshopping.menu.LoginMenu.displayLoginMenu;

public class UserService {
    public static void signupUser(User user) {
        String subject = "Welcome to OnlineShopping";
        String text = "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\nHere, is your verification code: " + user.getVerificationPin();
        try {
            MailService.sendMail(user.getEmail(), subject, text);
            UserDao.addUser(user);
        } catch (MailException e) {
            e.printStackTrace();
            SignupMenu.displaySignupMenu();
        } catch (DAOLayerException e) {
            e.printStackTrace();
            SignupMenu.displaySignupMenu();
        }
        System.out.println("Successfully registered. Please check your mail for verification code. You have to insert that code on your first login to verify your account.");

        System.out.println("You can now login !!!");
        OnlineShoppingApplication.main(null);
    }

    public static void loginUser(User user) {
        user = UserValidator.validateUser(user);

        if (user != null) {
            if (user.isUserVerified() == true) {
                System.out.println("Login successful !!!");
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ". Your role is " + user.getUserRole().getUserRoleName() + ".");
                LoggedInUser.setCurrentUser(user);
                OnlineShoppingApplication.displayMenuBasedOnAccess();
            } else {
                verifyUser(user);
            }
        } else {
            System.out.println("Login unsuccessful !!! Try again !!!");
            displayLoginMenu();
        }
    }

    public static void verifyUser(User user) {
        System.out.println("Please enter verification pin to verify your account: ");
        int verificationPin = InputHandler.getIntInput();

        if (verificationPin == user.getVerificationPin()) {
            System.out.println("Verification successful !!!");
            try {
                UserDao.updateVerifiedUser(user);
            } catch (DAOLayerException e) {
                e.printStackTrace();
            }

            user.setUserVerified(true);
            System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ". Your role is " + user.getUserRole().getUserRoleName() + ".");
            LoggedInUser.setCurrentUser(user);
            OnlineShoppingApplication.displayMenuBasedOnAccess();
        } else {
            System.out.println("Incorrect verification pin !!! Login again !!!");
            displayLoginMenu();
        }
    }

    public static void addUserAddress(Order order) {
        try {
            UserAddress userAddress = new UserAddress();
            System.out.println("Enter Address_Line_1: ");
            userAddress.setAddressLine1(InputHandler.getStrInput());
            System.out.println("Enter Address_Line_2: ");
            userAddress.setAddressLine2(InputHandler.getStrInput());
            System.out.println("Enter landmark: ");
            userAddress.setLandmark(InputHandler.getStrInput());
            System.out.println("Enter pincode: ");
            userAddress.setPincode(InputHandler.getStrInput());
            Display.printCities(LocationDao.getCities());
            System.out.println("Please enter city id: ");
            int cityId = InputHandler.getIntInput();
            while (!LocationDao.doCityExists(cityId)) {
                System.out.println("Please enter valid city id: ");
                cityId = InputHandler.getIntInput();
            }
            userAddress.setCityId(cityId);
            Display.printStates(LocationDao.getStates());
            System.out.println("Please enter state id: ");
            int stateId = InputHandler.getIntInput();
            while (!LocationDao.doStateExists(stateId)) {
                System.out.println("Please enter valid state id: ");
                stateId = InputHandler.getIntInput();
            }
            userAddress.setStateId(stateId);
            int addressId = UserDao.addUserAddress(userAddress);
            order.setUserAddressId(addressId);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectExistingAddress(Order order) {
        try {
            Display.printUserAddress(UserDao.getUserAddresses());
            System.out.println("Please select address id: ");
            int addressId = InputHandler.getIntInput();
            while (!UserDao.doUserAddressExists(addressId)) {
                System.out.println("Please enter valid address id: ");
                addressId = InputHandler.getIntInput();
            }
            order.setUserAddressId(addressId);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
