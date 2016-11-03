package ru.newlinesoft.entities;


public class Ingredient {
    private String name;
    private String measureUnit;

    public Ingredient(String name, String measureUnit) {
        this.name = name;
        this.measureUnit = measureUnit;
    }

    public String getName() {
        return name;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }
}
