package com.score_keeper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "tournament")
public class Tournament {
    @Id
    private String id;

    private String name;
    private String season;
    private int stages;
    private int holes;
    private int max_strokes;
    private boolean blocked;

    public Tournament() {
    }

    public Tournament(String name, String season, int stages, int holes, int max_strokes, boolean blocked) {
        this.name = name;
        this.season = season;
        this.stages = stages;
        this.holes = holes;
        this.max_strokes = max_strokes;
        this.blocked = blocked;
    }

    public String getId() {
        return id;
    }

//    public void setId(String id) {
//        this.id = id;
//    }

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
        return max_strokes;
    }

    public void setMax_strokes(int max_strokes) {
        this.max_strokes = max_strokes;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
