package com.score_keeper.models;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Stroke {
    @Id
    private String id;

    @Max(value = 9)
    private int holeNumber;
    @Max(value = 10)
    @Min(value = 1)
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
