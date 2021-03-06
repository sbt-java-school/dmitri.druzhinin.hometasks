package ru.sbt.view.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import ru.sbt.entities.Recipe;
import ru.sbt.services.IngredientService;
import ru.sbt.services.RecipeService;

import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Основная функциональность этого контроллера отражена в предке. Специфично здесь отслеживание нажатия кнопки для
 * сохранения измененного объекта класса {@code Recipe} в БД.
 */
public class UpdaterController extends AbstractController {
    private Logger logger = getLogger(UpdaterController.class);
    private Recipe beforeChangeRecipe;

    public UpdaterController(RecipeService recipeService, IngredientService ingredientService, MainController mainController) {
        super(recipeService, ingredientService, mainController);
    }

    @FXML
    protected void initialize() {
        super.initialize();
        beforeChangeRecipe = (recipeService.getByName(mainController.getFocusedItem())).get();
        descriptionTextArea.setText(beforeChangeRecipe.getDescription());
        Iterator<ChoiceBox> choiceBoxIterator = ingredientNameChoiceBoxes.iterator();
        Iterator<Label> labelIterator = measureUnitLabels.iterator();
        Iterator<TextField> textFieldIterator = amountTextFields.iterator();
        beforeChangeRecipe.getIngredients().entrySet().stream().forEach(entry -> {
            choiceBoxIterator.next().getSelectionModel().select(entry.getKey().getName());
            labelIterator.next().setText(entry.getKey().getMeasureUnit());
            textFieldIterator.next().setText(String.valueOf(entry.getValue()));
        });
        logger.info("UpdaterController::initialize()");
    }

    @FXML
    private void saveChanges(ActionEvent event) {
        getRecipeFromView().ifPresent(recipe -> {
            recipeService.save(recipe);
            mainController.notifyUpdate(recipe);
            mainController.closeUpdaterStage();
            logger.info("The changes are saved to recipe " + recipe.getName());
        });
    }
}
