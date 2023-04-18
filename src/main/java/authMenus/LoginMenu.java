package authMenus;

import authenticate.Authenticate;
import dao.AuthenticationDao;
import model.User;
import session.LoggedInUser;

import java.util.Scanner;

public class LoginMenu {
    public static void displayLoginMenu() {
        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("Please enter your email: ");
        user.setEmail(sc.nextLine());
        System.out.println("Please enter your password: ");
        user.setPassword(sc.nextLine());


        user = Authenticate.isValidUser(user);

        if(user != null) {
            if(user.isUserVerified() == true) {
                System.out.println("Login successful !!!");
                System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ". Your role is " + user.getUserRole().getUserRoleName() + ".");
                LoggedInUser.setCurrentUser(user);
            }
            else {
                System.out.println("Please enter verification pin to verify your account: ");
                int verificationPin = sc.nextInt();
                sc.nextLine();

                if(verificationPin == user.getVerificationPin()) {
                    System.out.println("Verification successful !!!");
                    AuthenticationDao.updateVerifiedUser(user);
                    user.setUserVerified(true);
                    System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + ". Your role is " + user.getUserRole().getUserRoleName() + ".");
                    LoggedInUser.setCurrentUser(user);
                }
                else {
                    System.out.println("Incorrect verification pin !!!");
                }
            }

        }
        else {
            System.out.println("Login unsuccessful !!!");
        }
    }
}
