package com.score_keeper.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "player")
public class Player {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private LocalDate birth_day;

    @DBRef
    private Club club;

    @DBRef
    private Category category;

    public Player(String firstName, String lastName, LocalDate birth_day) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth_day = birth_day;
    }

    public String getId() {
        return id;
    }



    public String getFirstName() {
        return firstName + " " + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(LocalDate birth_day) {
        this.birth_day = birth_day;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
