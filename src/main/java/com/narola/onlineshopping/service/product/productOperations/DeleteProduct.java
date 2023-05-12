package com.narola.onlineshopping.service.product.productOperations;

import com.narola.onlineshopping.service.product.ProductManager;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;

public class DeleteProduct {
    public static void deleteProduct() {
        try {
            if (!ProductDao.doProductsExists()) {
                System.out.println("No products exists !!!");
                ProductManager.handleProductManagement();
            } else {
                Display.printProducts(ProductDao.getALlProducts());
                System.out.println("Please enter the id of the product you want to delete: ");

                int productId = InputHandler.getIntInput();

                if (!ProductDao.doProductExists(productId)) {
                    System.out.println("Product not present with id " + productId);
                    deleteProduct();
                } else {
                    ProductDao.deleteProduct(productId);
                    System.out.println("Product successfully deleted !!!");
                    ProductManager.handleProductManagement();
                }
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
            deleteProduct();
        } catch (Exception e) {
            e.printStackTrace();
            deleteProduct();
        }
    }
}
