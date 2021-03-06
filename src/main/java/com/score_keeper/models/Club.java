package com.score_keeper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Document(collection = "club")
public class Club  {
    @Id
    private String id;
    @Pattern(regexp = "^[\\p{L} .'-]+$" , message = "The name field may only contain letters")
    @NotEmpty(message = "Name must not be empty")
    private String name;


    public Club() {
    }

    public Club(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
