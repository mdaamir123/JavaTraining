package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductSpecifications {
    public void updateProductSpecifications(int id) {
        Scanner sc = new Scanner(System.in);
        if (!ProductDao.checkIfProductSpecificationsExistsForGivenProduct(id)) {
            System.out.println("No specifications are present for the given product.");
            return;
        }
        ProductDao.getAllProductSpecifications(id);
        System.out.println("Please enter id of specification you want to update. Leave blank if don't want to modify it.");
        int specId = sc.nextInt();
        sc.nextLine();

        if (String.valueOf(specId).trim().length() == 0) {
            return;
        }

        if (!ProductDao.checkIfProductSpecificationExists(specId)) {
            System.out.println("Please enter valid specification id.");
            return;
        }

        System.out.println("Please enter new attribute name. Leave blank if don't want to modify it.");
        String newAttName = sc.nextLine();
        if (newAttName.trim().length() != 0) {
            ProductDao.updateProductSpecificationAttributeName(specId, newAttName);
        }

        System.out.println("Please enter new attribute value. Leave blank if don't want to modify it.");
        String newAttValue = sc.nextLine();
        if (newAttValue.trim().length() != 0) {
            ProductDao.updateProductSpecificationAttributeValue(specId, newAttValue);
        }
        System.out.println("Successfully updated.");
    }
}

