package com.narola.onlineshopping.service.category.categoryOperations;

import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.service.category.CategoryManager;
import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.validation.Validation;

public class DeleteCategory {
    public static void deleteCategory() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No category available !!!");
                CategoryManager.handleCategoryManagement();
            } else {
                Display.printCategories(CategoryDao.getAllCategories());

                int categoryId = getCategoryId();

                if (!CategoryDao.doCategoryExists(categoryId)) {
                    System.out.println("Category not present with id " + categoryId);
                    deleteCategory();
                } else {
                    CategoryDao.deleteCategory(categoryId);
                    System.out.println("Category successfully deleted !!!");
                    CategoryManager.handleCategoryManagement();
                }
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
            deleteCategory();
        } catch (Exception e) {
            e.printStackTrace();
            deleteCategory();
        }
    }

    private static int getCategoryId() {
        System.out.println("Please enter the id of the category you want to delete: ");
        int categoryId;
        try {
            categoryId = InputHandler.getIntInput();

            if (Validation.isEmpty(String.valueOf(categoryId))) {
                System.out.println("Category id cannot be empty.");
                categoryId = getCategoryId();
            }

            if (categoryId <= 0) {
                System.out.println("Category id cannot be less than or equal or 0.");
                categoryId = getCategoryId();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            categoryId = getCategoryId();
        }
        return categoryId;
    }
}
