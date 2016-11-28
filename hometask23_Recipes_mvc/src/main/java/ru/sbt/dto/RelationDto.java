package ru.sbt.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Для работы с ингредиентом рецепта клиенту передается объект данного класса.
 */
public class RelationDto {
    private Long id;
    private String recipeName;
    private String ingredientName;
    private Double amount;
    @NotNull
    @Size(min=1)
    private String unit;

    public RelationDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
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
