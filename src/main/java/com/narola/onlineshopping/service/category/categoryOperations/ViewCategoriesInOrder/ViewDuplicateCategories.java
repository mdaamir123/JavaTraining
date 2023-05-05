package com.narola.onlineshopping.service.category.categoryOperations.ViewCategoriesInOrder;

import com.narola.onlineshopping.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ViewDuplicateCategories {
    List<Category> categories;
    List<String> duplicateList = new ArrayList<>();
    List<String> editedList = new ArrayList<>();

    //TODO : Will take List<Category>
    public ViewDuplicateCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<String> getDuplicates() {
        for (int i = 0; i < categories.size(); i++) {
            String item = categories.get(i).getCategoryName();
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
