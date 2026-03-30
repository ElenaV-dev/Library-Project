package com.my_library.validator;

import com.my_library.exception.ValidationException;
import com.my_library.model.Author;

public class AuthorValidator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int MAX_LIFE_YEARS_LENGTH = 25;
    private static final String LIFE_YEARS_REGEX = "\\d{4}(-\\d{4}|-)?";

    public static void validate(Author author) throws ValidationException {

        if (author == null) {
            throw new ValidationException("Author is null");
        }

        String lastName = author.getLastName();
        String firstName = author.getFirstName();
        String lifeYears = author.getLifeYears();

        if (lastName == null || lastName.isBlank()) {
            throw new ValidationException("Last name is required");
        }

        if (lastName.length() > MAX_STRING_LENGTH) {
            throw new ValidationException("Last name is too long");
        }

        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("First name is required");
        }

        if (firstName.length() > MAX_STRING_LENGTH) {
            throw new ValidationException("First name is too long");
        }

        if (lifeYears != null
                && (lifeYears.length() > MAX_LIFE_YEARS_LENGTH
                || !lifeYears.matches(LIFE_YEARS_REGEX))) {
            throw new ValidationException("Life years is invalid");
        }
    }
}
