package com.score_keeper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "category")
public class Category {

    @Id
    private String id;

    @NotEmpty(message = "Description may not be empty")
    private String description;
    @NotNull(message = "Gender may not be empty")
    private Gender gender;
    @NotNull(message = "Age may not be empty")
    private int min_age;
    @NotNull(message = "Age may not be empty")
    private int max_age;

    public Category() {
    }

    public Category(String description, Gender gender, int min_age, int max_age) {
        this.description = description;
        this.gender = gender;
        this.min_age = min_age;
        this.max_age = max_age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }
}
