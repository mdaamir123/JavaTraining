package authMenu;

import authenticate.Authenticate;
import dao.AuthenticationDao;
import exception.DAOLayerException;
import model.User;
import session.LoggedInUser;
import java.io.*;
import java.util.Scanner;

public class LoginMenu {
    public static void displayLoginMenu(){
        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("Please enter your email: ");
        try {
            user.setEmail(sc.nextLine());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            displayLoginMenu();
        }

        System.out.println("Please enter your password: ");
        //String password = sc.nextLine();
        Console console = System.console();
        if (console == null) {
            System.out.println("Console is not available. Try again.");
            displayLoginMenu();
        }

        String password = "";
        try {
            char[] passwordArray = console.readPassword("", "Please Enter your password: ");
            password = new String(passwordArray);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            displayLoginMenu();
        }
        user.setPassword(password);

        user = Authenticate.isValidUser(user);

        if (user != null) {
            if (user.isUserVerified() == true) {
                System.out.println("Login successful !!!");
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ". Your role is " + user.getUserRole().getUserRoleName() + ".");
                LoggedInUser.setCurrentUser(user);
            } else {
                System.out.println("Please enter verification pin to verify your account: ");
                int verificationPin = sc.nextInt();
                sc.nextLine();

                if (verificationPin == user.getVerificationPin()) {
                    System.out.println("Verification successful !!!");
                    try {
                        AuthenticationDao.updateVerifiedUser(user);
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
        } else {
            System.out.println("Login unsuccessful !!! Try again !!!");
            displayLoginMenu();
        }
    }
}
