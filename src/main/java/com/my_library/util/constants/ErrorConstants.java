package com.my_library.util.constants;

public final class ErrorConstants {

    public static final String FAILED_TO_CONNECT_TO_THE_DATABASE = "Failed to connect to the database. " +
            "Check your connection parameters";
    public static final String ERROR_CONNECTING_TO_THE_DATABASE = "Error connecting to the database";
    public static final String ERROR_CREATING_CONNECTION = "Error creating connection";
    public static final String FAILED_TO_FILL_CONNECTION_POOL = "Failed to fill connection pool";
    public static final String CONNECTION_WAS_INTERRUPTED = "Connection was interrupted";
    public static final String CONNECTION_FAILED = "Connection failed";
    public static final String FAILED_TO_CREATE = "Creating %s failed, no %s";
    public static final String FAILED_TO_UPDATE = "Updating %s failed, no %s";
    public static final String FAILED_TO_DELETE = "Deleting %s failed, no %s";
    public static final String BOOK_ID_NULL = "Book ID must not be null for delete";
    public static final String AUTHOR_ID_NULL = "Author ID must not be null for delete";
    public static final String FAILED_TO_ADD = "Adding %s failed, no %s";
    public static final String USER_ID_NULL = "User ID must not be null for delete";
    public static final String BOOK_COPY_NULL = "Book copy is null";
    public static final String BOOK_COPY_ID_NULL = "Book copy ID must not be null for delete";
    public static final String BOOK_NULL = "Book is null";
    public static final String AUTHOR_NULL = "Author is null";
    public static final String USER_NULL = "User is null";
    public static final String LOAN_NULL = "Loan is null";
    public static final String LOAN_ID_NULL = "Loan ID must not be null for delete";
    public static final String READER_CARD_NULL = "Reader card is null";
    public static final String READER_CARD_ID_NULL = "Reader card ID must not be null for delete";

    private ErrorConstants() {
    }
}
