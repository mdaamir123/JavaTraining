package com.narola.onlineshoppingV1.view;

import com.narola.onlineshoppingV1.dao.CategoryDao;
import com.narola.onlineshoppingV1.dao.ProductDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.enums.UserRoles;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.input.InputHandler;
import com.narola.onlineshoppingV1.session.LoggedInUser;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class ProductView {
    public int displayAndTakeInputForProductCrudOperation() {
        System.out.println("Please choose one option: ");
        System.out.println("1. View");
        System.out.println("2. Add");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Back");
        System.out.println("0. Exit");
        return InputHandler.getIntInput();
    }

    public void displaySuccessMessage(String message) {
        System.out.println(message);
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public String getProductTitleFromUser() {
        System.out.println("Enter product title: ");
        return InputHandler.getStrInput();
    }

    public String getProductDescriptionFromUser() {
        System.out.println("Enter description: ");
        return InputHandler.getStrInput();
    }

    public float getProductPriceFromUser() {
        System.out.println("Enter price: ");
        return InputHandler.getFloatInput();
    }

    public int getCategoryIdFromUser() {
        System.out.println("Enter category_id from below: ");
        try {
            Display.printCategories(CategoryDao.getAllCategories());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return InputHandler.getIntInput();
    }

    public float getDiscountFromUser() {
        System.out.println("Enter discount: ");
        return InputHandler.getFloatInput();
    }

    public String getBrandFromUser() {
        System.out.println("Enter brand: ");
        return InputHandler.getStrInput();
    }

    public String getAttributeNameFromUser() {
        System.out.println("Add attribute name: ");
        return InputHandler.getStrInput();
    }

    public String getAttributeValueFromUser() {
        System.out.println("Add attribute value: ");
        return InputHandler.getStrInput();
    }

    public int getProductIdToDeleteFromUser() {
        try {
            Display.printProducts(ProductDao.getALlProducts());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter the id of the product you want to delete: ");
        return InputHandler.getIntInput();
    }

    public int getProductIdToUpdateFromUser() {
        try {
            Display.printProducts(ProductDao.getALlProducts());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Enter id of product you want to update: ");
        return InputHandler.getIntInput();
    }

    public String getProductTitleToUpdateFromUser() {
        System.out.println("Enter product_title if want to update else leave it blank.");
        return InputHandler.getStrInput(true);
    }

    public String getProductDescriptionToUpdateFromUser() {
        System.out.println("Enter product_description if want to update else leave it blank.");
        return InputHandler.getStrInput(true);
    }

    public String getProductPriceToUpdateFromUser() {
        System.out.println("Enter price if want to update else leave it blank.");
        return InputHandler.getStrInput(true);
    }

    public String getCategoryIdToUpdateFromUser() {
        try {
            Display.printCategories(CategoryDao.getAllCategories());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Enter category_id if want to update else leave it blank.");
        return InputHandler.getStrInput(true);
    }

    public String getProductDiscountToUpdateFromUser() {
        System.out.println("Enter discount if want to update else leave it blank.");
        return InputHandler.getStrInput(true);
    }

    public String getProductBrandToUpdateFromUser() {
        System.out.println("Enter brand if want to update else leave it blank.");
        return InputHandler.getStrInput(true);
    }

    public int getProductViewChoiceFromUser() {
        System.out.println("Select the way of viewing products: ");
        System.out.println(VIEW_ALL_PRODUCTS + ". View all products.");
        System.out.println(VIEW_PRODUCTS_BY_HIGHEST_PRICE + ". View all by highest price.");
        System.out.println(VIEW_PRODUCTS_BY_LOWEST_PRICE + ". View all by lowest price.");
        System.out.println(VIEW_PRODUCTS_BY_CATEGORY + ". View products by category.");
        System.out.println(VIEW_PRODUCT_BY_ID + ". View Product by id.");
        if (LoggedInUser.currentUser.getUserRoleId() == UserRoles.ADMIN.getValue()) {
            System.out.println(MY_CART_OR_BACK + ". Back");
        } else {
            System.out.println(MY_CART_OR_BACK + ". My Cart");
            System.out.println(MY_ORDERS + ". My Orders");
            System.out.println(CUSTOMER_LOGOUT + ". Logout");
        }
        System.out.println(EXIT + ". Exit");

        return InputHandler.getIntInput();
    }

    public int getProductViewByCategoryChoice() {
        try {
            Display.printCategories(CategoryDao.getAllCategories());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter choice of viewing products: ");
        System.out.println(VIEW_PRODUCTS_BY_CATEGORY_AND_IN_RETRIEVED_ORDER + ": In retrieved order");
        System.out.println(VIEW_PRODUCTS_BY_CATEGORY_AND_BY_HIGHEST_PRICE + ": By highest price");
        System.out.println(VIEW_PRODUCTS_BY_CATEGORY_AND_BY_LOWEST_PRICE + ": By lowest price");
        System.out.println(BACK_TO_VIEW_PRODUCTS_MENU + ": Back");
        System.out.println(EXIT + ": Exit");
        return InputHandler.getIntInput();
    }

    public int getProductIdFromUserToViewProduct() {
        System.out.println("Enter id of product.");
        return InputHandler.getIntInput();
    }

    public int getCategoryIdFromTheUserToViewProducts() {
        System.out.println("Enter id of the category: ");
        return InputHandler.getIntInput();
    }
}
