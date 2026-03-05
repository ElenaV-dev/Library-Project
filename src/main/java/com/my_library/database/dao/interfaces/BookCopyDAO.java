package com.my_library.database.dao.interfaces;

import com.my_library.model.BookCopy;
import com.my_library.model.CopyStatus;

import java.util.List;

public interface BookCopyDAO extends GenericDAO<BookCopy, Long> {

    List<BookCopy> findByBookId(Long bookId);

    List<BookCopy> findByInventoryNumber(Integer inventoryNumber);

    List<BookCopy> findByStatus(CopyStatus status);

}
