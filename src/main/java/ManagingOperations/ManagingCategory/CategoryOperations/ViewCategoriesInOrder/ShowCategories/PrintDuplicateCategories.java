package ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories;

import java.util.List;

public class PrintDuplicateCategories {
    List<String> duplicateCategoriesList;

    public PrintDuplicateCategories(List<String> duplicateCategoriesList) {
        this.duplicateCategoriesList = duplicateCategoriesList;
    }

    public void printDuplicateCategories() {
        if (duplicateCategoriesList.isEmpty()) {
            System.out.println("There are no duplicate categories. ");
            return;
        }

        for (int i = 0; i < duplicateCategoriesList.size(); i++) {
            System.out.println("CATEGORY_NAME: " + duplicateCategoriesList.get(i));
        }
    }
}
