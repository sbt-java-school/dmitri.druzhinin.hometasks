package ru.sbt.entity;

import javax.persistence.*;

@Entity
@Table(name = "ingredients_of_recipes")
public class Relation {
    private Long id;
    private Recipe recipe;
    private Ingredient ingredient;
    private Double amount;
    private String unit;

    public Relation() {

    }

    public Relation(Ingredient ingredient) {
        this(null, ingredient, null, null);
    }

    public Relation(Recipe recipe, Ingredient ingredient, Double amount, String unit) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "recipe_name", foreignKey = @ForeignKey(name = "recipe_name_fk"))
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @ManyToOne
    @JoinColumn(name = "ingredient_name", foreignKey = @ForeignKey(name = "ingredient_name_fk"))
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
