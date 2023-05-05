package com.narola.onlineshopping.menu;

import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.model.User;
import com.narola.onlineshopping.service.user.UserService;
import com.narola.onlineshopping.validation.EmailValidator;

public class LoginMenu {
    public static void displayLoginMenu() {
        User user = new User();
        boolean isEmailValid = false;
        System.out.println("Please enter your email: ");
        String email = "";
        while (!isEmailValid) {
            email = TakeInput.getStrInput();
            isEmailValid = EmailValidator.validate(email);
            if (!isEmailValid) {
                System.out.println("Please enter valid email address.");
            }
        }
        user.setEmail(email);
        System.out.println("Please enter your password: ");
        //user.setPassword(TakeInput.getStrInput());
        user.setPassword(TakeInput.getPassword());
        UserService.loginUser(user);
    }
}
