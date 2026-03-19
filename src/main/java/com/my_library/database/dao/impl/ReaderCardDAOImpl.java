package com.my_library.database.dao.impl;

import com.my_library.database.connection.ConnectionPool;
import com.my_library.database.dao.interfaces.ReaderCardDAO;
import com.my_library.model.Loan;
import com.my_library.model.ReaderCard;
import com.my_library.util.constants.ErrorConstants;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ReaderCardDAOImpl implements ReaderCardDAO {

    private final ConnectionPool connectionPool;

    private static final String SELECT_READER_CARD_BY_ID = "SELECT * FROM reader_cards WHERE reader_id = ?";
    private static final String SELECT_ALL_READER_CARDS = "SELECT * FROM reader_cards";
    private static final String ADD_READER_CARD = "INSERT INTO reader_cards (reader_id, card_number, issue_date, " +
            " expiration_date) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_READER_CARD = "UPDATE reader_cards SET card_number = ?, issue_date = ?, " +
            "expiration_date = ? WHERE reader_id = ?";
    private static final String DELETE_READER_CARD = "DELETE FROM reader_cards WHERE reader_id = ?";

    public ReaderCardDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Optional<ReaderCard> findById(Long readerId) throws SQLException {

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_READER_CARD_BY_ID)) {
                stmt.setLong(1, readerId);

                try (ResultSet resultSet = stmt.executeQuery()) {

                    if (resultSet.next()) {
                        return Optional.of(mapRow(resultSet));
                    }
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<ReaderCard> findAll() throws SQLException {

        Connection connection = null;
        List<ReaderCard> listReaderCards = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(SELECT_ALL_READER_CARDS);
                 ResultSet resultSet = stmt.executeQuery()) {

                while (resultSet.next()) {
                    listReaderCards.add(mapRow(resultSet));
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
        return listReaderCards;
    }

    @Override
    public void save(ReaderCard readerCard) throws SQLException {

        if (readerCard == null) {
            throw new IllegalArgumentException(ErrorConstants.READER_CARD_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(ADD_READER_CARD)) {
                stmt.setLong(1, readerCard.getReaderId());
                stmt.setString(2, readerCard.getCardNumber());
                stmt.setObject(3, readerCard.getIssueDate(), Types.DATE);
                stmt.setObject(4, readerCard.getExpirationDate(), Types.DATE);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_CREATE, "reader card", "rows affected");
                    throw new SQLException(error);
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public void update(ReaderCard readerCard) throws SQLException {

        if (readerCard == null) {
            throw new IllegalArgumentException(ErrorConstants.READER_CARD_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_READER_CARD)) {
                stmt.setString(1, readerCard.getCardNumber());
                stmt.setObject(2, readerCard.getIssueDate(), Types.DATE);
                stmt.setObject(3, readerCard.getExpirationDate(), Types.DATE);
                stmt.setLong(4, readerCard.getReaderId());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_UPDATE, "reader card", "rows affected");
                    throw new SQLException(error);
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public void deleteById(Long readerId) throws SQLException {

        if (readerId == null) {
            throw new IllegalArgumentException(ErrorConstants.READER_CARD_ID_NULL);
        }

        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            try (PreparedStatement stmt = connection.prepareStatement(DELETE_READER_CARD)) {
                stmt.setLong(1, readerId);

                int affectedRows = stmt.executeUpdate();

                if (affectedRows == 0) {
                    String error = String.format(ErrorConstants.FAILED_TO_DELETE, "reader card", "rows affected");
                    throw new SQLException(error);
                }
            }
        } finally {
            if (connection != null) {
                connectionPool.returnConnection(connection);
            }
        }
    }

    @Override
    public ReaderCard findByCardNumber(String cardNumber) {
        return null;
    }

    @Override
    public List<ReaderCard> findByIssueDate(Date issueDate) {
        return List.of();
    }

    private ReaderCard mapRow(ResultSet resultSet) throws SQLException {
        ReaderCard readerCard = new ReaderCard();
        readerCard.setReaderId(resultSet.getLong("reader_id"));
        readerCard.setCardNumber(resultSet.getString("card_number"));
        readerCard.setIssueDate(resultSet.getObject("issue_date", LocalDate.class));
        readerCard.setExpirationDate(resultSet.getObject("expiration_date", LocalDate.class));
        return readerCard;
    }
}
