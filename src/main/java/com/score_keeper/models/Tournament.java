package com.score_keeper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Document(collection = "tournament")
public class Tournament {
    @Id
    private String id;
    @NotEmpty(message = "Name must not be empty")
    private String name;
    @NotEmpty(message = "Season must not be empty")
    private String season;
    @NotNull(message = "Stage must not be empty")
    private int stages;
    @Max(value = 9, message = "Maximum number of holes is 9")
    @NotNull(message = "Holes must not be empty")
    private int holes;
    private boolean blocked;

    public Tournament() {
    }

    public Tournament(String name, String season, int stages, int holes) {
        this.name = name;
        this.season = season;
        this.stages = stages;
        this.holes = holes;
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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public int getHoles() {
        return holes;
    }

    public void setHoles(int holes) {
        this.holes = holes;
    }

    public int getMax_strokes() {
        return 10;
    }

//    public void setMax_strokes(int max_strokes) {
//        this.max_strokes = max_strokes;
//    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
