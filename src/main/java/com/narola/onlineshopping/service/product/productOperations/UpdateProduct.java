package com.narola.onlineshopping.service.product.productOperations;

import com.narola.onlineshopping.service.product.productOperations.UpdateProducts.AddSpecification;
import com.narola.onlineshopping.service.product.productOperations.UpdateProducts.DeleteSpecification;
import com.narola.onlineshopping.service.product.productOperations.UpdateProducts.UpdateProductSpecifications;
import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.model.Product;
import com.narola.onlineshopping.system.ExitSystem;
import com.narola.onlineshopping.validation.Validation;

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
            int id = TakeInput.getIntInput();

            if (!ProductDao.doProductExists(id)) {
                System.out.println("ID not found.");
                return;
            }
            product = ProductDao.getProductById(id);
            product.setProductId(id);

            System.out.println("Enter product_title if want to update else leave it blank.");
            String product_title = TakeInput.getStrInput();
            if (!Validation.isEmpty(product_title)) {
                product.setProductTitle(product_title);
            }

            System.out.println("Enter description if want to update else leave it blank.");
            String description = TakeInput.getStrInput();
            if (!Validation.isEmpty(description)) {
                product.setProductDescription(description);
            }

            System.out.println("Enter price if want to update else leave it blank.");
            String price = TakeInput.getStrInput();
            if (!Validation.isEmpty(String.valueOf(price))) {
                product.setProductPrice(Float.parseFloat(price));
            }

            Display.printCategories(CategoryDao.getAllCategories());
            System.out.println("Enter category_id if want to update else leave it blank.");
            String category = TakeInput.getStrInput();
            if (!Validation.isEmpty(String.valueOf(category))) {
                if (!CategoryDao.doCategoryExists(Integer.parseInt(category))) {
                    System.out.println("Category doesn't exist.");
                    return;
                }
                product.setProductCategoryId(Integer.parseInt(category));
            }

            System.out.println("Enter discount if want to update else leave it blank.");
            String discount = TakeInput.getStrInput();
            if (!Validation.isEmpty(String.valueOf(discount))) {
                product.setProductDiscount(Float.parseFloat(discount));
            }

            System.out.println("Enter brand if want to update else leave it blank.");
            String brand = TakeInput.getStrInput();
            if (!Validation.isEmpty(brand)) {
                product.setProductBrand(brand);
            }

            if (ProductDao.doProductSpecificationsExistsForGivenProduct(id)) {
                boolean continueUpdate = true;
                do {
                    System.out.println("Do you want to add/update/delete product specifications ?");
                    System.out.println("1. Yes");
                    System.out.println("Other. No");
                    int choice = TakeInput.getIntInput();

                    switch (choice) {
                        case 1:
                            displayCrudOptions(id);
                            break;
                        case 2:
                            continueUpdate = false;
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
        System.out.println(ADD_SPECIFICATION+". Add specification");
        System.out.println(UPDATE_SPECIFICATION+". Update specification");
        System.out.println(DELETE_SPECIFICATION+". Delete specification");
        System.out.println(EXIT+". Exit");
        int choice = TakeInput.getIntInput();

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
                ExitSystem.exit();
            default:
                System.out.println("Invalid choice selected.");
                displayCrudOptions(productId);
                break;
        }
    }
}
