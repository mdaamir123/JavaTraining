package com.narola.onlineshopping.service.category;

import com.narola.onlineshopping.service.category.categoryOperations.AddCategory;
import com.narola.onlineshopping.service.category.categoryOperations.DeleteCategory;
import com.narola.onlineshopping.service.category.categoryOperations.ShowCategories;
import com.narola.onlineshopping.service.category.categoryOperations.UpdateCategory;
import com.narola.onlineshopping.OnlineShoppingApplication;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.menu.CrudManagementMenu;
import com.narola.onlineshopping.system.ExitSystem;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class CategoryManager {

    public static void handleCategoryManagement() {
        try {
            CrudManagementMenu.crudOptions();
            int choice = TakeInput.getIntInput();

            switch (choice) {
                case VIEW_CATEGORIES_MENU:
                    ShowCategories.displayMenu();
                    break;
                case ADD_CATEGORY:
                    AddCategory.addCategory();
                    break;
                case UPDATE_CATEGORY:
                    UpdateCategory.updateCategory();
                    break;
                case DELETE_CATEGORY:
                    DeleteCategory.deleteCategory();
                    break;
                case BACK_TO_ADMIN_OPTIONS:
                    OnlineShoppingApplication.displayMenuBasedOnAccess();
                    break;
                case EXIT:
                    ExitSystem.exit();
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }
            handleCategoryManagement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
