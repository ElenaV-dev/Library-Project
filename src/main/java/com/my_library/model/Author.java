package com.my_library.model;

import java.util.Objects;
import java.util.UUID;

public class Author {

    private UUID uuid;
    private String lastName;
    private String firstName;
    private String lifeYears;

    public Author() {
    }

    public Author(UUID uuid, String lastName, String firstName, String lifeYears) {
        this.uuid = uuid;
        this.lastName = lastName;
        this.firstName = firstName;
        this.lifeYears = lifeYears;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLifeYears() {
        return lifeYears;
    }

    public void setLifeYears(String lifeYears) {
        this.lifeYears = lifeYears;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(uuid, author.uuid) && Objects.equals(lastName, author.lastName) && Objects.equals(firstName, author.firstName) && Objects.equals(lifeYears, author.lifeYears);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, lastName, firstName, lifeYears);
    }
}
