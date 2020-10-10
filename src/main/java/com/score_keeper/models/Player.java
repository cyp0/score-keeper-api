package com.score_keeper.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@Document(collection = "player")
public class Player {
    @Id
    private String id;

    @NotEmpty(message = "First Name must not be empty")
    @Pattern(regexp = "^[\\p{L} .'-]+$" , message = "The name field may only contain letters")
    private String firstName;

    @NotEmpty(message = "Last Name must not be empty")
    @Pattern(regexp = "^[\\p{L} .'-]+$" , message = "The last name field may only contain letters")
    private String lastName;

    @NotNull(message = "Birth day must not be empty")
    private LocalDate birth_day;


    @DBRef
    @NotNull(message = "Club id must not be empty")
    private Club club;

    @DBRef
    @NotNull(message = "Category id must not be empty")
    private Category category;

    public Player(String firstName, String lastName, LocalDate birth_day) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth_day = birth_day;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
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
