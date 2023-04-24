package ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder;

import comparator.SortCategoryByDate;
import model.Category;

import java.util.Collections;
import java.util.List;

public class SortByDate {
    List<Category> categories;

    //TODO : Will take List<Category>
    public SortByDate(List<Category> categories) {
        this.categories = categories;
    }

    //TODO : Use comparator.
    public  List<Category>  sortByDate() {
        Collections.sort(categories, new SortCategoryByDate());
        return categories;
    }
}
