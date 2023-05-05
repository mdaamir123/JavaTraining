package com.narola.onlineshopping.service.cart.cartOperations;

import com.narola.onlineshopping.dao.CartDao;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.session.LoggedInUser;

public class AddToCart {
    public static void addItemToCart() {
        try {
            if (!ProductDao.doProductsExists()) {
                System.out.println("No products are present.");
                return;
            }

            Display.printProducts(ProductDao.getALlProducts());
            System.out.println("Please enter id of the product: ");
            int productId = TakeInput.getIntInput();

            if (!ProductDao.doProductExists(productId)) {
                System.out.println("Please enter valid product id.");
                addItemToCart();
            } else {
                CartDao.addItemToCart(LoggedInUser.getCurrentUser().getUserId(), productId);
                System.out.println("Item successfully added.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
