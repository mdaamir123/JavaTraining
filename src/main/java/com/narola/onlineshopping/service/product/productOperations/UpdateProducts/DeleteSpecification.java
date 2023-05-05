package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.model.Specification;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;

public class DeleteSpecification {
    public static void deleteSpecification(int productId) {
        Specification specification = new Specification();
        specification.setSpecProductId(productId);
        specification.setUpdatedBy(LoggedInUser.currentUser.getUserId());

        try {
            if (ProductDao.getProductSpecifications(productId).isEmpty()) {
                System.out.println("No specification to delete.");
                return;
            }

            Display.printProductSpecifications(ProductDao.getProductSpecifications(productId));
            System.out.println("Enter id of the specification you want to delete: ");
            int specId = TakeInput.getIntInput();

            if(!ProductDao.doSpecificationExists(specId, productId)) {
                System.out.println("No specification with id " + specId + " exists.");
                deleteSpecification(productId);
            }

            else {
                ProductDao.deleteProductSpecification(specId);
                System.out.println("Successfully deleted !!!");
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
