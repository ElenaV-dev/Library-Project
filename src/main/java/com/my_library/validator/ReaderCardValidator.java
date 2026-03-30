package com.my_library.validator;

import com.my_library.exception.ValidationException;
import com.my_library.model.ReaderCard;

import java.time.LocalDate;

public class ReaderCardValidator {

    private static final int MAX_READER_CARD_NUMBER_LENGTH = 20;
    private static final LocalDate FIRST_DATE = LocalDate.of(1900, 1, 1);

    public static void validate(ReaderCard readerCard) throws ValidationException {

        if (readerCard == null) {
            throw new ValidationException("Reader card is null");
        }

        Long readerId = readerCard.getReaderId();
        String cardNumber = readerCard.getCardNumber();
        LocalDate issueDate = readerCard.getIssueDate();
        LocalDate expirationDate = readerCard.getExpirationDate();

        LocalDate currentDate = LocalDate.now();

        if (readerId == null) {
            throw new ValidationException("Reader id is null");
        }

        if (readerId <= 0) {
            throw new ValidationException("Reader id must be positive");
        }

        if (cardNumber == null || cardNumber.isBlank()) {
            throw new ValidationException("Card number is required");
        }
        if (cardNumber.length() > MAX_READER_CARD_NUMBER_LENGTH) {
            throw new ValidationException("Card number is too long");
        }

        if (issueDate == null) {
            throw new ValidationException("Issue date is null");
        }

        if (issueDate.isAfter(currentDate)) {
            throw new ValidationException("Issue date cannot be in the future");
        }
        if (issueDate.isBefore(FIRST_DATE)) {
            throw new ValidationException("Issue date is too early");
        }

        if (expirationDate != null) {
            if (expirationDate.isBefore(issueDate)) {
                throw new ValidationException("Expiration date cannot be before issue date");
            }
            if (expirationDate.isBefore(FIRST_DATE)) {
                throw new ValidationException("Expiration date is too early");
            }
        }
    }
}
