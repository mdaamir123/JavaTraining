package com.narola.onlineshopping.service.category.categoryOperations;

import com.narola.onlineshopping.service.category.CategoryManager;
import com.narola.onlineshopping.service.category.categoryOperations.ViewCategoriesInOrder.SortAlphabetically;
import com.narola.onlineshopping.service.category.categoryOperations.ViewCategoriesInOrder.SortByDate;
import com.narola.onlineshopping.service.category.categoryOperations.ViewCategoriesInOrder.ViewDuplicateCategories;
import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.InputHandler;
import com.narola.onlineshopping.system.ProgramTerminator;

import com.narola.onlineshopping.model.Category;

import java.util.List;

import static com.narola.onlineshopping.constant.AppConstant.*;

public class ShowCategories {

    public static void viewCategories(int choice) {

        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories available !!!");
                return;
            }

            List<Category> finalList;
            List<String> duplicateCategoriesList;
            List<Category> resultSet;
            resultSet = CategoryDao.getAllCategories();

            switch (choice) {
                case SORT_CATEGORIES_BY_LATEST_ADDED:
                    SortByDate obj1 = new SortByDate(resultSet);
                    finalList = obj1.sortByDate();
                    Display.printCategories(finalList);
                    break;
                case SORT_CATEGORIES_ALPHABETICALLY:
                    SortAlphabetically obj2 = new SortAlphabetically(resultSet);
                    finalList = obj2.sortAlphabetically();
                    Display.printCategories(finalList);
                    break;
                case SHOW_DUPLICATE_CATEGORIES:
                    ViewDuplicateCategories obj3 = new ViewDuplicateCategories(resultSet);
                    duplicateCategoriesList = obj3.getDuplicates();
                    Display.printDuplicateCategories(duplicateCategoriesList);
                    break;
                case SHOW_CATEGORIES_IN_ADDED_ORDER:
                    Display.printCategories(resultSet);
                    break;
                case BACK_TO_CATEGORY_MANAGEMENT:
                    CategoryManager.handleCategoryManagement();
                    break;
                case EXIT:
                    ProgramTerminator.exit();
                default:
                    System.out.println("Please enter valid input.");
                    displayMenu();
                    break;
            }
            displayMenu();
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void displayMenu() {
        System.out.println("Select the way of viewing categories: ");
        System.out.println(SORT_CATEGORIES_BY_LATEST_ADDED+". Latest added.");
        System.out.println(SORT_CATEGORIES_ALPHABETICALLY+". In alphabetical order.");
        System.out.println(SHOW_DUPLICATE_CATEGORIES+". Show only duplicate categories.");
        System.out.println(SHOW_CATEGORIES_IN_ADDED_ORDER+". Show categories in the retrieved order.");
        System.out.println(BACK_TO_CATEGORY_MANAGEMENT+". Back");
        System.out.println(EXIT+". Exit");

        viewCategories(InputHandler.getIntInput());
    }

}
