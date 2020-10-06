package com.score_keeper.models;

import org.springframework.data.annotation.Id;

public class Stroke {
    @Id
    private String id;


    private int holeNumber;
    private int stroke;

    public Stroke() {
    }

    public Stroke(int holeNumber, int stroke) {
        this.holeNumber = holeNumber;
        this.stroke = stroke;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

}
