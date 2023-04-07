package ManagingOperations.ManagingCategory.CategoryOperations;

import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories.PrintCategories;
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

        PrintCategories.printCategories(CategoryDao.getAllCategories());

        System.out.println("Enter id of category you want to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        if(!CategoryDao.checkIfCategoryExists(id)) {
            System.out.println("Please enter correct id.");
            return;
        }
        
        System.out.println("Enter updated category name: ");
        String newCategory = sc.nextLine();
        CategoryDao.updateCategory(id, newCategory);

    }
}
