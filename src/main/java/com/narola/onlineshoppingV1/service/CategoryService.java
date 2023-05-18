package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.comparator.SortCategoryByDate;
import com.narola.onlineshoppingV1.dao.CategoryDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.manager.CategoryManager;
import com.narola.onlineshoppingV1.model.Category;
import com.narola.onlineshoppingV1.validation.InputValidator;
import com.narola.onlineshoppingV1.view.CategoryView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {
    private CategoryView categoryView = new CategoryView();

    public void addCategory() {
        String newCategory = categoryView.getCategoryNameFromUser();
        try {
            CategoryDao.addCategory(newCategory);
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        categoryView.displaySuccessMessage("Successfully added.");
    }

    public void updateCategory() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                categoryView.displayErrorMessage("No categories found.");
                return;
            }

            Display.printCategories(CategoryDao.getAllCategories());

            int id = categoryView.getCategoryIdToUpdate();

            if (!CategoryDao.doCategoryExists(id)) {
                categoryView.displayErrorMessage("Please enter correct id.");
                return;
            }

            String newCategory = categoryView.getCategoryNameFromUser();

            CategoryDao.updateCategory(id, newCategory);
            categoryView.displaySuccessMessage("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCategory() {
        CategoryManager categoryManager = new CategoryManager();
        try {
            if (!CategoryDao.doCategoriesExists()) {
                categoryView.displayErrorMessage("No category available !!!");
                categoryManager.handleCategoryManagement();
            } else {
                Display.printCategories(CategoryDao.getAllCategories());

                int categoryId = getCategoryId();

                if (!CategoryDao.doCategoryExists(categoryId)) {
                    categoryView.displayErrorMessage("Category not present with id " + categoryId);
                    deleteCategory();
                } else {
                    CategoryDao.deleteCategory(categoryId);
                    categoryView.displaySuccessMessage("Category successfully deleted !!!");
                    categoryManager.handleCategoryManagement();
                }
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
            deleteCategory();
        } catch (Exception e) {
            e.printStackTrace();
            deleteCategory();
        }
    }

    private int getCategoryId() {
        int categoryId = categoryView.getCategoryIdToDelete();
        try {
            if (InputValidator.isEmpty(String.valueOf(categoryId))) {
                categoryView.displayErrorMessage("Category id cannot be empty.");
                categoryId = getCategoryId();
            }

            if (categoryId <= 0) {
                categoryView.displayErrorMessage("Category id cannot be less than or equal or 0.");
                categoryId = getCategoryId();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            categoryId = getCategoryId();
        }
        return categoryId;
    }

    public void getCategoriesByLatestDate() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories available !!!");
                return;
            }
            List<Category> categoryList = com.narola.onlineshoppingV1.dao.CategoryDao.getAllCategories();
            Collections.sort(categoryList, new SortCategoryByDate());
            Display.printCategories(categoryList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getCategoriesAlphabetically() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories available !!!");
                return;
            }
            List<Category> categoryList = com.narola.onlineshoppingV1.dao.CategoryDao.getAllCategories();
            Collections.sort(categoryList);
            Display.printCategories(categoryList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void getCategoriesInAddedOrder() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories available !!!");
                return;
            }
            List<Category> categoryList = com.narola.onlineshoppingV1.dao.CategoryDao.getAllCategories();
            Display.printCategories(categoryList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    private List<String> getDuplicateCategories(List<Category> categoryList) {
        List<String> duplicateList = new ArrayList<>();
        List<String> editedList = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            String item = categoryList.get(i).getCategoryName();
            String[] str = item.split(" ");
            if (str.length > 1) {
                item = "";
                for (int j = 0; j < str.length; j++)
                    item = item + str[j];
            }
            item = item.toLowerCase();
            if (!editedList.contains(item)) {
                editedList.add(item);
            } else {
                duplicateList.add(item.toUpperCase());
            }
        }
        return duplicateList.stream().distinct().collect(Collectors.toList());
    }

    public void viewDuplicateCategories() {
        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories available !!!");
                return;
            }

            Display.printDuplicateCategories(getDuplicateCategories(CategoryDao.getAllCategories()));
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

    }
}
