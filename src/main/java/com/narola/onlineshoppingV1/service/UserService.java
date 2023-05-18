package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.dao.LocationDao;
import com.narola.onlineshoppingV1.dao.UserDao;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.exception.MailException;
import com.narola.onlineshoppingV1.input.InputHandler;
import com.narola.onlineshoppingV1.manager.UserManager;
import com.narola.onlineshoppingV1.model.Order;
import com.narola.onlineshoppingV1.model.User;
import com.narola.onlineshoppingV1.model.UserAddress;
import com.narola.onlineshoppingV1.session.LoggedInUser;
import com.narola.onlineshoppingV1.validation.EmailValidator;
import com.narola.onlineshoppingV1.view.Menu;
import com.narola.onlineshoppingV1.view.UserView;

import java.util.Random;

public class UserService {
    private UserView userView = new UserView();
    private UserManager userManager = new UserManager();
    private Menu menu = new Menu();
    private final int MAX_LIMIT = 3;
    private int currentTry = 0;

    public User getSignUpInformationFromUser() {
        User user = new User();
        boolean isEmailExists = true;

        userView.displayInfoMessage("Please enter first name: ");
        user.setFirstName(InputHandler.getStrInput());
        userView.displayInfoMessage("Please enter last name: ");
        user.setLastName(InputHandler.getStrInput());
        userView.displayInfoMessage("Please enter email: ");
        String email = "";
        while (isEmailExists) {
            email = InputHandler.getStrInput();
            try {
                if (!EmailValidator.validate(email)) {
                    userView.displayInfoMessage("Please enter valid email.");
                    continue;
                }
                isEmailExists = UserDao.doEmailExists(email);
                if (isEmailExists) {
                    userView.displayInfoMessage("Email already exists. Please try with another email.");
                }
            } catch (DAOLayerException e) {
                e.printStackTrace();
                userManager.doSignUp();
            }
        }
        user.setEmail(email);
        userView.displayInfoMessage("Please enter password: ");
        user.setPassword(InputHandler.getStrInput());
        //user.setPassword(InputHandler.getPassword());
        user.setVerificationPin(new Random().nextInt(900000) + 100000);
        return user;
    }

    public User getLoginInformationMenu() {
        User user = new User();
        boolean isEmailValid = false;
        userView.displayInfoMessage("Please enter your email: ");
        String email = "";
        while (!isEmailValid) {
            email = InputHandler.getStrInput();
            isEmailValid = EmailValidator.validate(email);
            if (!isEmailValid) {
                userView.displayInfoMessage("Please enter valid email address.");
            }
        }
        user.setEmail(email);
        userView.displayInfoMessage("Please enter your password: ");
        //user.setPassword(InputHandler.getPassword());
        user.setPassword(InputHandler.getStrInput());
        return user;
    }

    public void doSignUp(User user) {
        String subject = "Welcome to OnlineShopping";
        String text = "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\nHere, is your verification code: " + user.getVerificationPin();
        try {
            MailService.sendMail(user.getEmail(), subject, text);
            UserDao.addUser(user);
        } catch (MailException e) {
            e.printStackTrace();
            userManager.doSignUp();
        } catch (DAOLayerException e) {
            e.printStackTrace();
            userManager.doSignUp();
        }
        userView.displayInfoMessage("Successfully registered. Please check your mail for verification code. You have to insert that code on your first login to verify your account.");
        com.narola.onlineshoppingV1.OnlineShoppingApplication.main(null);
    }

    public void doLogin(User user) {
        try {
            user = UserDao.isValidUser(user);
            if (user != null) {
                if (!checkIfUserIsVerified(user)) {
                    while (++currentTry != MAX_LIMIT) {
                        userView.displayInfoMessage("Please try again.");
                        if (checkIfUserIsVerified(user)) {
                            break;
                        }
                    }
                    if (currentTry == MAX_LIMIT) {
                        System.out.println("Verification unsuccessful. Kindly try again later.");
                        com.narola.onlineshoppingV1.OnlineShoppingApplication.main(null);
                    }
                }
                userView.displayInfoMessage("Login successful !!!");
                userView.displayInfoMessage("Welcome " + user.getFirstName() + " " + user.getLastName() + ". Your role is " + user.getUserRole().getUserRoleName() + ".");
                LoggedInUser.setCurrentUser(user);
                menu.displayMenuBasedOnAccess();

            } else {
                userView.displayInfoMessage("Login unsuccessful !!! Try again !!!");
                userManager.doLogin();
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUserIsVerified(User user) {
        if (!user.isUserVerified()) {
            int verificationPin = userView.getVerificationCodeFromUser();
            if (verificationPin == user.getVerificationPin()) {
                try {
                    UserDao.updateVerifiedUser(user);
                } catch (DAOLayerException e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public void addUserAddress(Order order) {
        try {
            UserAddress userAddress = new UserAddress();
            userAddress.setAddressLine1(userView.getAddressLine1FromUser());
            userAddress.setAddressLine2(userView.getAddressLine2FromUser());
            userAddress.setLandmark(userView.getLandMarkFromUser());
            userAddress.setPincode(userView.getPinCodeFromUser());

            int cityId = userView.getCityIdFromUser();
            while (!LocationDao.doCityExists(cityId)) {
                userView.displayErrorMessage("Please enter valid city id: ");
                cityId = userView.getCityIdFromUser();
            }
            userAddress.setCityId(cityId);

            int stateId = userView.getStateIdFromUser();
            while (!LocationDao.doStateExists(stateId)) {
                userView.displayErrorMessage("Please enter valid state id: ");
                stateId = userView.getStateIdFromUser();
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

    public void selectExistingAddress(Order order) {
        try {
            int addressId = userView.getAddressIdFromUser();
            while (!UserDao.doUserAddressExists(addressId)) {
                userView.displayErrorMessage("Please enter valid address id: ");
                addressId = userView.getAddressIdFromUser();
            }
            order.setUserAddressId(addressId);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}