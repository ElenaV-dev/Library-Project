package com.my_library.validator;

import com.my_library.model.Book;

import java.time.LocalDate;

public class BookValidator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int MAX_ISBN_LENGTH = 20;

    public static boolean isValid(Book book) {

        int currentYear = LocalDate.now().getYear();

        if (book == null) {
            return false;
        }

        if (book.getTitle() == null || book.getTitle().isBlank() || book.getTitle().length() > MAX_STRING_LENGTH) {
            return false;
        }

        if (book.getIsbn() == null || book.getIsbn().isBlank() || book.getIsbn().length() > MAX_ISBN_LENGTH) {
            return false;
        }

        if (book.getYear() != null && (book.getYear() < 0 || book.getYear() > currentYear)) {
            return false;
        }

        if (book.getPublisher() != null && book.getPublisher().length() > MAX_STRING_LENGTH) {
            return false;
        }
        return true;
    }
}
