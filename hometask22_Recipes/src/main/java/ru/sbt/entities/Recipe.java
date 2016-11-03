package ru.sbt.entities;

import java.util.Map;

public class Recipe {
    private String name;
    private String description;
    private Map<Ingredient, Integer> ingredients;

    public Recipe(String name, String description, Map<Ingredient, Integer> ingredients) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<Ingredient, Integer> getIngredients() {
        return ingredients;
    }
}
