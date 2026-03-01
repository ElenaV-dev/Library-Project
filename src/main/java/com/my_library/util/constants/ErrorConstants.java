package com.my_library.util.constants;

public final class ErrorConstants {
    public static final String FAILED_TO_CONNECT_TO_THE_DATABASE = "Failed to connect to the database. " +
            "Check your connection parameters";
    public static final String ERROR_CONNECTING_TO_THE_DATABASE = "Error connecting to the database";
    public static final String ERROR_CREATING_CONNECTION = "Error creating connection";
    public static final String FAILED_TO_FILL_CONNECTION_POOL = "Failed to fill connection pool";
    public static final String CONNECTION_WAS_INTERRUPTED = "Connection was interrupted";
    public static final String CONNECTION_FAILED = "Connection failed";

    private ErrorConstants() {
    }
}
