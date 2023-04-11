package ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder;

import model.Category;

import java.util.Collections;
import java.util.List;

public class SortAlphabetically {
    List<Category> categories;

    //TODO : Will take List<Category>
    public SortAlphabetically(List<Category> categories) {
        this.categories = categories;
    }

    //TODO : Use comparable
    public List<Category> sortAlphabetically() {
        Collections.sort(categories);
        return categories;
    }
}
