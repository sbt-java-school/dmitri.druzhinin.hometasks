package ru.sbt.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Для работы с рецептом клиенту передается объект данного класса.
 */
public class RecipeDto {
    @NotNull(message = "Recipe name must not be null")
    @Size(min = 2, max = 10, message = "Recipe name must be between 2 and 20 characters")
    private String name;
    private String description;
    private List<RelationDto> relations;

    public RecipeDto() {
        relations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RelationDto> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationDto> relations) {
        this.relations = relations;
    }
}
