package com.narola.onlineshopping.menu;

import com.narola.onlineshopping.dao.UserDao;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.model.User;

import java.util.Random;

import static com.narola.onlineshopping.service.user.UserService.signupUser;

public class SignupMenu {
    public static void displaySignupMenu() {
        Random rand = new Random();
        User user = new User();
        boolean isEmailExists = true;

        System.out.println("Please enter first name: ");
        user.setFirstName(TakeInput.getStrInput());
        System.out.println("Please enter last name: ");
        user.setLastName(TakeInput.getStrInput());
        System.out.println("Please enter email: ");
        String email = "";
        while (isEmailExists) {
            email = TakeInput.getStrInput();
            try {
                isEmailExists = UserDao.doEmailExists(email);
                if(isEmailExists) {
                    System.out.println("Email already exists. Please try with another email.");
                }
            } catch (DAOLayerException e) {
                e.printStackTrace();
                displaySignupMenu();
            }
        }
        user.setEmail(email);
        System.out.println("Please enter password: ");
        //user.setPassword(TakeInput.getStrInput());
        user.setPassword(TakeInput.getPassword());
        user.setVerificationPin(rand.nextInt(900000) + 100000);
        signupUser(user);
    }
}
