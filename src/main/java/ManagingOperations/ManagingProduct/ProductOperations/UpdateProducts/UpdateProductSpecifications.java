package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exceptions.DAOLayerException;
import dao.ProductDao;
import display.Display;

import java.util.Scanner;

public class UpdateProductSpecifications {
    public void updateProductSpecifications(int id) {
        Scanner sc = new Scanner(System.in);
        try {
            if (!ProductDao.checkIfProductSpecificationsExistsForGivenProduct(id)) {
                System.out.println("No specifications are present for the given product.");
                return;
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try {
            Display.printProductSpecifications(ProductDao.getAllProductSpecifications(id));
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Please enter id of specification you want to update. Leave blank if don't want to modify it.");
        int specId = sc.nextInt();
        sc.nextLine();

        if (String.valueOf(specId).trim().length() == 0) {
            return;
        }

        try {
            if (!ProductDao.checkIfProductSpecificationExists(specId)) {
                System.out.println("Please enter valid specification id.");
                return;
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Please enter new attribute name. Leave blank if don't want to modify it.");
        String newAttName = sc.nextLine();
        if (newAttName.trim().length() != 0) {
            try {
                ProductDao.updateProductSpecificationAttributeName(specId, newAttName);
            } catch (DAOLayerException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Please enter new attribute value. Leave blank if don't want to modify it.");
        String newAttValue = sc.nextLine();
        if (newAttValue.trim().length() != 0) {
            try {
                ProductDao.updateProductSpecificationAttributeValue(specId, newAttValue);
            } catch (DAOLayerException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("Successfully updated.");
    }
}

