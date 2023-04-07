package ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories;

import java.util.List;

public class PrintDuplicateCategories {

    public static void printDuplicateCategories(List<String> duplicateCategoriesList) {
        if (duplicateCategoriesList.isEmpty()) {
            System.out.println("There are no duplicate categories. ");
            return;
        }

        for (int i = 0; i < duplicateCategoriesList.size(); i++) {
            System.out.println("CATEGORY_NAME: " + duplicateCategoriesList.get(i));
        }
    }
}
