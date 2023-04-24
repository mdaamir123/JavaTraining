package authenticate;

import authMenu.LoginMenu;
import dao.AuthenticationDao;
import email.SendMail;
import exception.DAOLayerException;
import model.User;

public class RegisterUser {
    public static void signupUser(User user) {
        try {
            AuthenticationDao.signupUser(user);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully registered. Please check your mail for verification code. You have to insert that code on your first login to verify your account.");
        SendMail.sendMail(user);
        System.out.println("You can now login !!!");
        LoginMenu.displayLoginMenu();
    }
}
