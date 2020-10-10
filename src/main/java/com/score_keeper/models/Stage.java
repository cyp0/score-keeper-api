package com.score_keeper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Document(collection = "stage")
public class Stage {
    @Id
    private String id;

    @DBRef
    @NotNull(message = "Tournament id must no be empty")
    private Tournament tournament;


    @NotNull(message = "Stage number must not be empty")
    private int stageNumber;

    private boolean blocked;

    public Stage() {
    }

    public Stage(Tournament tournament, int stageNumber) {
        this.tournament = tournament;
        this.stageNumber = stageNumber;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }


    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
