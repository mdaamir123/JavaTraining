package com.narola.onlineshopping.menu;

import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.model.User;
import com.narola.onlineshopping.service.user.UserService;

public class LoginMenu {
    public static void displayLoginMenu() {
        User user = new User();
        System.out.println("Please enter your email: ");
        user.setEmail(TakeInput.getStrInput());
        System.out.println("Please enter your password: ");
        //user.setPassword(TakeInput.getStrInput());
        user.setPassword(TakeInput.getPassword());
        UserService.loginUser(user);
    }
}
