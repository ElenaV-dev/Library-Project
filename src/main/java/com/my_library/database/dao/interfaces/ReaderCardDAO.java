package com.my_library.database.dao.interfaces;

import com.my_library.model.ReaderCard;

import java.util.Date;
import java.util.List;

public interface ReaderCardDAO extends GenericDAO<ReaderCard, Long> {

    ReaderCard findByCardNumber(String cardNumber);

    List<ReaderCard> findByIssueDate(Date issueDate);
}
