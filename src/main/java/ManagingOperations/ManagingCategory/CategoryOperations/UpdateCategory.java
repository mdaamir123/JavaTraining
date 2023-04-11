package ManagingOperations.ManagingCategory.CategoryOperations;

import display.Display;
import dao.CategoryDao;
import java.util.Scanner;

public class UpdateCategory {
    Scanner sc = new Scanner(System.in);

    public void updateCategory() {
        boolean isCategoryTableEmpty = CategoryDao.checkIfCategoriesExists();
        if (!isCategoryTableEmpty) {
            System.out.println("No categories found.");
            return;
        }

        // TODO : Change class name to Display. It will have logic of displaying anything to console.
        Display.printCategories(CategoryDao.getAllCategories());

        System.out.println("Enter id of category you want to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        if(!CategoryDao.checkIfCategoryExists(id)) {
            System.out.println("Please enter correct id.");
            return;
        }
        
        System.out.println("Enter new category name: ");
        String newCategory = sc.nextLine();
        CategoryDao.updateCategory(id, newCategory);
        System.out.println("Successfully updated.");

    }
}
