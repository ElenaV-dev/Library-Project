package com.my_library.service.interfaces;

import com.my_library.exception.ServiceException;
import com.my_library.model.Book;

import java.util.List;

public interface BookService extends GenericService<Book, Long> {

    List<Book> findByTitle(String title) throws ServiceException;
}
