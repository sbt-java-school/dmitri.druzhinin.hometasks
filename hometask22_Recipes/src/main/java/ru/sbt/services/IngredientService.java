package ru.sbt.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.sbt.dao.IngredientDao;
import ru.sbt.entities.Ingredient;

import java.util.List;

public class IngredientService {
    private final IngredientDao ingredientDao;

    public IngredientService(IngredientDao ingredientDao) {
        this.ingredientDao=ingredientDao;
    }

    @Transactional(readOnly = true)
    public List<Ingredient> getAll(){
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
        try {
            ingredientDao.create(ingredient);
        }catch (DuplicateKeyException e){
            throw new BusinessException("Don't repeat!");
        }
    }

    private void validate(Ingredient ingredient){
        if(ingredient==null){
            throw new BusinessException("No ingridient");
        }
        validate(ingredient.getName());
    }
    private void validate(String name){
        if(name==null || (!StringUtils.hasText(name))){
            throw new BusinessException("No ingredient name");
        }
    }
}
