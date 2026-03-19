package com.my_library.validator;

import com.my_library.model.BookCopy;

public class BookCopyValidator {

    public static boolean isValid(BookCopy bookCopy) {

        if (bookCopy == null) {
            return false;
        }

        Integer inventoryNumber = bookCopy.getInventoryNumber();
        Long bookId = bookCopy.getBookId();

        if (inventoryNumber == null || inventoryNumber <= 0) {
            return false;
        }

        if (bookId == null || bookId <= 0) {
            return false;
        }
        return true;
    }
}
