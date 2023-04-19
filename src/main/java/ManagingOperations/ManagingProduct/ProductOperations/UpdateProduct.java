package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts.*;
import exceptions.DAOLayerException;
import dao.ProductDao;
import display.Display;

import java.util.Scanner;

public class UpdateProduct {
    Scanner sc = new Scanner(System.in);

    public void updateProduct() {
        try {
            if (!ProductDao.checkIfProductsExists()) {
                System.out.println("No products are present.");
                return;
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            Display.printProducts(ProductDao.getALlProducts());
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Enter id of product you want to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        try {
            if (!ProductDao.checkIfProductExists(id)) {
                System.out.println("ID not found.");
                return;
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Enter choice of attribute you want to update: ");
        System.out.println("1. Product Title.");
        System.out.println("2. Description.");
        System.out.println("3. Price.");
        System.out.println("4. Category.");
        System.out.println("5. Discount.");
        System.out.println("6. Brand.");
        System.out.println("7. Specifications.");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                UpdateProductTitle updateProductTitle = new UpdateProductTitle();
                updateProductTitle.updateProductTitle(id);
                break;
            case 2:
                UpdateProductDescription updateProductDescription = new UpdateProductDescription();
                updateProductDescription.updateProductDescription(id);
                break;
            case 3:
                UpdateProductPrice updateProductPrice = new UpdateProductPrice();
                updateProductPrice.updateProductPrice(id);
                break;
            case 4:
                UpdateProductCategory updateProductCategory = new UpdateProductCategory();
                updateProductCategory.updateProductCategory(id);
                break;
            case 5:
                UpdateProductDiscount updateProductDiscount = new UpdateProductDiscount();
                updateProductDiscount.updateProductDiscount(id);
                break;
            case 6:
                UpdateProductBrand updateProductBrand = new UpdateProductBrand();
                updateProductBrand.updateProductBrand(id);
                break;
            case 7:
                UpdateProductSpecifications updateProductSpecifications = new UpdateProductSpecifications();
                updateProductSpecifications.updateProductSpecifications(id);
                break;
            default:
                System.out.println("Please enter valid input.");
                break;
        }


    }

}
