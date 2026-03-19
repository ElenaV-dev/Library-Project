package com.my_library.validator;

import com.my_library.model.Loan;

import java.time.LocalDate;

public class LoanValidator {

    private static final LocalDate FIRST_DATE = LocalDate.of(1900, 1, 1);

    public static boolean isValid(Loan loan) {

        if (loan == null) {
            return false;
        }

        Long bookCopyId = loan.getBookCopyId();
        Long userId = loan.getUserId();
        LocalDate loanDate = loan.getLoanDate();
        LocalDate returnDate = loan.getReturnDate();

        LocalDate currentDate = LocalDate.now();

        if (bookCopyId == null || bookCopyId <= 0) {
            return false;
        }

        if (userId == null || userId <= 0) {
            return false;
        }

        if (loanDate == null || loanDate.isAfter(currentDate)
                || loanDate.isBefore(FIRST_DATE)) {
            return false;
        }

        if (returnDate != null && (returnDate.isBefore(loanDate))
                || returnDate.isBefore(FIRST_DATE)) {
            return false;
        }
        return true;
    }
}
