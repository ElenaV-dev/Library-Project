package com.my_library.model;

import java.util.Objects;

public class BookCopy {

    private Long id;
    private Long bookId;
    private Integer inventoryNumber;
    private String status;

    public BookCopy() {
    }

    public BookCopy(Long id, Long bookId, Integer inventoryNumber, String status) {
        this.id = id;
        this.bookId = bookId;
        this.inventoryNumber = inventoryNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(Integer inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookCopy bookCopy = (BookCopy) o;
        return Objects.equals(id, bookCopy.id) && Objects.equals(bookId, bookCopy.bookId) && Objects.equals(inventoryNumber, bookCopy.inventoryNumber) && Objects.equals(status, bookCopy.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, inventoryNumber, status);
    }
}
