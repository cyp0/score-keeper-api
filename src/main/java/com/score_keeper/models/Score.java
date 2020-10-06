package com.score_keeper.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

@Document(collection = "score")
public class Score {
    @Id
    private String id;

    @DBRef
    private Player player;

    @DBRef
    private Stage stage;

    @NotEmpty
    @Size(max = 18)
    private List<Stroke> strokes;

    private int score;

    public Score() {
    }

    public Score(List<Stroke> strokes) {
        this.strokes = strokes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<Stroke> getStrokes() {
        setScore();
        return strokes;
    }

    public void setStrokes(List<Stroke> strokes) {
        this.strokes = strokes;
    }

    public int getScore() {
        return score;
    }

    public void setScore() {
        score = 0;
        Iterator<Stroke> iterator = strokes.iterator();
        while (iterator.hasNext())
            score += iterator.next().getStroke();

    }
}
