package ManagingOperations.ManagingCategory;

import ManagingOperations.ManagingCategory.CategoryOperations.AddCategory;
import ManagingOperations.ManagingCategory.CategoryOperations.ShowCategories;
import ManagingOperations.ManagingCategory.CategoryOperations.UpdateCategory;

import java.sql.Connection;
import java.util.Scanner;

public class CategoryManagement {
    private Connection con;
    private Scanner sc;

    public CategoryManagement(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }
    public void handleCategoryManagement() {
        try {
            System.out.println("Please choose one option: ");
            System.out.println("1. View");
            System.out.println("2. Add");
            System.out.println("3. Update");
            int x = sc.nextInt();
            sc.nextLine();

            switch (x) {
                case 1:
                    ShowCategories obj1 = new ShowCategories(con);
                    obj1.viewCategories();
                    break;
                case 2:
                    AddCategory obj2 = new AddCategory(con, sc);
                    obj2.addCategory();
                    break;
                case 3:
                    UpdateCategory obj3 = new UpdateCategory(con, sc);
                    obj3.updateCategory();
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
