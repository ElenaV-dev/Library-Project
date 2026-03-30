package com.my_library.database.dao.interfaces;

import com.my_library.exception.DaoException;
import com.my_library.model.ReaderCard;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReaderCardDAO extends GenericDAO<ReaderCard, Long> {

   Optional<ReaderCard>  findByCardNumber(String cardNumber) throws DaoException;

    List<ReaderCard> findByIssueDate(Date issueDate) throws DaoException;
}
