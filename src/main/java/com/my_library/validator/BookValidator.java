package com.my_library.validator;

import com.my_library.model.Book;

import java.time.LocalDate;

public class BookValidator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int MAX_ISBN_LENGTH = 20;
    private static final String ISBN_REGEX = "[0-9\\-]+";
    private static final Integer MIN_YEAR = 1450;

    public static boolean isValid(Book book) {

        if (book == null) {
            return false;
        }

        String title = book.getTitle();
        String isbn = book.getIsbn();
        Integer year = book.getYear();
        String publisher = book.getPublisher();

        Integer currentYear = LocalDate.now().getYear();

        if (title == null || title.isBlank() || title.trim().length() > MAX_STRING_LENGTH) {
            return false;
        }

        if (isbn == null || isbn.isBlank() || isbn.trim().length() > MAX_ISBN_LENGTH
                || !isbn.matches(ISBN_REGEX)) {
            return false;
        }

        if (year != null && (year < MIN_YEAR || year > currentYear)) {
            return false;
        }

        if (publisher != null && (publisher.trim().length() > MAX_STRING_LENGTH
                || publisher.isBlank())) {
            return false;
        }
        return true;
    }
}
