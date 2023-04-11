package comparators;

import model.Category;
import java.util.Comparator;

public class SortCategoryByDate implements Comparator<Category> {
    @Override
    public int compare(Category o1, Category o2) {
        return o1.getCreatedOn().compareTo(o2.getCreatedOn());
    }
}
