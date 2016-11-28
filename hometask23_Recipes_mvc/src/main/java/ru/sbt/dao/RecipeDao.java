package ru.sbt.dao;

import org.springframework.stereotype.Repository;
import ru.sbt.entity.Recipe;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * DAO для рецептов. Поддерживает основные CRUD-операции для работы с рецептами.
 */
@Repository
public class RecipeDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param recipe рецепт, который необходимо поместить в базу.
     */
    public void create(Recipe recipe) {
        entityManager.persist(recipe);
    }

    /**
     * @param name название рецепта, который необходимо взять из базы.
     */
    public Recipe get(String name) {
        return entityManager.find(Recipe.class, name);
    }

    /**
     * @return список всех рецептов в базе.
     */
    public List getAll() {
        return entityManager.createQuery("select r from Recipe r").getResultList();
    }

    /**
     * @param recipe рецепт, который необходимо обновить в базе.
     */
    public void update(Recipe recipe) {
        entityManager.merge(recipe);
    }

    /**
     * @param name название рецепта, который необходимо удалить из базы.
     */
    public void delete(String name) {
        Recipe recipe = entityManager.find(Recipe.class, name);
        if (recipe != null) {
            entityManager.remove(recipe);
        }
    }

}
