package authenticate;

import authMenus.LoginMenu;
import dao.AuthenticationDao;
import model.User;

public class Authenticate {

    public static User isValidUser(User user) {

        user = AuthenticationDao.isValidUser(user);
        if(user == null) {
            System.out.println("Invalid credentials !!! Kindly login again !!!");
            LoginMenu.displayLoginMenu();
        }
        return user;
    }
}
