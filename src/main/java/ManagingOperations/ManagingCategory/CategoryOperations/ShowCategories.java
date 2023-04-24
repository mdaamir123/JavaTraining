package ManagingOperations.ManagingCategory.CategoryOperations;

import ManagingOperations.ManagingCategory.CategoryManagement;
import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.*;
import exception.DAOLayerException;
import display.Display;
import dao.CategoryDao;
import model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowCategories {

    Scanner sc = new Scanner(System.in);

    public void viewCategories(int choice) {

        boolean isCategoryTableEmpty = false;
        try {
            isCategoryTableEmpty = CategoryDao.checkIfCategoriesExists();

            if (!isCategoryTableEmpty) {
                System.out.println("No categories available !!!");
                return;
            }

            List<Category> finalList = new ArrayList<>();
            List<String> duplicateCategoriesList = new ArrayList<>();

            List<Category> resultSet = null;

            resultSet = CategoryDao.getAllCategories();

            final int SORT_BY_LATEST_ADDED = 1;
            final int SORT_ALPHABETICALLY = 2;
            final int SHOW_DUPLICATE_CATEGORIES = 3;
            final int SHOW_CATEGORIES_IN_ADDED_ORDER = 4;
            final int BACK = 5;
            switch (choice) {
                case SORT_BY_LATEST_ADDED:
                    SortByDate obj1 = new SortByDate(resultSet);
                    finalList = obj1.sortByDate();
                    Display.printCategories(finalList);
                    break;
                case SORT_ALPHABETICALLY:
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
                case BACK:
                    CategoryManagement.handleCategoryManagement();
                default:
                    System.out.println("Please enter valid input.");
                    viewCategories(choice);
                    break;
            }

        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void displayMenu() {
        System.out.println("Select the way of viewing categories: ");
        System.out.println("1. Latest added.");
        System.out.println("2. In alphabetical order.");
        System.out.println("3. Show only duplicate categories.");
        System.out.println("4. Show categories in the retrieved order.");
        System.out.println("5. Back");

        int choice = sc.nextInt();
        viewCategories(choice);
    }

}
