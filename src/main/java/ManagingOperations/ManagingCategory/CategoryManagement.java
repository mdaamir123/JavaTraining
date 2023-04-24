package ManagingOperations.ManagingCategory;

import ManagingOperations.ManagingCategory.CategoryOperations.AddCategory;
import ManagingOperations.ManagingCategory.CategoryOperations.ShowCategories;
import ManagingOperations.ManagingCategory.CategoryOperations.UpdateCategory;
import ManagingOperations.OnlineShopping;

import java.util.Scanner;

public class CategoryManagement {

    public static void handleCategoryManagement() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please choose one option: ");
            System.out.println("1. View");
            System.out.println("2. Add");
            System.out.println("3. Update");
            System.out.println("4. Back");
            int choice = sc.nextInt();
            sc.nextLine();

            final int VIEW = 1;
            final int ADD = 2;
            final int UPDATE = 3;
            final int BACK = 4;
            switch (choice) {
                case VIEW:
                    ShowCategories obj1 = new ShowCategories();
                    obj1.displayMenu();
                    break;
                case ADD:
                    AddCategory obj2 = new AddCategory();
                    obj2.addCategory();
                    break;
                case UPDATE:
                    UpdateCategory obj3 = new UpdateCategory();
                    obj3.updateCategory();
                    break;
                case BACK:
                    OnlineShopping.displayMenuBasedOnAccess();
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }
            handleCategoryManagement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
