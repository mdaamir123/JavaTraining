package ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories;

import java.util.List;

public class PrintCategories {
    public void printCategories(List<List<String>> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("ID: " + list.get(i).get(0) +
                    " CATEGORY_NAME: " + list.get(i).get(1) +
                    " DATE_ADDED: " + list.get(i).get(2));
        }
    }
}
