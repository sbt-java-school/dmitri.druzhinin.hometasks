package ru.sbt;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.dto.IngredientDto;
import ru.sbt.dto.RecipeDto;
import ru.sbt.dto.RelationDto;
import ru.sbt.exception.BusinessException;
import ru.sbt.service.IngredientService;
import ru.sbt.service.RecipeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Для выполнения тестов необходимо наличие конфигурационного файла Spring в resources.
 */
@ContextConfiguration(locations = "/app-test-context.xml")
//@Rollback(false)
@Transactional(transactionManager = "transactionManager")
public class RecipeServiceTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void testCreate() {
        RecipeDto recipeDtoBefore = new RecipeDto();
        recipeDtoBefore.setName("Recipe1");
        recipeService.create(recipeDtoBefore);
        RecipeDto recipeDtoAfter = recipeService.get(recipeDtoBefore.getName());
        Assert.assertEquals(recipeDtoBefore.getName(), recipeDtoAfter.getName());
    }

    @Test(expected = BusinessException.class)
    public void testDuplicateKeyCreate() {
        String name = "Recipe1";
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(name);
        recipeService.create(recipeDto);
        recipeDto = new RecipeDto();
        recipeDto.setName(name);
        recipeService.create(recipeDto);
    }

//    @Test(expected = DataIntegrityViolationException.class)
//    public void testDeleteIngredientOfRecipe(){
//        String ingredientName="Ingredient1";
//        String recipeName="Recipe1";
//        prepare(recipeName, ingredientName);
//        ingredientService.delete(ingredientName);
//    }

    @Test(expected = BusinessException.class)
    public void testDelete() {
        String ingredientName = "Ingredient1";
        String recipeName = "Recipe1";
        prepare(recipeName, ingredientName);
        recipeService.delete(recipeName);
        recipeService.get(recipeName);
    }

    private void prepare(String recipeName, String ingredientName) {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName(ingredientName);
        ingredientService.save(ingredientDto);
        RelationDto relationDto = new RelationDto();
        relationDto.setRecipeName(recipeName);
        relationDto.setIngredientName(ingredientName);
        List<RelationDto> recipeIngredients = new ArrayList<>();
        recipeIngredients.add(relationDto);
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(recipeName);
        recipeDto.setRelations(recipeIngredients);
        recipeService.create(recipeDto);
    }
}

