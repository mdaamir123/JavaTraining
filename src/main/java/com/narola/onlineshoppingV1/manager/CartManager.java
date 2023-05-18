package com.narola.onlineshoppingV1.manager;

import com.narola.onlineshoppingV1.service.CartService;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.CartView;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class CartManager {
    private CartView cartView = new CartView();
    CartService cartService = new CartService();

    public void handleCartManagement() {
        ProductManager productManager = new ProductManager();
        int choice = cartView.getCartOperationChoiceFromUser();
        switch (choice) {
            case VIEW_CART_ITEMS:
                cartService.getCartItems();
                break;

            case ADD_ITEM_TO_CART:
                cartService.addItemToCart();
                break;

            case UPDATE_CART:
                cartService.updateItemQuantity();
                break;

            case DELETE_CART_ITEM:
                cartService.deleteProductFromCart();
                break;

            case CHECK_OUT:
                cartService.proceedToCheckout();
                break;

            case BACK_TO_PRODUCT_MENU:
                productManager.handleProductsViewManagement();
                break;

            case EXIT:
                ProgramTerminator.exit();

            default:
                System.out.println("Please enter valid choice");
                break;
        }
        handleCartManagement();
    }
}
