package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;

public class UpdateProductCategory {
    public void updateProductCategory(int id) {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                return;
            }
            System.out.println("Enter new category id from the below choices: ");
            Display.printCategories(CategoryDao.getAllCategories());
            int newId = InputHandler.getIntInput();
            if (String.valueOf(newId).trim().length() == 0) {
                return;
            }
            if (!CategoryDao.doCategoryExists(newId)) {
                return;
            }
            ProductDao.updateProductCategory(id, newId);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
