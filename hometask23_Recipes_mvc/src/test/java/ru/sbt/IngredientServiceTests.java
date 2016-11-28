package ru.sbt;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.dto.IngredientDto;
import ru.sbt.exception.BusinessException;
import ru.sbt.service.IngredientService;

import java.util.List;

/**
 * Для выполнения тестов необходимо наличие конфигурационного файла Spring в resources.
 */
@ContextConfiguration(locations = "/app-test-context.xml")
//@Rollback(false)
@Transactional(transactionManager = "transactionManager")
public class IngredientServiceTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void testSave() {
        IngredientDto ingredientDto1 = new IngredientDto();
        ingredientDto1.setName("Ingredient1");
        ingredientService.save(ingredientDto1);
        IngredientDto ingredientDto2 = ingredientService.get(ingredientDto1.getName());
        Assert.assertEquals(ingredientDto1.getName(), ingredientDto2.getName());
    }

    @Test(expected = BusinessException.class)
    public void testDelete() {
        IngredientDto ingredientDto = new IngredientDto();
        ingredientDto.setName("Ingredient1");
        ingredientService.save(ingredientDto);
        ingredientService.delete(ingredientDto.getName());
        ingredientService.get(ingredientDto.getName());
    }

    @Test
    public void testGetAll() {
        int size = 10;
        for (int i = 0; i < size; i++) {
            IngredientDto ingredientDto = new IngredientDto();
            ingredientDto.setName("Ingredient" + i);
            ingredientService.save(ingredientDto);
        }
        List<IngredientDto> ingredients = ingredientService.getAll();
        Assert.assertTrue(ingredients.size() == size);
    }
}
