package com.my_library.model;

import java.time.LocalDate;
import java.util.Objects;

public class Loan {

    private Long id;
    private Long bookCopyId;
    private Long readerId;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(Long id, Long bookCopyId, Long readerId, LocalDate loanDate, LocalDate returnDate) {
        this.id = id;
        this.bookCopyId = bookCopyId;
        this.readerId = readerId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(Long bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id) && Objects.equals(bookCopyId, loan.bookCopyId) && Objects.equals(readerId, loan.readerId) && Objects.equals(loanDate, loan.loanDate) && Objects.equals(returnDate, loan.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookCopyId, readerId, loanDate, returnDate);
    }
}
