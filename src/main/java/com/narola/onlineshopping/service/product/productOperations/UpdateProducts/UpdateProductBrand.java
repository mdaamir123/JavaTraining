package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.validation.Validation;

public class UpdateProductBrand {
    public void updateProductBrand(int id) {
        try {
            System.out.println("Enter new product brand. Leave blank if don't want to modify.");
            String newBrand = TakeInput.getStrInput();

            if (Validation.isEmpty(newBrand)) {
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
