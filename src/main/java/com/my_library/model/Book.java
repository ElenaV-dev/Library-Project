package com.my_library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book {

    private Long id;
    private String title;
    private Integer year;
    private String isbn;
    private String publisher;
    private List<Author> authors = new ArrayList<>();

    public Book() {
    }

    public Book(Long id, String title, Integer year, String isbn, String publisher) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(year, book.year)
                && Objects.equals(isbn, book.isbn)
                && Objects.equals(publisher, book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, isbn, publisher);
    }

}
