package com.narola.onlineshopping;

import com.narola.onlineshopping.menu.LoginMenu;
import com.narola.onlineshopping.menu.SignupMenu;
import com.narola.onlineshopping.service.category.CategoryManager;
import com.narola.onlineshopping.service.product.ProductManager;
import com.narola.onlineshopping.enums.UserRoles;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.system.ProgramTerminator;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class OnlineShoppingApplication {
    static {
        System.out.println("\n------Welcome to Online Shopping !!!------\n");
    }

    public static void main(String[] args) {
        int choice = displayMainMenuAndSelect();
        switch (choice) {
            case MENU_LOGIN:
                LoginMenu.displayLoginMenu();
                break;
            case MENU_SIGNUP:
                SignupMenu.displaySignupMenu();
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Please enter valid choice:");
                main(null);
                break;
        }
        displayMenuBasedOnAccess();
    }

    public static int displayMainMenuAndSelect() {
        System.out.println("Please select from below choices:");
        System.out.println(MENU_LOGIN + ": Login");
        System.out.println(MENU_SIGNUP + ": Signup");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }

    public static int displayMenuAndSelect() {
        System.out.println("Please choose one option: ");
        System.out.println(MENU_CATEGORY_MANAGEMENT + ": Category Management");
        System.out.println(MENU_PRODUCT_MANAGEMENT + ": Product Management");
        System.out.println(ADMIN_LOGOUT + ": Logout");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }

    public static void adminOptions() {
        int selectedMenuID = displayMenuAndSelect();
        switch (selectedMenuID) {
            case MENU_CATEGORY_MANAGEMENT:
                CategoryManager.handleCategoryManagement();
                break;
            case MENU_PRODUCT_MANAGEMENT:
                ProductManager.handleProductManagement();
                break;
            case ADMIN_LOGOUT:
                main(null);
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Enter valid input.");
                adminOptions();
                break;
        }
    }

    public static void customerOptions() {
        ProductManager.handleProductManagement();
    }

    public static void displayMenuBasedOnAccess() {
        if (LoggedInUser.currentUser.getUserRole().getUserRoleId() == UserRoles.ADMIN.getValue()) {
            adminOptions();
        } else {
            customerOptions();
        }
    }
}

