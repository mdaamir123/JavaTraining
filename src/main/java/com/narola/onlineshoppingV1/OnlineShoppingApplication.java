package com.narola.onlineshoppingV1;

import com.narola.onlineshoppingV1.manager.UserManager;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.Menu;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class OnlineShoppingApplication {
    static {
        System.out.println("\n------Welcome to Online Shopping !!!------\n");
    }

    private static Menu menu = new Menu();

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        int choice = menu.displayMainMenuAndSelect();
        switch (choice) {
            case MENU_LOGIN:
                userManager.doLogin();
                break;
            case MENU_SIGNUP:
                userManager.doSignUp();
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Please enter valid choice:");
                main(null);
                break;
        }
    }
}

