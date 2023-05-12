package com.narola.onlineshopping.service.cart.cartOperations;

import com.narola.onlineshopping.dao.CartDao;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.system.ProgramTerminator;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class UpdateCartItemQuantity {
    public static void updateItemQuantity() {
        try {
            if (!CartDao.isCartEmpty(LoggedInUser.getCurrentUser().getUserId())) {
                System.out.println("No item is present in your cart.");
                return;
            }
            ViewCartItems.getCartItems();
            System.out.println("Enter id of the product you want to update quantity: ");
            int productId = InputHandler.getIntInput();
            if (!CartDao.doItemExists(LoggedInUser.getCurrentUser().getUserId(), productId)) {
                System.out.println("No such item exists in your cart.");
                updateItemQuantity();
            } else {
                updateItem(productId);
                System.out.println("Cart updated successfully.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    private static void updateItem(int productId) throws DAOLayerException {
        System.out.println(INCREASE_QUANTITY_BY_1 + ". Increase Quantity by 1");
        System.out.println(DECREASE_QUANTITY_BY_1 + ". Decrease Quantity by 1");
        System.out.println(EXIT + ". Exit");
        int choice = InputHandler.getIntInput();
        try {
            switch (choice) {
                case INCREASE_QUANTITY_BY_1:
                    CartDao.updateProductQuantity(LoggedInUser.getCurrentUser().getUserId(), productId, 1);
                    break;
                case DECREASE_QUANTITY_BY_1:
                    CartDao.updateProductQuantity(LoggedInUser.getCurrentUser().getUserId(), productId, -1);
                    CartDao.deleteItemIfZeroQuantity(LoggedInUser.getCurrentUser().getUserId(), productId);
                    break;
                case EXIT:
                    ProgramTerminator.exit();
                default:
                    System.out.println("Please enter valid choice.");
                    updateItem(productId);
                    break;
            }
        } catch (DAOLayerException e) {
            throw new DAOLayerException(e);
        }
    }
}
