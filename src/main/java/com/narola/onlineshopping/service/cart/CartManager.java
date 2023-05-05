package com.narola.onlineshopping.service.cart;

import com.narola.onlineshopping.service.cart.cartOperations.AddToCart;
import com.narola.onlineshopping.service.cart.cartOperations.DeleteCartItem;
import com.narola.onlineshopping.service.cart.cartOperations.UpdateCartItemQuantity;
import com.narola.onlineshopping.service.cart.cartOperations.ViewCartItems;
import com.narola.onlineshopping.service.product.productOperations.ShowProducts;
import com.narola.onlineshopping.system.ExitSystem;
import com.narola.onlineshopping.input.TakeInput;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class CartManager {
    public static void displayCartOptions() {
        System.out.println("Enter from below choice: ");
        System.out.println(VIEW_CART_ITEMS + ". View cart items");
        System.out.println(ADD_ITEM_TO_CART + ". Add item to cart");
        System.out.println(UPDATE_CART + ". Update cart items");
        System.out.println(DELETE_CART_ITEM + ". Delete cart item");
        System.out.println(BACK_TO_PRODUCT_MENU + ". Back");
        System.out.println(EXIT + ". Exit");
        int choice = TakeInput.getIntInput();

        switch (choice) {
            case VIEW_CART_ITEMS:
                ViewCartItems.getCartItems();
                break;

            case ADD_ITEM_TO_CART:
                AddToCart.addItemToCart();
                break;

            case UPDATE_CART:
                UpdateCartItemQuantity.updateItemQuantity();
                break;

            case DELETE_CART_ITEM:
                DeleteCartItem.deleteProductFromCart();
                break;

            case BACK_TO_PRODUCT_MENU:
                ShowProducts.viewProducts();
                break;

            case EXIT:
                ExitSystem.exit();

            default:
                System.out.println("Please enter valid choice");
                break;
        }
        displayCartOptions();
    }
}
