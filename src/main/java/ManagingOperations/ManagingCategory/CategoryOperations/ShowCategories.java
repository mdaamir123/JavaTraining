package ManagingOperations.ManagingCategory.CategoryOperations;

import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.*;
import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories.PrintCategories;
import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories.PrintDuplicateCategories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowCategories {
    private Connection con;
    private Scanner sc;
    public ShowCategories(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void viewCategories() {
        try {
            String query = "select * from category";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No categories found.");
                con.close();
                return;
            }

            rs.previous();
            List<List<String>> resultSet = new ArrayList<>();
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(rs.getInt(1)));
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                resultSet.add(list);
            }
            con.close();

            System.out.println("Select the way of viewing categories: ");
            System.out.println("1. Latest added.");
            System.out.println("2. In alphabetical order.");
            System.out.println("3. Show only duplicate categories.");
            System.out.println("4. Show categories in the retrieved order.");

            int choice = sc.nextInt();
            List<List<String>> finalList = new ArrayList<>();
            List<String> duplicateCategoriesList = new ArrayList<>();
            PrintCategories printCategory = new PrintCategories();
            switch (choice) {
                case 1:
                    SortByDate obj1 = new SortByDate(resultSet);
                    finalList = obj1.sortByDate();
                    printCategory.printCategories(finalList);
                    break;
                case 2:
                    SortAlphabetically obj2 = new SortAlphabetically(resultSet);
                    finalList = obj2.sortAlphabetically();
                    printCategory.printCategories(finalList);
                    break;
                case 3:
                    ViewDuplicateCategories obj3 = new ViewDuplicateCategories(resultSet);
                    duplicateCategoriesList = obj3.getDuplicates();
                    PrintDuplicateCategories printDuplicateCategories = new PrintDuplicateCategories(duplicateCategoriesList);
                    printDuplicateCategories.printDuplicateCategories();
                    break;
                case 4:
                    printCategory.printCategories(resultSet);
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
