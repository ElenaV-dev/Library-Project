package com.my_library.validator;

import com.my_library.exception.ValidationException;
import com.my_library.model.BookCopy;

public class BookCopyValidator {

    public static void validate(BookCopy bookCopy) throws ValidationException {

        if (bookCopy == null) {
            throw new ValidationException("Book copy is null");
        }

        Integer inventoryNumber = bookCopy.getInventoryNumber();
        Long bookId = bookCopy.getBookId();

        if (inventoryNumber == null) {
            throw new ValidationException("Inventory number is required");
        }

        if (inventoryNumber <= 0) {
            throw new ValidationException("Inventory number must be positive");
        }

        if (bookId == null) {
            throw new ValidationException("Book id is required");
        }

        if (bookId <= 0) {
            throw new ValidationException("Book id must be positive");
        }
    }
}
