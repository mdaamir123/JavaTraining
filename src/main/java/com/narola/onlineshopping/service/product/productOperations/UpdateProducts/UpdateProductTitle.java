package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.validation.Validation;

public class UpdateProductTitle {
    public void updateProductTitle(int id) {
        try {
            System.out.println("Enter new product title. Leave blank if don't want to modify.");
            String newTitle = TakeInput.getStrInput();

            if (Validation.isEmpty(newTitle)) {
                return;
            }

            ProductDao.updateProductTitle(id, newTitle);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
