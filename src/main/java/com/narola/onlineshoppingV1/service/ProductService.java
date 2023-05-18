package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.comparator.SortProductsByPrice;
import com.narola.onlineshoppingV1.dao.CategoryDao;
import com.narola.onlineshoppingV1.dao.ProductDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.input.InputHandler;
import com.narola.onlineshoppingV1.manager.ProductManager;
import com.narola.onlineshoppingV1.manager.SpecificationManager;
import com.narola.onlineshoppingV1.model.Product;
import com.narola.onlineshoppingV1.model.Specification;
import com.narola.onlineshoppingV1.validation.InputValidator;
import com.narola.onlineshoppingV1.view.SpecificationView;
import com.narola.onlineshoppingV1.view.ProductView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    private ProductView productView = new ProductView();
    private SpecificationView specificationView = new SpecificationView();
    private ProductManager productManager = new ProductManager();
    private SpecificationManager specificationManager = new SpecificationManager();

    public void addProduct() {
        Scanner sc = new Scanner(System.in);
        try {
            if (!CategoryDao.doCategoriesExists()) {
                productView.displayErrorMessage("No categories present. Kindly add one before.");
                return;
            }

            Product product = new Product();

            product.setProductTitle(productView.getProductTitleFromUser());

            product.setProductDescription(productView.getProductDescriptionFromUser());

            product.setProductPrice(productView.getProductPriceFromUser());

            int category_id = productView.getCategoryIdFromUser();
            product.setProductCategoryId(category_id);

            if (!CategoryDao.doCategoryExists(category_id)) {
                productView.displayErrorMessage("Category id does not exists.");
                return;
            }

            product.setProductDiscount(productView.getDiscountFromUser());

            product.setProductBrand(productView.getBrandFromUser());

            System.out.println("Do you want to add specifications ?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int spec = InputHandler.getIntInput();
            List<Specification> specificationList = new ArrayList<>();
            if (spec == 1) {
                do {
                    Specification specification = new Specification();
                    specification.setSpecAttributeName(productView.getAttributeNameFromUser());
                    specification.setSpecAttributeValue(productView.getAttributeValueFromUser());
                    specificationList.add(specification);
                    System.out.println("Press q to quit: ");
                    char pressed = sc.next().charAt(0);
                    sc.nextLine();
                    if (pressed == 'q') {
                        spec = 0;
                    }
                } while (spec == 1);
            } else if (spec != 1) {
                System.out.println("No attributes added to product.");
            }

            product.setSpecificationList(specificationList);
            ProductDao.addProduct(product);
            productView.displaySuccessMessage("Product successfully inserted !!!");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteProduct() {
        try {
            if (!ProductDao.doProductsExists()) {
                productView.displayErrorMessage("No products exists !!!");
                productManager.handleProductManagement();
            } else {

                int productId = productView.getProductIdToDeleteFromUser();

                if (!ProductDao.doProductExists(productId)) {
                    productView.displayErrorMessage("Product not present with id " + productId);
                    deleteProduct();
                } else {
                    ProductDao.deleteProduct(productId);
                    productView.displaySuccessMessage("Product successfully deleted !!!");
                    productManager.handleProductManagement();
                }
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
            deleteProduct();
        } catch (Exception e) {
            e.printStackTrace();
            deleteProduct();
        }
    }

    public void updateProduct() {
        Product product;
        try {
            if (!ProductDao.doProductsExists()) {
                productView.displayErrorMessage("No products are present.");
                return;
            }

            int id = productView.getProductIdToUpdateFromUser();

            if (!ProductDao.doProductExists(id)) {
                productView.displayErrorMessage("ID not found.");
                return;
            }
            product = ProductDao.getProductById(id);
            product.setProductId(id);

            String product_title = productView.getProductTitleFromUser();
            if (!InputValidator.isEmpty(product_title)) {
                product.setProductTitle(product_title);
            }

            String description = productView.getProductDescriptionToUpdateFromUser();
            if (!InputValidator.isEmpty(description)) {
                product.setProductDescription(description);
            }

            String price = productView.getProductPriceToUpdateFromUser();
            while (!InputValidator.isEmpty(String.valueOf(price))) {
                if (InputValidator.isInputFloat(price)) {
                    product.setProductPrice(Float.parseFloat(price));
                    break;
                }
                productView.displayErrorMessage("Please enter valid input.");
                price = productView.getProductPriceToUpdateFromUser();
            }

            String category = productView.getCategoryIdToUpdateFromUser();
            while (!InputValidator.isEmpty(category)) {
                if (InputValidator.isInputInteger(category)) {
                    if (!CategoryDao.doCategoryExists(Integer.parseInt(category))) {
                        productView.displayErrorMessage("Category doesn't exist. Please enter valid category id.");
                        category = productView.getCategoryIdToUpdateFromUser();
                        continue;
                    }
                    product.setProductCategoryId(Integer.parseInt(category));
                    break;
                }
                productView.displayErrorMessage("Please enter valid input.");
                category = productView.getCategoryIdToUpdateFromUser();
            }

            String discount = productView.getProductDiscountToUpdateFromUser();
            while (!InputValidator.isEmpty(String.valueOf(discount))) {
                if (InputValidator.isInputFloat(discount)) {
                    product.setProductDiscount(Float.parseFloat(discount));
                    break;
                }
                productView.displayErrorMessage("Please enter valid input.");
                discount = productView.getProductDiscountToUpdateFromUser();
            }

            String brand = productView.getProductBrandToUpdateFromUser();
            if (!InputValidator.isEmpty(brand)) {
                product.setProductBrand(brand);
            }

            if (ProductDao.doProductSpecificationsExistsForGivenProduct(id)) {
                boolean continueUpdate = true;
                do {
                    int choice = specificationView.getChoiceFromUserIfWantToModifySpecification();

                    switch (choice) {
                        case 1:
                            specificationManager.handleSpecificationCrudOptions(id);
                            break;
                        default:
                            continueUpdate = false;
                            break;
                    }
                } while (continueUpdate);
            }
            ProductDao.updateProduct(product);
            productView.displaySuccessMessage("Successfully product updated.");

        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void getAllProducts() {
        try {
            Display.printProducts(ProductDao.getALlProducts());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getProductsByHighestPrice() {
        try {
            List<Product> productList = ProductDao.getALlProducts();
            Collections.sort(productList, new SortProductsByPrice().reversed());
            Display.printProducts(productList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getProductsByLowestPrice() {
        try {
            List<Product> productList = ProductDao.getALlProducts();
            Collections.sort(productList, new SortProductsByPrice());
            Display.printProducts(productList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getProductById() {
        try {
            int productId = productView.getProductIdFromUserToViewProduct();
            if (!ProductDao.doProductExists(productId)) {
                productView.displayErrorMessage("No such product exists !!!");
                return;
            }
            Display.printProductById(ProductDao.getProductById(productId));
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProductByCategoryInRetrievedOrder() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                productView.displayErrorMessage("No categories available.");
                return;
            }
            Display.printProducts(getProducts());

        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getProductsByCategorySortedByHighestPrice() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                productView.displayErrorMessage("No categories available.");
                return;
            }
            List<Product> productList = getProducts();
            Collections.sort(productList, new SortProductsByPrice().reversed());
            Display.printProducts(productList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getProductsByCategorySortedByLowestPrice() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                productView.displayErrorMessage("No categories available.");
                return;
            }
            List<Product> productList = getProducts();
            Collections.sort(productList, new SortProductsByPrice());
            Display.printProducts(productList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    private List<Product> getProducts() {
        int id = productView.getCategoryIdFromTheUserToViewProducts();
        try {
            if (!CategoryDao.doCategoryExists(id)) {
                productView.displayErrorMessage("Please enter valid id.");
                return Collections.emptyList();
            }
            List<Product> productList = ProductDao.getProductsByCategory(id);
            return productList;
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
