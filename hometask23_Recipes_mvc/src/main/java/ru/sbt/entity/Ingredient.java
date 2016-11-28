package ru.sbt.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Ingredient")
public class Ingredient {
    private String name;
    private List<Relation> relations;

    public Ingredient() {
    }

    public Ingredient(String name, List<Relation> relations) {
        this.name = name;
        this.relations = relations;
    }

    public Ingredient(String name) {
        this(name, null);
    }

    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
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
