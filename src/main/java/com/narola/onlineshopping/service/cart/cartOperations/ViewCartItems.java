package com.narola.onlineshopping.service.cart.cartOperations;

import com.narola.onlineshopping.dao.CartDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;

public class ViewCartItems {
    public static void getCartItems() {
        try {
            Display.printUserCartItems(CartDao.getCartItems());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
