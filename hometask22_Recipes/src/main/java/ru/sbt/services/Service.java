package ru.sbt.services;


import org.springframework.util.StringUtils;
import ru.sbt.dao.IngredientDao;

public abstract class Service {
    protected IngredientDao ingredientDao;
    protected Service(IngredientDao ingredientDao){
        this.ingredientDao=ingredientDao;
    }

    protected void validate(String name){
        if(name==null || (!StringUtils.hasText(name))){
            throw new BusinessException("No name");
        }
    }
}
