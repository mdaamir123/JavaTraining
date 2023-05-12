package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.validation.InputValidator;

import java.util.Scanner;

public class UpdateProductSpecifications {
    public static void updateProductSpecifications(int id) {
        try {
            Scanner sc = new Scanner(System.in);
            Display.printProductSpecifications(ProductDao.getAllProductSpecifications(id));
            System.out.println("Please enter id of specification you want to update.");
            int specId = InputHandler.getIntInput();

            if (!ProductDao.doSpecificationExists(specId, id)) {
                System.out.println("Please enter valid specification id.");
                updateProductSpecifications(id);
            } else {
                System.out.println("Please enter new attribute name. Leave blank if don't want to modify it.");
                String newAttName = sc.nextLine();
                if (!InputValidator.isEmpty(newAttName)) {
                    ProductDao.updateProductSpecificationAttributeName(specId, newAttName);
                }

                System.out.println("Please enter new attribute value. Leave blank if don't want to modify it.");
                String newAttValue = sc.nextLine();
                if (!InputValidator.isEmpty(newAttValue)) {
                    ProductDao.updateProductSpecificationAttributeValue(specId, newAttValue);
                }

                System.out.println("Successfully updated.");
            }
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

