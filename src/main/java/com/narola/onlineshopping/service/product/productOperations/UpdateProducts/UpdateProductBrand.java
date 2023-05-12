package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.validation.InputValidator;

public class UpdateProductBrand {
    public void updateProductBrand(int id) {
        try {
            System.out.println("Enter new product brand. Leave blank if don't want to modify.");
            String newBrand = InputHandler.getStrInput();

            if (InputValidator.isEmpty(newBrand)) {
                return;
            }
            ProductDao.updateProductBrand(id, newBrand);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
