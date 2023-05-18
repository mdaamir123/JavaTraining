package com.narola.onlineshoppingV1.view;

import com.narola.onlineshoppingV1.input.InputHandler;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class CategoryView {
    public int displayAndTakeInputForCategoryCrudOperation() {
        System.out.println("Please choose one option: ");
        System.out.println("1. View");
        System.out.println("2. Add");
        System.out.println("3. Update");
        System.out.println("4. Delete");
        System.out.println("5. Back");
        System.out.println("0. Exit");
        return InputHandler.getIntInput();
    }

    public String getCategoryNameFromUser() {
        System.out.println("Enter new category name: ");
        return InputHandler.getStrInput();
    }

    public int getCategoryIdToUpdate() {
        System.out.println("Enter id of category you want to update: ");
        return InputHandler.getIntInput();
    }

    public int getCategoryIdToDelete() {
        System.out.println("Please enter the id of the category you want to delete: ");
        return InputHandler.getIntInput();
    }

    public void displaySuccessMessage(String message) {
        System.out.println(message);
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public int getCategoryViewChoiceFromUser() {
        System.out.println("Select the way of viewing categories: ");
        System.out.println(SORT_CATEGORIES_BY_LATEST_ADDED + ". Latest added.");
        System.out.println(SORT_CATEGORIES_ALPHABETICALLY + ". In alphabetical order.");
        System.out.println(SHOW_DUPLICATE_CATEGORIES + ". Show only duplicate categories.");
        System.out.println(SHOW_CATEGORIES_IN_ADDED_ORDER + ". Show categories in the retrieved order.");
        System.out.println(BACK_TO_CATEGORY_MANAGEMENT + ". Back");
        System.out.println(EXIT + ". Exit");
        return InputHandler.getIntInput();
    }
}
