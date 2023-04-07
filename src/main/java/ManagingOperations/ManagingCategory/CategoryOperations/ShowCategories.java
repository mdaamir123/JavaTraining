package ManagingOperations.ManagingCategory.CategoryOperations;

import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.*;
import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories.PrintCategories;
import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories.PrintDuplicateCategories;
import config.DatabaseConfig;
import dao.CategoryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowCategories {

    Scanner sc = new Scanner(System.in);

    public void viewCategories() {
        boolean isCategoryTableEmpty = CategoryDao.checkIfCategoriesExists();
        if (!isCategoryTableEmpty) {
            return;
        }

        System.out.println("Select the way of viewing categories: ");
        System.out.println("1. Latest added.");
        System.out.println("2. In alphabetical order.");
        System.out.println("3. Show only duplicate categories.");
        System.out.println("4. Show categories in the retrieved order.");

        int choice = sc.nextInt();
        List<List<String>> finalList = new ArrayList<>();
        List<String> duplicateCategoriesList = new ArrayList<>();

        List<List<String>> resultSet = CategoryDao.getAllCategories();
        switch (choice) {
            case 1:
                SortByDate obj1 = new SortByDate(resultSet);
                finalList = obj1.sortByDate();
                PrintCategories.printCategories(finalList);
                break;
            case 2:
                SortAlphabetically obj2 = new SortAlphabetically(resultSet);
                finalList = obj2.sortAlphabetically();
                PrintCategories.printCategories(finalList);
                break;
            case 3:
                ViewDuplicateCategories obj3 = new ViewDuplicateCategories(resultSet);
                duplicateCategoriesList = obj3.getDuplicates();
                PrintDuplicateCategories.printDuplicateCategories(duplicateCategoriesList);
                break;
            case 4:
                PrintCategories.printCategories(resultSet);
                break;
            default:
                System.out.println("Please enter valid input.");
                break;
        }

    }

}
