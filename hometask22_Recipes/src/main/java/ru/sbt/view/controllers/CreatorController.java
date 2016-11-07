package ru.sbt.view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import ru.sbt.services.BusinessException;
import ru.sbt.services.IngredientService;
import ru.sbt.services.RecipeService;
import ru.sbt.view.ViewUtils;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Основная функциональность этого контроллера отражена в предке. Специфично здесь отслеживание нажатия кнопки для
 * сохранения нового объекта класса {@code Recipe} в БД.
 */
public class CreatorController extends AbstractController {
    private Logger logger = getLogger(CreatorController.class);

    public CreatorController(RecipeService recipeService, IngredientService ingredientService, MainController mainController) {
        super(recipeService, ingredientService, mainController);
    }

    @FXML
    private void createRecipe(ActionEvent event) {
        try {
            getRecipeFromView().ifPresent(recipe -> {
                recipeService.add(recipe);
                mainController.notifyCreate(recipe);
                mainController.closeCreatorStage();
            });
        } catch (BusinessException e) {
            logger.error("Attempt to create recipe with existing name in DB");
            ViewUtils.errorShow(e.getMessage());
        }
    }
}
