package com.narola.onlineshopping.service.user;

import com.narola.onlineshopping.dao.UserDao;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.menu.SignupMenu;
import com.narola.onlineshopping.model.User;
import com.narola.onlineshopping.service.email.MailService;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.validation.UserValidation;

import javax.mail.MessagingException;

import static com.narola.onlineshopping.menu.LoginMenu.displayLoginMenu;

public class UserService {
    public static void signupUser(User user) {
        try {
            MailService.sendMail(user);
            UserDao.addUser(user);
        } catch (MessagingException e) {
            e.printStackTrace();
            SignupMenu.displaySignupMenu();
        } catch (DAOLayerException e) {
            e.printStackTrace();
            SignupMenu.displaySignupMenu();
        }
        System.out.println("Successfully registered. Please check your mail for verification code. You have to insert that code on your first login to verify your account.");

        System.out.println("You can now login !!!");
        displayLoginMenu();
    }

    public static void loginUser(User user) {
        user = UserValidation.validateUser(user);

        if (user != null) {
            if (user.isUserVerified() == true) {
                System.out.println("Login successful !!!");
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ". Your role is " + user.getUserRole().getUserRoleName() + ".");
                LoggedInUser.setCurrentUser(user);
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
        int verificationPin = TakeInput.getIntInput();

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
        } else {
            System.out.println("Incorrect verification pin !!! Login again !!!");
            displayLoginMenu();
        }
    }
}
