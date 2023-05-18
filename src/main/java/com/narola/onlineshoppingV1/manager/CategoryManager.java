package com.narola.onlineshoppingV1.manager;

import com.narola.onlineshoppingV1.service.CategoryService;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.CategoryView;
import com.narola.onlineshoppingV1.view.Menu;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class CategoryManager {
    private CategoryView categoryView = new CategoryView();
    private CategoryService categoryService = new CategoryService();
    private Menu menu = new Menu();

    public void handleCategoryManagement() {
        try {
            int choice = categoryView.displayAndTakeInputForCategoryCrudOperation();
            switch (choice) {
                case VIEW_CATEGORIES_MENU:
                    handleViewCategoryManagement();
                    break;
                case ADD_CATEGORY:
                    categoryService.addCategory();
                    break;
                case UPDATE_CATEGORY:
                    categoryService.updateCategory();
                    break;
                case DELETE_CATEGORY:
                    categoryService.deleteCategory();
                    break;
                case BACK_TO_ADMIN_OPTIONS:
                    menu.displayMenuBasedOnAccess();
                    break;
                case EXIT:
                    ProgramTerminator.exit();
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }
            handleCategoryManagement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleViewCategoryManagement() {
        int choice = categoryView.getCategoryViewChoiceFromUser();
        switch (choice) {
            case SORT_CATEGORIES_BY_LATEST_ADDED:
                categoryService.getCategoriesByLatestDate();
                break;
            case SORT_CATEGORIES_ALPHABETICALLY:
                categoryService.getCategoriesAlphabetically();
                break;
            case SHOW_DUPLICATE_CATEGORIES:
                categoryService.viewDuplicateCategories();
                break;
            case SHOW_CATEGORIES_IN_ADDED_ORDER:
                categoryService.getCategoriesInAddedOrder();
                break;
            case BACK_TO_CATEGORY_MANAGEMENT:
                handleCategoryManagement();
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Please enter valid input.");
                handleViewCategoryManagement();
                break;
        }
        handleViewCategoryManagement();
    }
}
