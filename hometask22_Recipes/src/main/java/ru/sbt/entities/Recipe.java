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
    public Recipe(String name, String description){
        this(name, description, null);
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

    public void setIngredients(Map<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        return name.equals(recipe.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
