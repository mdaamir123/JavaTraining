package com.narola.onlineshoppingV1.manager;

import com.narola.onlineshoppingV1.OnlineShoppingApplication;
import com.narola.onlineshoppingV1.dao.ProductDao;
import com.narola.onlineshoppingV1.enums.UserRoles;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.service.OrderService;
import com.narola.onlineshoppingV1.service.ProductService;
import com.narola.onlineshoppingV1.session.LoggedInUser;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.Menu;
import com.narola.onlineshoppingV1.view.ProductView;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class ProductManager {
    private ProductView productView = new ProductView();
    private Menu menu = new Menu();
    private OrderService orderService = new OrderService();
    private CartManager cartManager = new CartManager();

    public void handleProductManagement() {
        ProductService productService = new ProductService();
        if (LoggedInUser.currentUser.getUserRole().getUserRoleId() == UserRoles.ADMIN.getValue()) {
            try {
                int choice = productView.displayAndTakeInputForProductCrudOperation();
                switch (choice) {
                    case VIEW_PRODUCTS_MENU:
                        handleProductsViewManagement();
                        break;
                    case ADD_PRODUCT:
                        productService.addProduct();
                        break;
                    case UPDATE_PRODUCT:
                        productService.updateProduct();
                        break;
                    case DELETE_PRODUCT:
                        productService.deleteProduct();
                        break;
                    case BACK_TO_MENU_BASED_ON_ACCESS:
                        menu.displayMenuBasedOnAccess();
                        break;
                    case EXIT:
                        ProgramTerminator.exit();
                    default:
                        System.out.println("Please enter valid input.");
                        break;
                }
                handleProductManagement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            handleProductsViewManagement();
        }
    }

    public void handleProductsViewManagement() {
        ProductService productService = new ProductService();
        try {
            if (!ProductDao.doProductsExists()) {
                productView.displayErrorMessage("No products are present.");
                return;
            }
            int choice = productView.getProductViewChoiceFromUser();
            switch (choice) {
                case VIEW_ALL_PRODUCTS:
                    productService.getAllProducts();
                    break;
                case VIEW_PRODUCTS_BY_HIGHEST_PRICE:
                    productService.getProductsByHighestPrice();
                    break;
                case VIEW_PRODUCTS_BY_LOWEST_PRICE:
                    productService.getProductsByLowestPrice();
                    break;
                case VIEW_PRODUCTS_BY_CATEGORY:
                    handleProductViewByCategoryManagement();
                    break;
                case VIEW_PRODUCT_BY_ID:
                    productService.getProductById();
                    break;
                case MY_CART_OR_BACK:
                    if (LoggedInUser.currentUser.getUserRoleId() == UserRoles.CUSTOMER.getValue()) {
                        cartManager.handleCartManagement();
                    } else {
                        handleProductManagement();
                    }
                    break;
                case MY_ORDERS:
                    if (LoggedInUser.currentUser.getUserRoleId() == UserRoles.CUSTOMER.getValue()) {
                        orderService.handleOrderManagement();
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
            handleProductsViewManagement();
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleProductViewByCategoryManagement() {
        ProductService productService = new ProductService();
        int choice = productView.getProductViewByCategoryChoice();
        switch (choice) {
            case VIEW_PRODUCTS_BY_CATEGORY_AND_IN_RETRIEVED_ORDER:
                productService.getProductByCategoryInRetrievedOrder();
                break;
            case VIEW_PRODUCTS_BY_CATEGORY_AND_BY_HIGHEST_PRICE:
                productService.getProductsByCategorySortedByHighestPrice();
                break;
            case VIEW_PRODUCTS_BY_CATEGORY_AND_BY_LOWEST_PRICE:
                productService.getProductsByCategorySortedByLowestPrice();
                break;
            case BACK_TO_VIEW_PRODUCTS_MENU:
                handleProductsViewManagement();
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Invalid choice selected.");
                handleProductViewByCategoryManagement();
                break;
        }
    }
}
