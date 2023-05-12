package com.narola.onlineshopping.service.product.productOperations;

import com.narola.onlineshopping.service.cart.CartManager;
import com.narola.onlineshopping.service.order.OrderManager;
import com.narola.onlineshopping.service.product.ProductManager;
import com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder.SortByPrice;
import com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder.SortByPriceAsc;
import com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder.ViewProductByCategory;
import com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder.ViewProductById;
import com.narola.onlineshopping.OnlineShoppingApplication;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.enums.UserRoles;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.system.ProgramTerminator;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class ShowProducts {

    public static void viewProducts() {
        try {
            if (!ProductDao.doProductsExists()) {
                System.out.println("No products are present.");
                return;
            }

            System.out.println("Select the way of viewing products: ");
            System.out.println("1. View all products.");
            System.out.println("2. View all by highest price.");
            System.out.println("3. View all by lowest price.");
            System.out.println("4. View products by category.");
            System.out.println("5. View Product by id.");
            if (LoggedInUser.currentUser.getUserRoleId() == UserRoles.ADMIN.getValue()) {
                System.out.println("6. Back");
            } else {
                System.out.println("6. My Cart");
                System.out.println("7. My Orders");
                System.out.println("8. Logout");
            }
            System.out.println("0. Exit");

            int choice = InputHandler.getIntInput();
            switch (choice) {
                case VIEW_ALL_PRODUCTS:
                    Display.printProducts(ProductDao.getALlProducts());
                    break;
                case VIEW_PRODUCTS_BY_HIGHEST_PRICE:
                    SortByPrice sortByPrice = new SortByPrice(ProductDao.getALlProducts());
                    Display.printProducts(sortByPrice.sortByPrice());
                    break;
                case VIEW_PRODUCTS_BY_LOWEST_PRICE:
                    SortByPriceAsc sortByPriceAsc = new SortByPriceAsc(ProductDao.getALlProducts());
                    Display.printProducts(sortByPriceAsc.sortByPriceAsc());
                    break;
                case VIEW_PRODUCTS_BY_CATEGORY:
                    ViewProductByCategory viewProductByCategory = new ViewProductByCategory();
                    Display.printProducts(viewProductByCategory.viewProductByCategory());
                    break;
                case VIEW_PRODUCT_BY_ID:
                    ViewProductById.getProductById();
                    break;
                case MY_CART_OR_BACK:
                    if (LoggedInUser.currentUser.getUserRoleId() == UserRoles.CUSTOMER.getValue()) {
                        CartManager.displayCartOptions();
                    } else {
                        ProductManager.handleProductManagement();
                    }
                    break;
                case MY_ORDERS:
                    if (LoggedInUser.currentUser.getUserRoleId() == UserRoles.CUSTOMER.getValue()) {
                        OrderManager.handleOrderManagement();
                    } else {
                        System.out.println("Please enter valid choice.");
                    }
                    break;
                case CUSTOMER_LOGOUT:
                    if (LoggedInUser.currentUser.getUserRoleId() == UserRoles.CUSTOMER.getValue()) {
                        OnlineShoppingApplication.main(null);
                    } else {
                        System.out.println("Please enter valid choice.");
                    }
                    break;
                case EXIT:
                    ProgramTerminator.exit();
                default:
                    System.out.println("Please enter valid choice.");
                    break;
            }
            viewProducts();
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
