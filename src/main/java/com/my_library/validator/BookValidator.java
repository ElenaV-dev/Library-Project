package com.my_library.validator;

import com.my_library.exception.ValidationException;
import com.my_library.model.Book;

import java.time.LocalDate;

public class BookValidator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int MAX_ISBN_LENGTH = 20;
    private static final String ISBN_REGEX = "[0-9\\-]+";
    private static final Integer MIN_YEAR = 1450;

    public static void validate(Book book) throws ValidationException {

        if (book == null) {
            throw new ValidationException("Book is null");
        }

        String title = book.getTitle();
        String isbn = book.getIsbn();
        Integer year = book.getYear();
        String publisher = book.getPublisher();

        Integer currentYear = LocalDate.now().getYear();

        validateTitle(title);

        if (isbn == null || isbn.isBlank()) {
            throw new ValidationException("ISBN is required");
        }

        if (isbn.length() > MAX_ISBN_LENGTH) {
            throw new ValidationException("ISBN is too long");
        }

        if (!isbn.matches(ISBN_REGEX)) {
            throw new ValidationException("ISBN format is invalid");
        }

        if (year != null && (year < MIN_YEAR || year > currentYear)) {
            throw new ValidationException("Year is invalid");
        }

        if (publisher != null && publisher.length() > MAX_STRING_LENGTH) {
            throw new ValidationException("Publisher is too long");
        }
    }

    public static void validateTitle(String title) throws ValidationException {

        if (title == null || title.isBlank()) {
            throw new ValidationException("Title is required");
        }

        if (title.length() > MAX_STRING_LENGTH) {
            throw new ValidationException("Title is too long");
        }
    }
}
