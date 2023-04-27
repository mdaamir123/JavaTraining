package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts.AddSpecification;
import ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts.DeleteSpecification;
import ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts.UpdateProductSpecifications;
import dao.CategoryDao;
import exception.DAOLayerException;
import dao.ProductDao;
import display.Display;
import model.Product;

import java.util.Scanner;

public class UpdateProduct {
    Scanner sc = new Scanner(System.in);
    Product product = new Product();

    public void updateProduct() {
        try {
            if (!ProductDao.checkIfProductsExists()) {
                System.out.println("No products are present.");
                return;
            }

            Display.printProducts(ProductDao.getALlProducts());

            System.out.println("Enter id of product you want to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (!ProductDao.checkIfProductExists(id)) {
                System.out.println("ID not found.");
                return;
            }
            product = ProductDao.getProductById(id);
            product.setProductId(id);

            System.out.println("Enter product_title if want to update else leave it blank.");
            String product_title = sc.nextLine();
            if (product_title.trim().length() > 0) {
                product.setProductTitle(product_title);
            }

            System.out.println("Enter description if want to update else leave it blank.");
            String description = sc.nextLine();
            if (description.trim().length() > 0) {
                product.setProductDescription(description);
            }

            System.out.println("Enter price if want to update else leave it blank.");
            String price = sc.nextLine();
            if (String.valueOf(price).trim().length() > 0) {
                product.setProductPrice(Float.parseFloat(price));
            }

            Display.printCategories(CategoryDao.getAllCategories());
            System.out.println("Enter category_id if want to update else leave it blank.");
            String category = sc.nextLine();
            if (String.valueOf(category).trim().length() > 0) {
                if (!CategoryDao.checkIfCategoryExists(Integer.parseInt(category))) {
                    System.out.println("Category doesn't exist.");
                    return;
                }
                product.setProductCategoryId(Integer.parseInt(category));
            }

            System.out.println("Enter discount if want to update else leave it blank.");
            String discount = sc.nextLine();
            if (String.valueOf(discount).trim().length() > 0) {
                product.setProductDiscount(Float.parseFloat(discount));
            }

            System.out.println("Enter brand if want to update else leave it blank.");
            String brand = sc.nextLine();
            if (brand.trim().length() > 0) {
                product.setProductBrand(brand);
            }

            if (ProductDao.checkIfProductSpecificationsExistsForGivenProduct(id)) {
                boolean continueUpdate = true;
                do {
                    System.out.println("Do you want to add/update/delete product specifications ?");
                    System.out.println("1. Yes");
                    System.out.println("Other. No");
                    int choice = sc.nextInt();
                    sc.nextLine();

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

            /*System.out.println("Enter choice of attribute you want to update: ");
            System.out.println("1. Product Title.");
            System.out.println("2. Description.");
            System.out.println("3. Price.");
            System.out.println("4. Category.");
            System.out.println("5. Discount.");
            System.out.println("6. Brand.");
            System.out.println("7. Specifications.");

            int choice = sc.nextInt();
            sc.nextLine();

            final int PRODUCT_TITLE = 1;
            final int PRODUCT_DESCRIPTION = 2;
            final int PRODUCT_PRICE = 3;
            final int PRODUCT_CATEGORY = 4;
            final int PRODUCT_DISCOUNT = 5;
            final int PRODUCT_BRAND = 6;
            final int PRODUCT_SPECIFICATIONS = 7;

            switch (choice) {
                case PRODUCT_TITLE:
                    UpdateProductV2.updateProduct(id, PRODUCT_TITLE);
                    break;
                case
            }*/
            /*switch (choice) {
                case PRODUCT_TITLE:
                    UpdateProductTitle updateProductTitle = new UpdateProductTitle();
                    updateProductTitle.updateProductTitle(id);
                    break;
                case PRODUCT_DESCRIPTION:
                    UpdateProductDescription updateProductDescription = new UpdateProductDescription();
                    updateProductDescription.updateProductDescription(id);
                    break;
                case PRODUCT_PRICE:
                    UpdateProductPrice updateProductPrice = new UpdateProductPrice();
                    updateProductPrice.updateProductPrice(id);
                    break;
                case PRODUCT_CATEGORY:
                    UpdateProductCategory updateProductCategory = new UpdateProductCategory();
                    updateProductCategory.updateProductCategory(id);
                    break;
                case PRODUCT_DISCOUNT:
                    UpdateProductDiscount updateProductDiscount = new UpdateProductDiscount();
                    updateProductDiscount.updateProductDiscount(id);
                    break;
                case PRODUCT_BRAND:
                    UpdateProductBrand updateProductBrand = new UpdateProductBrand();
                    updateProductBrand.updateProductBrand(id);
                    break;
                case PRODUCT_SPECIFICATIONS:
                    UpdateProductSpecifications updateProductSpecifications = new UpdateProductSpecifications();
                    updateProductSpecifications.updateProductSpecifications(id);
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }*/
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void displayCrudOptions(int productId) {
        final int ADD = 1;
        final int UPDATE = 2;
        final int DELETE = 3;

        System.out.println("Please select one option: ");
        System.out.println("1. Add specification");
        System.out.println("2. Update specification");
        System.out.println("3. Delete specification");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case ADD:
                AddSpecification.addSpecification(productId);
                break;
            case UPDATE:
                UpdateProductSpecifications.updateProductSpecifications(productId);
                break;
            case DELETE:
                DeleteSpecification.deleteSpecification(productId);
                break;
            default:
                System.out.println("Invalid choice selected.");
                displayCrudOptions(productId);
                break;
        }
    }
}
