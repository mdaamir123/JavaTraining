package ManagingOperations.ManagingCategory.CategoryOperations;

import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories.PrintCategories;
import dao.CategoryDao;
import java.util.Scanner;

public class UpdateCategory {
    Scanner sc = new Scanner(System.in);

    public void updateCategory() {
        boolean isCategoryTableEmpty = CategoryDao.checkIfCategoriesExists();
        if (!isCategoryTableEmpty) {
            return;
        }

        PrintCategories.printCategories(CategoryDao.getAllCategories());

        System.out.println("Enter id of category you want to update: ");
        CategoryDao.updateCategory();

    }
}
