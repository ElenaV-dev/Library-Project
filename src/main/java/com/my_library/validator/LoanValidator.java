package com.my_library.validator;

import com.my_library.exception.ValidationException;
import com.my_library.model.Loan;

import java.time.LocalDate;

public class LoanValidator {

    private static final LocalDate FIRST_DATE = LocalDate.of(1900, 1, 1);

    public static void validate(Loan loan) throws ValidationException {

        if (loan == null) {
            throw new ValidationException("Loan is null");
        }

        Long bookCopyId = loan.getBookCopyId();
        Long userId = loan.getUserId();
        LocalDate loanDate = loan.getLoanDate();
        LocalDate returnDate = loan.getReturnDate();

        LocalDate currentDate = LocalDate.now();

        if (bookCopyId == null) {
            throw new ValidationException("Book copy id is null");
        }

        if (bookCopyId <= 0) {
            throw new ValidationException("Book copy id must be positive");
        }

        if (userId == null) {
            throw new ValidationException("User id is null");
        }

        if (userId <= 0) {
            throw new ValidationException("User id must be positive");
        }

        if (loanDate == null) {
            throw new ValidationException("Loan date is null");
        }

        if (loanDate.isAfter(currentDate)) {
            throw new ValidationException("Loan date cannot be in the future");
        }

        if (loanDate.isBefore(FIRST_DATE)) {
            throw new ValidationException("Loan date is too early");
        }

        if (returnDate != null) {
            if (returnDate.isBefore(loanDate)) {
                throw new ValidationException("Return date cannot be before loan date");
            }
            if (returnDate.isBefore(FIRST_DATE)) {
                throw new ValidationException("Return date is too early");
            }
        }
    }
}
