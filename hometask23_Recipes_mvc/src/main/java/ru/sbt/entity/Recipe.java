package ru.sbt.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Recipe")
public class Recipe {
    private String name;
    private String description;
    private List<Relation> relations;

    public Recipe(String name, String description, List<Relation> relations) {
        this.name = name;
        this.description = description;
        this.relations = relations;
    }

    public Recipe() {
        relations = new ArrayList<>();
    }

    public Recipe(String name, String description) {
        this(name, description, new ArrayList<>());
    }

    @Id
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

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "recipe", fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

}
