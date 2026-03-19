package com.my_library.validator;

import com.my_library.model.ReaderCard;

import java.time.LocalDate;

public class ReaderCardValidator {

    private static final int MAX_READER_CARD_NUMBER_LENGTH = 20;
    private static final LocalDate FIRST_DATE = LocalDate.of(1900, 1, 1);

    public static boolean isValid(ReaderCard readerCard) {

        if (readerCard == null) {
            return false;
        }

        Long readerId = readerCard.getReaderId();
        String cardNumber = readerCard.getCardNumber();
        LocalDate issueDate = readerCard.getIssueDate();
        LocalDate expirationDate = readerCard.getExpirationDate();

        LocalDate currentDate = LocalDate.now();

        if (readerId == null || readerId <= 0) {
            return false;
        }

        if (cardNumber == null || cardNumber.isBlank()
                || cardNumber.trim().length() > MAX_READER_CARD_NUMBER_LENGTH) {
            return false;
        }

        if (issueDate == null || issueDate.isAfter(currentDate)
                || issueDate.isBefore(FIRST_DATE)) {
            return false;
        }

        if (expirationDate != null && (expirationDate.isBefore(issueDate)
                || expirationDate.isBefore(FIRST_DATE))) {
            return false;
        }
        return true;
    }
}
