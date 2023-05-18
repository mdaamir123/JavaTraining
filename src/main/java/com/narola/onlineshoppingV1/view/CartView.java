package com.narola.onlineshoppingV1.view;

import com.narola.onlineshoppingV1.dao.ProductDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.input.InputHandler;
import com.narola.onlineshoppingV1.service.CartService;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class CartView {

    public void displaySuccessMessage(String message) {
        System.out.println(message);
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public int getCartOperationChoiceFromUser() {
        System.out.println("Enter from below choice: ");
        System.out.println(VIEW_CART_ITEMS + ". View cart items");
        System.out.println(ADD_ITEM_TO_CART + ". Add item to cart");
        System.out.println(UPDATE_CART + ". Update cart items");
        System.out.println(DELETE_CART_ITEM + ". Delete cart item");
        System.out.println(CHECK_OUT + ". Check out");
        System.out.println(BACK_TO_PRODUCT_MENU + ". Back");
        System.out.println(EXIT + ". Exit");
        return InputHandler.getIntInput();
    }

    public int getProductIdToAddToCartFromUser() {
        try {
            Display.printProducts(ProductDao.getALlProducts());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter id of the product: ");
        return InputHandler.getIntInput();
    }

    public int getProductIdFromUserToModifyCartQuantity() {
        CartService cartService = new CartService();
        cartService.getCartItems();
        System.out.println("Enter id of the product you want to update quantity: ");
        return InputHandler.getIntInput();
    }

    public int getChoiceFromUserToModifyProductQuantityInCart() {
        System.out.println(INCREASE_QUANTITY_BY_1 + ". Increase Quantity by 1");
        System.out.println(DECREASE_QUANTITY_BY_1 + ". Decrease Quantity by 1");
        System.out.println(EXIT + ". Exit");
        return InputHandler.getIntInput();
    }

    public int getProductIdFromUserToRemoveProductFromCart() {
        CartService cartService = new CartService();
        cartService.getCartItems();
        System.out.println("Enter product id you want to remove from cart: ");
        return InputHandler.getIntInput();
    }
}
