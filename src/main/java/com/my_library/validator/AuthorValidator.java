package com.my_library.validator;

import com.my_library.model.Author;

public class AuthorValidator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int MAX_LIFE_YEARS_LENGTH = 25;
    private static final String LIFE_YEARS_REGEX = "\\d{4}(-\\d{4}|-)?";

    public static boolean isValid(Author author) {

        if (author == null) {
            return false;
        }

        String lastName = author.getLastName();
        String firstName = author.getFirstName();
        String lifeYears = author.getLifeYears();

        if (lastName == null || lastName.isBlank() || lastName.trim().length() > MAX_STRING_LENGTH) {
            return false;
        }

        if (firstName == null || firstName.isBlank() || firstName.trim().length() > MAX_STRING_LENGTH) {
            return false;
        }

        if (lifeYears != null
                && (lifeYears.trim().length() > MAX_LIFE_YEARS_LENGTH
                || !lifeYears.matches(LIFE_YEARS_REGEX)
                || lifeYears.isBlank())) {
            return false;
        }
        return true;
    }
}
