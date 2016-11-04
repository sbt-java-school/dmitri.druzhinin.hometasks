package ru.sbt.entities;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
