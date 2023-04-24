package authenticate;

import authMenu.LoginMenu;
import dao.AuthenticationDao;
import exception.DAOLayerException;
import model.User;

public class Authenticate {

    public static User isValidUser(User user) {

        try {
            user = AuthenticationDao.isValidUser(user);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            LoginMenu.displayLoginMenu();
        }
        if(user == null) {
            System.out.println("Invalid credentials !!! Kindly login again !!!");
            LoginMenu.displayLoginMenu();
        }
        return user;
    }
}
