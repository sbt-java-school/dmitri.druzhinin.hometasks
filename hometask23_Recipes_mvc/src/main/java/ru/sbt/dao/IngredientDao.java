package ru.sbt.dao;

import org.springframework.stereotype.Repository;
import ru.sbt.entity.Ingredient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * DAO для ингредиентов. Поддерживает основные CRUD-операции для работы с ингредиентами.
 */
@Repository
public class IngredientDao {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * @param ingredient ингредиент, который необходимо поместить в базу.
     */
    public void create(Ingredient ingredient) {
        entityManager.persist(ingredient);
    }

    /**
     * @param name название ингредиента, который необходимо получить из базы.
     */
    public Ingredient get(String name) {
        return entityManager.find(Ingredient.class, name);
    }

    /**
     * @param ingredient который необходимо обновить в базе.
     */
    public void update(Ingredient ingredient) {
        entityManager.merge(ingredient);
    }

    /**
     * @param name название ингредиента, который необходимо удалить из базы.
     */
    public void delete(String name) {
        Ingredient ingredient = entityManager.find(Ingredient.class, name);
        if (ingredient != null) {
            entityManager.remove(ingredient);
        }
    }

    /**
     * @return список всех ингредиентов в базе.
     */
    public List<Ingredient> getAll() {
        return entityManager.createQuery("select i from Ingredient i").getResultList();
    }
}
