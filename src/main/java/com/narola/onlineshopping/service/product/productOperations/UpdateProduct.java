package com.narola.onlineshopping.service.product.productOperations;

import com.narola.onlineshopping.service.product.productOperations.UpdateProducts.AddSpecification;
import com.narola.onlineshopping.service.product.productOperations.UpdateProducts.DeleteSpecification;
import com.narola.onlineshopping.service.product.productOperations.UpdateProducts.UpdateProductSpecifications;
import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.model.Product;
import com.narola.onlineshopping.system.ProgramTerminator;
import com.narola.onlineshopping.validation.InputValidator;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class UpdateProduct {
    public static void updateProduct() {
        Product product;
        try {
            if (!ProductDao.doProductsExists()) {
                System.out.println("No products are present.");
                return;
            }

            Display.printProducts(ProductDao.getALlProducts());

            System.out.println("Enter id of product you want to update: ");
            int id = InputHandler.getIntInput();

            if (!ProductDao.doProductExists(id)) {
                System.out.println("ID not found.");
                return;
            }
            product = ProductDao.getProductById(id);
            product.setProductId(id);

            System.out.println("Enter product_title if want to update else leave it blank.");
            String product_title = InputHandler.getStrInput(true);
            if (!InputValidator.isEmpty(product_title)) {
                product.setProductTitle(product_title);
            }

            System.out.println("Enter description if want to update else leave it blank.");
            String description = InputHandler.getStrInput(true);
            if (!InputValidator.isEmpty(description)) {
                product.setProductDescription(description);
            }

            System.out.println("Enter price if want to update else leave it blank.");
            String price = InputHandler.getStrInput(true);
            while (!InputValidator.isEmpty(String.valueOf(price))) {
                if (InputValidator.isInputFloat(price)) {
                    product.setProductPrice(Float.parseFloat(price));
                    break;
                }
                System.out.println("Please enter valid input.");
                price = InputHandler.getStrInput(true);
            }

            Display.printCategories(CategoryDao.getAllCategories());
            System.out.println("Enter category_id if want to update else leave it blank.");
            String category = InputHandler.getStrInput(true);
            while (!InputValidator.isEmpty(category)) {
                if (InputValidator.isInputInteger(category)) {
                    if (!CategoryDao.doCategoryExists(Integer.parseInt(category))) {
                        System.out.println("Category doesn't exist. Please enter valid category id.");
                        category = InputHandler.getStrInput(true);
                        continue;
                    }
                    product.setProductCategoryId(Integer.parseInt(category));
                    break;
                }
                System.out.println("Please enter valid input.");
                category = InputHandler.getStrInput(true);
            }

            System.out.println("Enter discount if want to update else leave it blank.");
            String discount = InputHandler.getStrInput(true);
            while (!InputValidator.isEmpty(String.valueOf(discount))) {
                if (InputValidator.isInputFloat(discount)) {
                    product.setProductDiscount(Float.parseFloat(discount));
                    break;
                }
                System.out.println("Please enter valid input.");
                discount = InputHandler.getStrInput(true);
            }

            System.out.println("Enter brand if want to update else leave it blank.");
            String brand = InputHandler.getStrInput(true);
            if (!InputValidator.isEmpty(brand)) {
                product.setProductBrand(brand);
            }

            if (ProductDao.doProductSpecificationsExistsForGivenProduct(id)) {
                boolean continueUpdate = true;
                do {
                    System.out.println("Do you want to add/update/delete product specifications ?");
                    System.out.println("1. Yes");
                    System.out.println("Other. No");
                    int choice = InputHandler.getIntInput();

                    switch (choice) {
                        case 1:
                            displayCrudOptions(id);
                            break;
                        default:
                            continueUpdate = false;
                            break;
                    }
                } while (continueUpdate);
            }
            ProductDao.updateProduct(product);
            System.out.println("Successfully product updated.");

        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void displayCrudOptions(int productId) {
        System.out.println("Please select one option: ");
        System.out.println(ADD_SPECIFICATION + ". Add specification");
        System.out.println(UPDATE_SPECIFICATION + ". Update specification");
        System.out.println(DELETE_SPECIFICATION + ". Delete specification");
        System.out.println(EXIT + ". Exit");
        int choice = InputHandler.getIntInput();

        switch (choice) {
            case ADD_SPECIFICATION:
                AddSpecification.addSpecification(productId);
                break;
            case UPDATE_SPECIFICATION:
                UpdateProductSpecifications.updateProductSpecifications(productId);
                break;
            case DELETE_SPECIFICATION:
                DeleteSpecification.deleteSpecification(productId);
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Invalid choice selected.");
                displayCrudOptions(productId);
                break;
        }
    }
}
