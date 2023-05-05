package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.validation.Validation;

public class UpdateProductDescription {
    public void updateProductDescription(int id) {
        try {
            System.out.println("Enter new product description. Leave blank if don't want to modify.");
            String newDescription = TakeInput.getStrInput();

            if (Validation.isEmpty(newDescription)) {
                return;
            }
            ProductDao.updateProductDescription(id, newDescription);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
