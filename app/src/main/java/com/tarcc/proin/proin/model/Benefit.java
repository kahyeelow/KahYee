package com.tarcc.proin.proin.model;

public class Benefit {

    private int drawableId;
    private String name;
    private String description;

    public int getDrawableId() {
        return drawableId;
    }

    public Benefit setDrawableId(int drawableId) {
        this.drawableId = drawableId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Benefit setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Benefit setDescription(String description) {
        this.description = description;
        return this;
    }
}
