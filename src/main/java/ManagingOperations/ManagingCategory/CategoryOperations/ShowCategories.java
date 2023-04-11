package ManagingOperations.ManagingCategory.CategoryOperations;

import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.*;
import display.Display;
import dao.CategoryDao;
import model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowCategories {

    Scanner sc = new Scanner(System.in);

    public void viewCategories(int choice) {

        boolean isCategoryTableEmpty = CategoryDao.checkIfCategoriesExists();
        if (!isCategoryTableEmpty) {
            return;
        }

        List<Category> finalList = new ArrayList<>();
        List<String> duplicateCategoriesList = new ArrayList<>();

        List<Category> resultSet = CategoryDao.getAllCategories();
        switch (choice) {
            case 1:
                SortByDate obj1 = new SortByDate(resultSet);
                finalList = obj1.sortByDate();
                Display.printCategories(finalList);
                break;
            case 2:
                SortAlphabetically obj2 = new SortAlphabetically(resultSet);
                finalList = obj2.sortAlphabetically();
                Display.printCategories(finalList);
                break;
            case 3:
                ViewDuplicateCategories obj3 = new ViewDuplicateCategories(resultSet);
                duplicateCategoriesList = obj3.getDuplicates();
                Display.printDuplicateCategories(duplicateCategoriesList);
                break;
            case 4:
                Display.printCategories(resultSet);
                break;
            default:
                System.out.println("Please enter valid input.");
                break;
        }

    }

    public void displayMenu() {
        System.out.println("Select the way of viewing categories: ");
        System.out.println("1. Latest added.");
        System.out.println("2. In alphabetical order.");
        System.out.println("3. Show only duplicate categories.");
        System.out.println("4. Show categories in the retrieved order.");

        int choice = sc.nextInt();
        viewCategories(choice);
    }

}
