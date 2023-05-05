package com.narola.onlineshopping.service.category.categoryOperations;

import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.input.TakeInput;

public class UpdateCategory {

    public static void updateCategory() {
        try {

            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories found.");
                return;
            }

            // TODO : Change class name to Display. It will have logic of displaying anything to console.
            Display.printCategories(CategoryDao.getAllCategories());

            System.out.println("Enter id of category you want to update: ");
            int id = TakeInput.getIntInput();

            if (!CategoryDao.doCategoryExists(id)) {
                System.out.println("Please enter correct id.");
                return;
            }

            System.out.println("Enter new category name: ");
            String newCategory = TakeInput.getStrInput();

            CategoryDao.updateCategory(id, newCategory);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
