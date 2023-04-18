package authenticate;

import authMenus.LoginMenu;
import dao.AuthenticationDao;
import email.SendMail;
import model.User;

public class RegisterUser {
    public static void signupUser(User user) {
        AuthenticationDao.signupUser(user);
        System.out.println("Successfully registered. Please check your mail for verification code. You have to insert that code on your first login to verify you raccount.");
        SendMail.sendMail(user);
        LoginMenu.displayLoginMenu();
    }
}
