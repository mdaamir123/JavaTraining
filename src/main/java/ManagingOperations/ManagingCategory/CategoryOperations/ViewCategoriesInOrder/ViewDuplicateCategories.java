package ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewDuplicateCategories {
    List<List<String>> list;
    List<String> duplicateList = new ArrayList<>();
    List<String> editedList = new ArrayList<>();

    public ViewDuplicateCategories(List<List<String>> list) {
        this.list = list;
    }

    public List<String> getDuplicates() {
        for (int i = 0; i < list.size(); i++) {
            var item = list.get(i).get(1);
            String[] str = item.split(" ");
            if(str.length > 1) {
                item = "";
                for (int j = 0; j < str.length; j++)
                item = item+str[j];
            }
            item = item.toLowerCase();
            if(!editedList.contains(item)) {
                editedList.add(item);
            }
            else {
                duplicateList.add(item.toUpperCase());
            }
        }

        return duplicateList.stream().distinct().collect(Collectors.toList());
    }
}
