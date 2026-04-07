package com.my_library.util.constants;

public class UriConstants {


    public static final String CONTROLLER = "controller";

    public static final String PARAM_ENTITY = "entity";
    public static final String PARAM_ACTION = "action";

    public static final String ENTITY_BOOK = "book";
    public static final String ENTITY_AUTHOR = "author";
    public static final String ENTITY_BOOK_COPY = "bookCopy";
    public static final String ENTITY_USER = "user";
    public static final String ENTITY_READER_CARD = "readerCard";
    public static final String ENTITY_LOAN = "loan";

    public static final String ACTION_FIND_BY_ID = "findById";
    public static final String ACTION_FIND_ALL = "findAll";
    public static final String ACTION_CREATE = "create";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DELETE = "delete";

    public static final String BOOK_FIND_ALL_URI = CONTROLLER + "?" + PARAM_ENTITY + "=" + ENTITY_BOOK +
            "&" + PARAM_ACTION + "=" + ACTION_FIND_ALL;

    public static final String AUTHOR_FIND_ALL_URI = CONTROLLER + "?" + PARAM_ENTITY + "=" + ENTITY_AUTHOR +
            "&" + PARAM_ACTION + "=" + ACTION_FIND_ALL;

    public static final String BOOK_COPY_FIND_ALL_URI = CONTROLLER + "?" + PARAM_ENTITY + "=" + ENTITY_BOOK_COPY +
            "&" + PARAM_ACTION + "=" + ACTION_FIND_ALL;

    public static final String LOAN_FIND_ALL_URI = CONTROLLER + "?" + PARAM_ENTITY + "=" + ENTITY_LOAN +
            "&" + PARAM_ACTION + "=" + ACTION_FIND_ALL;

    public static final String USER_FIND_ALL_URI = CONTROLLER + "?" + PARAM_ENTITY + "=" + ENTITY_USER +
            "&" + PARAM_ACTION + "=" + ACTION_FIND_ALL;

    public static final String READER_CARD_FIND_ALL_URI = CONTROLLER + "?" + PARAM_ENTITY + "=" + ENTITY_READER_CARD +
            "&" + PARAM_ACTION + "=" + ACTION_FIND_ALL;

    private UriConstants() {
    }
}
