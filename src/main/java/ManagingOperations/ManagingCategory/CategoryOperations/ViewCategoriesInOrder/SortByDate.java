package ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder;

import java.util.List;

public class SortByDate {
    List<List<String>> list;

    public SortByDate(List<List<String>> list) {
        this.list = list;
    }

    public  List<List<String>>  sortByDate() {
        for(int i = 0; i < list.size(); i++) {
            for(int j = 0; j < list.size()-i-1; j++) {
                if(list.get(j).get(1).compareTo(list.get(j+1).get(2)) > 0) {
                    var temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                }
            }
        }
        return list;
    }
}
