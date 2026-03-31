package com.my_library.service.factory;

import com.my_library.model.User;
import com.my_library.service.impl.*;
import com.my_library.service.interfaces.*;

public class FactoryService {

    private static final FactoryService instance = new FactoryService();
    private final BookService bookService = new BookServiceImpl();
    private final AuthorService authorService = new AuthorServiceImpl();
    private final BookCopyService bookCopyService = new BookCopyServiceImpl();
    private final UserService userService = new UserServiceImpl();
    private final LoanService loanService = new LoanServiceImpl();
    private final ReaderCardService readerCardService = new ReaderCardServiceImpl();

    private  FactoryService() {
    }

    public static FactoryService getInstance() {
        return instance;
    }

    public BookService getBookService() {
        return bookService;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public BookCopyService getBookCopyService() {
        return bookCopyService;
    }

    public UserService getUserService() {
        return userService;
    }

    public LoanService getLoanService() {
        return loanService;
    }

    public ReaderCardService getReaderCardService() {
        return readerCardService;
    }
}
