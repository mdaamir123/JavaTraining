package com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder;

import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;

public class ViewProductById {
    public static void getProductById() {
        try {
            System.out.println("Enter id of product.");
            int productId = InputHandler.getIntInput();
            if (!ProductDao.doProductExists(productId)) {
                System.out.println("No such product exists !!!");
                return;
            }

            Display.printProductById(ProductDao.getProductById(productId));
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
