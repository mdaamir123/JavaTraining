package com.narola.onlineshoppingV1.manager;

import com.narola.onlineshoppingV1.OnlineShoppingApplication;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.Menu;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class AdminManager {
    private Menu menu = new Menu();
    private CategoryManager categoryManager = new CategoryManager();
    private ProductManager productManager = new ProductManager();

    public void adminOptions() {
        int selectedMenuID = menu.displayMenuAndSelect();
        switch (selectedMenuID) {
            case MENU_CATEGORY_MANAGEMENT:
                categoryManager.handleCategoryManagement();
                break;
            case MENU_PRODUCT_MANAGEMENT:
                productManager.handleProductManagement();
                break;
            case ADMIN_LOGOUT:
                OnlineShoppingApplication.main(null);
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Enter valid input.");
                adminOptions();
                break;
        }
    }
}
