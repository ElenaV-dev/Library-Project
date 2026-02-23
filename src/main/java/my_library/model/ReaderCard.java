package java.my_library.model;

import java.time.LocalDate;
import java.util.Objects;

public class ReaderCard {

    private Long readerId;
    private String cardNumber;
    private LocalDate issueDate;
    private LocalDate expirationDate;

    public ReaderCard() {
    }

    public ReaderCard(Long readerId, String cardNumber, LocalDate issueDate, LocalDate expirationDate) {
        this.readerId = readerId;
        this.cardNumber = cardNumber;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReaderCard that = (ReaderCard) o;
        return Objects.equals(readerId, that.readerId) && Objects.equals(cardNumber, that.cardNumber) && Objects.equals(issueDate, that.issueDate) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(readerId, cardNumber, issueDate, expirationDate);
    }
}
