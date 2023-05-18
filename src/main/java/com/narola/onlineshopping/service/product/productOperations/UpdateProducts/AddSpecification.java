package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.model.Specification;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.exception.DAOLayerException;

public class AddSpecification {
    public static void addSpecification(int productId) {
        Specification specification = new Specification();
        specification.setSpecProductId(productId);

        System.out.println("Enter attribute name: ");
        specification.setSpecAttributeName(InputHandler.getStrInput());

        System.out.println("Enter attribute value: ");
        specification.setSpecAttributeValue(InputHandler.getStrInput());

        specification.setCreatedBy(LoggedInUser.currentUser.getUserId());
        specification.setUpdatedBy(LoggedInUser.currentUser.getUserId());

        try {
            ProductDao.addNewSpecification(specification);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
