package com.narola.onlineshopping.validation;

import com.narola.onlineshopping.dao.UserDao;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.menu.LoginMenu;
import com.narola.onlineshopping.model.User;

public class UserValidation {
    public static User validateUser(User user) {
        try {
            user = UserDao.isValidUser(user);
            if (user == null) {
                System.out.println("Invalid credentials !!! Kindly login again !!!");
                LoginMenu.displayLoginMenu();
            }
            return user;
        } catch (DAOLayerException e) {
            e.printStackTrace();
            LoginMenu.displayLoginMenu();
        }
        return null;
    }
}
