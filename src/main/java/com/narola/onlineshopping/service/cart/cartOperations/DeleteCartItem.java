package com.narola.onlineshopping.service.cart.cartOperations;

import com.narola.onlineshopping.dao.CartDao;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.session.LoggedInUser;

public class DeleteCartItem {
    public static void deleteProductFromCart() {
        try {
            if (!CartDao.isCartEmpty(LoggedInUser.getCurrentUser().getUserId())) {
                System.out.println("No item is present in your cart.");
                return;
            }
            ViewCartItems.getCartItems();
            System.out.println("Enter product id you want to remove from cart: ");
            int productId = TakeInput.getIntInput();

            if (!CartDao.doItemExists(LoggedInUser.getCurrentUser().getUserId(), productId)) {
                System.out.println("No such item exists in your cart.");
            } else {
                CartDao.deleteItemFromCart(LoggedInUser.getCurrentUser().getUserId(), productId);
                System.out.println("Item successfully deleted.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
