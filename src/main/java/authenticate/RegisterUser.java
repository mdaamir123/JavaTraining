package authenticate;

import authMenus.LoginMenu;
import dao.AuthenticationDao;
import email.SendMail;
import exceptions.DAOLayerException;
import model.User;

public class RegisterUser {
    public static void signupUser(User user) {
        try {
            AuthenticationDao.signupUser(user);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Successfully registered. Please check your mail for verification code. You have to insert that code on your first login to verify you raccount.");
        SendMail.sendMail(user);
        LoginMenu.displayLoginMenu();
    }
}
