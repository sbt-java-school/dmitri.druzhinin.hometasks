package ru.newlinesoft.view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import ru.newlinesoft.dao.IngredientDao;
import ru.newlinesoft.dao.RecipeDao;
import ru.newlinesoft.view.ViewUtils;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Основная функциональность этого контроллера отражена в предке. Специфично здесь отслеживание нажатия кнопки для
 * сохранения нового объекта класса {@code Recipe} в БД.
 */
public class CreatorController extends AbstractController {
    private Logger logger = getLogger(CreatorController.class);

    public CreatorController(RecipeDao recipeDao, IngredientDao ingredientDao, MainController mainController) {
        super(recipeDao, ingredientDao, mainController);
    }

    @FXML
    private void createRecipe(ActionEvent event) {
        try {
            getRecipeFromView().ifPresent(recipe -> {
                recipeDao.create(recipe);
                mainController.notifyCreate(recipe);
                mainController.closeCreatorStage();
            });
        } catch (DuplicateKeyException e) {
            logger.error("Attempt to create recipe with existing name in DB");
            ViewUtils.errorShow("Don't repeat!");
        }
    }
}
