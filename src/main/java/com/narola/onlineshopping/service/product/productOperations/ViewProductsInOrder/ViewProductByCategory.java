package com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder;

import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.service.product.productOperations.ShowProducts;
import com.narola.onlineshopping.comparator.SortProductsByPrice;
import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.model.Product;
import com.narola.onlineshopping.system.ProgramTerminator;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class ViewProductByCategory {
    List<Product> resultSet;
    List<Product> products = new ArrayList<>();

    public List<Product> viewProductByCategory() {

        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories available.");
                return null;
            }

            Display.printCategories(CategoryDao.getAllCategories());
            int choice = displayAndSelectViewingChoices();
            switch (choice) {
                case VIEW_PRODUCTS_BY_CATEGORY_AND_IN_RETRIEVED_ORDER:
                    resultSet = getProducts();
                    if (resultSet == null) {
                        return null;
                    }
                    if (resultSet.size() == 0) {
                        return products;
                    }
                    break;
                case VIEW_PRODUCTS_BY_CATEGORY_AND_BY_HIGHEST_PRICE:
                    resultSet = getProducts();
                    if (resultSet == null) {
                        return null;
                    }
                    if (resultSet.size() == 0) {
                        return products;
                    }
                    Collections.sort(resultSet, new SortProductsByPrice().reversed());
                    break;
                case VIEW_PRODUCTS_BY_CATEGORY_AND_BY_LOWEST_PRICE:
                    resultSet = getProducts();
                    if (resultSet == null) {
                        return null;
                    }
                    if (resultSet.size() == 0) {
                        return products;
                    }
                    Collections.sort(resultSet, new SortProductsByPrice());
                    break;
                case BACK_TO_VIEW_PRODUCTS_MENU:
                    ShowProducts.viewProducts();
                    break;
                case EXIT:
                    ProgramTerminator.exit();
                default:
                    System.out.println("Invalid choice selected.");
                    viewProductByCategory();
                    break;
            }


            for (Product product : resultSet) {
                products.add(product);
            }

            return products;
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products;
    }

    private int displayAndSelectViewingChoices() {
        System.out.println("Please enter choice of viewing products: ");
        System.out.println(VIEW_PRODUCTS_BY_CATEGORY_AND_IN_RETRIEVED_ORDER + ": In retrieved order");
        System.out.println(VIEW_PRODUCTS_BY_CATEGORY_AND_BY_HIGHEST_PRICE + ": By highest price");
        System.out.println(VIEW_PRODUCTS_BY_CATEGORY_AND_BY_LOWEST_PRICE + ": By lowest price");
        System.out.println(BACK_TO_VIEW_PRODUCTS_MENU + ": Back");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }

    private List<Product> getProducts() {
        System.out.println("Enter id of the category: ");
        int id = InputHandler.getIntInput();

        try {
            if (!CategoryDao.doCategoryExists(id)) {
                System.out.println("Please enter valid id.");
                return null;
            }

            resultSet = ProductDao.getProductsByCategory(id);
            return resultSet;
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
