package com.score_keeper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stage")
public class Stage {
    @Id
    private String id;

    @DBRef
    private Tournament tournament;

    @DBRef
    private Club club;

    private int stageNumber;
    private boolean blocked;

    public Stage() {
    }

    public Stage(Tournament tournament, Club club, int stageNumber, boolean blocked) {
        this.tournament = tournament;
        this.club = club;
        this.stageNumber = stageNumber;
        this.blocked = blocked;
    }

    public String getId() {
        return id;
    }


    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
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
