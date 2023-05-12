package com.narola.onlineshopping.service.user.address;

import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.model.Order;
import com.narola.onlineshopping.service.cart.CartManager;
import com.narola.onlineshopping.service.user.UserService;
import com.narola.onlineshopping.system.ProgramTerminator;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class UserAddressManager {
    public static void handleUserAddressManagement(Order order) {
        int choice = displayAddressOptionsAndSelect();
        switch (choice) {
            case ADD_ADDRESS:
                UserService.addUserAddress(order);
                break;
            case SELECT_EXISTING_ADDRESS:
                UserService.selectExistingAddress(order);
                break;
            case BACK_TO_MENU_CART_OPTIONS:
                CartManager.displayCartOptions();
                break;
            case EXIT:
                ProgramTerminator.exit();
                break;
            default:
                System.out.println("Please enter valid choice");
                handleUserAddressManagement(order);
                break;
        }
    }

    public static int displayAddressOptionsAndSelect() {
        System.out.println(ADD_ADDRESS + " :Add Address");
        System.out.println(SELECT_EXISTING_ADDRESS + ": Select Address");
        System.out.println(BACK_TO_MENU_CART_OPTIONS + ": Back");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }
}
