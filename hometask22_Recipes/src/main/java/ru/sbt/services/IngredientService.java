package ru.sbt.services;

import org.springframework.transaction.annotation.Transactional;
import ru.sbt.dao.IngredientDao;
import ru.sbt.entities.Ingredient;

import java.util.List;

public class IngredientService extends Service {
    public IngredientService(IngredientDao ingredientDao) {
        super(ingredientDao);
    }

    @Transactional(readOnly = true)
    public List<Ingredient> getAll(String name){
        return ingredientDao.findAll();
    }

    @Transactional
    public void deleteByName(String name){
        validate(name);
        ingredientDao.deleteByName(name);
    }
    @Transactional
    public void put(Ingredient ingredient){
        validate(ingredient);
        ingredientDao.create(ingredient);
    }

    private void validate(Ingredient ingredient){
        if(ingredient==null){
            throw new BusinessException("No ingridient");
        }
        validate(ingredient.getName());
    }

}
