package com.narola.onlineshopping.validation;

public class InputValidator {

    public static boolean isEmpty(String attribute) {
        return attribute.isEmpty();
    }

    public static boolean isValidDateFormat(String date) {
        String DATE_FORMAT_REGEX = "^\\d{4}/\\d{2}$";
        return date.matches(DATE_FORMAT_REGEX);
    }

    public static boolean isInputInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInputFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
