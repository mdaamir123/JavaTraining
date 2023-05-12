package com.narola.onlineshopping.service.product;

import com.narola.onlineshopping.service.product.productOperations.AddProduct;
import com.narola.onlineshopping.service.product.productOperations.DeleteProduct;
import com.narola.onlineshopping.service.product.productOperations.ShowProducts;
import com.narola.onlineshopping.service.product.productOperations.UpdateProduct;
import com.narola.onlineshopping.OnlineShoppingApplication;
import com.narola.onlineshopping.enums.UserRoles;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.menu.CrudManagementMenu;
import com.narola.onlineshopping.session.LoggedInUser;
import com.narola.onlineshopping.system.ProgramTerminator;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class ProductManager {

    public static void handleProductManagement() {
        if (LoggedInUser.currentUser.getUserRole().getUserRoleId() == UserRoles.ADMIN.getValue()) {
            try {
                CrudManagementMenu.crudOptions();
                int choice = InputHandler.getIntInput();

                switch (choice) {
                    case VIEW_PRODUCTS_MENU:
                        ShowProducts.viewProducts();
                        break;
                    case ADD_PRODUCT:
                        AddProduct.addProduct();
                        break;
                    case UPDATE_PRODUCT:
                        UpdateProduct.updateProduct();
                        break;
                    case DELETE_PRODUCT:
                        DeleteProduct.deleteProduct();
                        break;
                    case BACK_TO_MENU_BASED_ON_ACCESS:
                        OnlineShoppingApplication.displayMenuBasedOnAccess();
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
            ShowProducts.viewProducts();
        }
    }

}
