package com.narola.onlineshopping.service.category.categoryOperations;

import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.TakeInput;

public class AddCategory {

    public static void addCategory() {
        System.out.println("Please enter new category name: ");
        String newCategory = TakeInput.getStrInput();
        //TODO : Take input here. Not in DAO. and pass data to dao method
        try {
            CategoryDao.addCategory(newCategory);
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Successfully added.");
    }

}
