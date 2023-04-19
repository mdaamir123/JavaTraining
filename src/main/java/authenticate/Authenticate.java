package authenticate;

import authMenus.LoginMenu;
import dao.AuthenticationDao;
import exceptions.DAOLayerException;
import model.User;

public class Authenticate {

    public static User isValidUser(User user) {

        try {
            user = AuthenticationDao.isValidUser(user);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        if(user == null) {
            System.out.println("Invalid credentials !!! Kindly login again !!!");
            LoginMenu.displayLoginMenu();
        }
        return user;
    }
}
