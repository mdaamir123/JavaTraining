package com.narola.onlineshoppingV1.view;

import com.narola.onlineshoppingV1.enums.UserRoles;
import com.narola.onlineshoppingV1.input.InputHandler;
import com.narola.onlineshoppingV1.manager.AdminManager;
import com.narola.onlineshoppingV1.manager.CustomerManager;
import com.narola.onlineshoppingV1.session.LoggedInUser;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class Menu {
    public int displayMainMenuAndSelect() {
        System.out.println("Please select from below choices:");
        System.out.println(MENU_LOGIN + ": Login");
        System.out.println(MENU_SIGNUP + ": Signup");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }

    public int displayMenuAndSelect() {
        System.out.println("Please choose one option: ");
        System.out.println(MENU_CATEGORY_MANAGEMENT + ": Category Management");
        System.out.println(MENU_PRODUCT_MANAGEMENT + ": Product Management");
        System.out.println(ADMIN_LOGOUT + ": Logout");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }

    public void displayMenuBasedOnAccess() {
        if (LoggedInUser.currentUser.getUserRole().getUserRoleId() == UserRoles.ADMIN.getValue()) {
            AdminManager adminManager = new AdminManager();
            adminManager.adminOptions();
        } else {
            CustomerManager customerManager = new CustomerManager();
            customerManager.customerOptions();
        }
    }
}
