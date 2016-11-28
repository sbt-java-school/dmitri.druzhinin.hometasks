package ru.sbt.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Для работы с ингредиентом клиенту передается объект данного класса.
 */
public class IngredientDto {
    @NotNull(message = "Ingredient name must not be null")
    @Size(min = 2, max = 20, message = "Ingredient name must be between 2 and 20 characters")
    private String name;

    public IngredientDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
