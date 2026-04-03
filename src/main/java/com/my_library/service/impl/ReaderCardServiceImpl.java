package com.my_library.service.impl;

import com.my_library.database.dao.factory.FactoryDAO;
import com.my_library.database.dao.interfaces.ReaderCardDAO;
import com.my_library.exception.DaoException;
import com.my_library.exception.ServiceException;
import com.my_library.exception.ValidationException;
import com.my_library.model.ReaderCard;
import com.my_library.service.interfaces.ReaderCardService;
import com.my_library.validator.ReaderCardValidator;

import java.util.List;
import java.util.Optional;

public class ReaderCardServiceImpl implements ReaderCardService {

    private final ReaderCardDAO readerCardDAO = FactoryDAO.getInstance().getReaderCardDAO();

    @Override
    public Optional<ReaderCard> findById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid reader card id");
        }

        try {
            return readerCardDAO.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to find reader card", e);
        }
    }

    @Override
    public List<ReaderCard> findAll() throws ServiceException {

        try {
            return readerCardDAO.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find all reader cards", e);
        }
    }

    @Override
    public void save(ReaderCard readerCard) throws ServiceException {

        try {
            ReaderCardValidator.validate(readerCard);

            Optional<ReaderCard> existing = readerCardDAO.findByCardNumber(readerCard.getCardNumber());

            if (existing.isPresent()) {
                throw new ServiceException("Reader card with this card number already exists");
            }

            readerCardDAO.save(readerCard);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to save reader card", e);
        }
    }

    @Override
    public void update(ReaderCard readerCard) throws ServiceException {

        if (readerCard.getReaderId() == null || readerCard.getReaderId() <= 0) {
            throw new ServiceException("Invalid reader card id");
        }

        try {
            ReaderCardValidator.validate(readerCard);

            Optional<ReaderCard> current = readerCardDAO.findById(readerCard.getReaderId());

            if (current.isEmpty()) {
                throw new ServiceException("Reader card not found");
            }

            Optional<ReaderCard> existing = readerCardDAO.findByCardNumber(readerCard.getCardNumber());

            if (existing.isPresent() && !existing.get().getReaderId().equals(readerCard.getReaderId())) {
                throw new ServiceException("Reader card with this card number already exists");
            }
            readerCardDAO.update(readerCard);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update reader card", e);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {

        if (id == null || id <= 0) {
            throw new ServiceException("Invalid reader card id");
        }

        try {
            readerCardDAO.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete reader card", e);
        }
    }
}
