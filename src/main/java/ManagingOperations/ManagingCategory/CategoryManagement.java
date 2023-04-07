package ManagingOperations.ManagingCategory;

import ManagingOperations.ManagingCategory.CategoryOperations.AddCategory;
import ManagingOperations.ManagingCategory.CategoryOperations.ShowCategories;
import ManagingOperations.ManagingCategory.CategoryOperations.UpdateCategory;

import java.util.Scanner;

public class CategoryManagement {

    Scanner sc = new Scanner(System.in);
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
                    ShowCategories obj1 = new ShowCategories();
                    obj1.viewCategories();
                    break;
                case 2:
                    AddCategory obj2 = new AddCategory();
                    obj2.addCategory();
                    break;
                case 3:
                    UpdateCategory obj3 = new UpdateCategory();
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
