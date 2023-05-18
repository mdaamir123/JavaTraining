package com.narola.onlineshoppingV1.manager;

import com.narola.onlineshoppingV1.model.Order;
import com.narola.onlineshoppingV1.model.User;
import com.narola.onlineshoppingV1.service.UserService;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.UserView;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;
public class UserManager {
    private UserView userView = new UserView();

    public void doSignUp() {
        UserService userService = new UserService();
        User user = userService.getSignUpInformationFromUser();
        userService.doSignUp(user);
    }

    public void doLogin() {
        UserService userService = new UserService();
        User user = userService.getLoginInformationMenu();
        userService.doLogin(user);
    }

    public void handleUserAddressManagement(Order order) {
        UserService userService = new UserService();
        CartManager cartManager = new CartManager();
        int choice = userView.getAddressOperationOptionFromUser();
        switch (choice) {
            case ADD_ADDRESS:
                userService.addUserAddress(order);
                break;
            case SELECT_EXISTING_ADDRESS:
                userService.selectExistingAddress(order);
                break;
            case BACK_TO_MENU_CART_OPTIONS:
                cartManager.handleCartManagement();
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
}
