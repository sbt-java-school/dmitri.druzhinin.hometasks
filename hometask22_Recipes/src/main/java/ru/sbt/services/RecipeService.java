package ru.sbt.services;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.sbt.dao.IngredientDao;
import ru.sbt.dao.RecipeDao;
import ru.sbt.entities.Recipe;

import java.util.List;
import java.util.Optional;

public class RecipeService {
    private IngredientDao ingredientDao;
    private RecipeDao recipeDao;

    public RecipeService(RecipeDao recipeDao, IngredientDao ingredientDao) {
        this.recipeDao = recipeDao;
        this.ingredientDao=ingredientDao;
    }

    /**
     *
     * @return Рецепт по имени с заполненными ингредиентами.
     */
    @Transactional(readOnly = true)
    public Optional<Recipe> getByName(String recipeName){
        validate(recipeName);
        Optional<Recipe> result=recipeDao.findByName(recipeName);
        result.ifPresent(recipe -> recipe.setIngredients(ingredientDao.getByRecipeName(recipeName)));
        return result;
    }

    /**
     *
     * @return Все рецепты с заполненными ингредиентами.
     */
    @Transactional(readOnly = true)
    public List<Recipe> getAll(){
        List<Recipe> recipes=recipeDao.findAll();
        for(Recipe recipe:recipes){
            recipe.setIngredients(ingredientDao.getByRecipeName(recipe.getName()));
        }
        return recipes;
    }

    /**
     * Предоставляет возможность добавить в базу новый рецепт со всеми его ингредиентами и их количествами.
     * @param recipe
     */
    @Transactional
    public void add(Recipe recipe){
        validate(recipe);
        try{
            recipeDao.create(recipe);
            ingredientDao.addByRecipeName(recipe.getName(), recipe.getIngredients());
        }catch (DuplicateKeyException e){
            throw new BusinessException("Don't repeat!");
        }
    }

    /**
     * Предоставляет возможность перезаписать в базу рецепт со всеми его ингредиентами и их количествами.
     */
    @Transactional
    public void save(Recipe recipe){
        deleteByName(recipe.getName());
        add(recipe);
    }

    /**
     * Удаляет из базы рецепт. Для этого сначала удаляет все связи этого рецепта, затем сам рецепт.
     */
    @Transactional
    public void deleteByName(String recipeName){
        validate(recipeName);
        ingredientDao.deleteByRecipeName(recipeName);
        recipeDao.deleteByName(recipeName);
    }
    private void validate(Recipe recipe){
        if(recipe==null || recipe.getIngredients().size()==0){
            throw new BusinessException("No ingridients");
        }
        validate(recipe.getName());
    }
    private void validate(String name){
        if(name==null || (!StringUtils.hasText(name))){
            throw new BusinessException("No recipe name");
        }
    }
}