package ru.sbt.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.sbt.dao.IngredientDao;
import ru.sbt.dao.RecipeDao;
import ru.sbt.entities.Recipe;

import java.util.List;

public class RecipeService extends Service {
    private RecipeDao recipeDao;

    public RecipeService(RecipeDao recipeDao, IngredientDao ingredientDao) {
        super(ingredientDao);
        this.recipeDao = recipeDao;
    }

    @Transactional(readOnly = true)
    public Recipe getByName(String recipeName){
        validate(recipeName);
        return recipeDao.findByName(recipeName);
    }

    @Transactional(readOnly = true)
    public List<Recipe> getAll(){
        return recipeDao.findAll();
    }

    /**
     * Предоставляет возможность сохранить в базу рецепт со всеми его ингредиентами и их количествами.
     */
    @Transactional
    public void put(Recipe recipe){
        validate(recipe);
        deleteByName(recipe.getName());
        recipeDao.create(recipe);
        ingredientDao.addByRecipeName(recipe.getName(), recipe.getIngredients());
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
}
