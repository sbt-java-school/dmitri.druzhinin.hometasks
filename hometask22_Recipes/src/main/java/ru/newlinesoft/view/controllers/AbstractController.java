package ru.newlinesoft.view.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import ru.newlinesoft.dao.IngredientDao;
import ru.newlinesoft.dao.RecipeDao;
import ru.newlinesoft.entities.Ingredient;
import ru.newlinesoft.entities.Recipe;
import ru.newlinesoft.view.SpringFxmlLoader;
import ru.newlinesoft.view.ViewUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Абстрактный контроллер, инкапсулирующий в себе общую логику своих наследников-{@code CreatorController} и
 * {@code UpdaterController}
 */
public abstract class AbstractController {
    private static Logger logger = getLogger(AbstractController.class);

    protected RecipeDao recipeDao;
    protected IngredientDao ingredientDao;
    protected MainController mainController;
    protected Map<String, String> measureUnitByIngredientName = new HashMap<>();
    protected static final int choiceBoxNumber = 4;
    protected List<ChoiceBox> ingredientNameChoiceBoxes = new ArrayList<>();
    protected List<TextField> amountTextFields = new ArrayList<>();
    protected List<Label> measureUnitLabels = new ArrayList<>();

    protected TextField nameTextField;
    @FXML
    protected TextArea descriptionTextArea;

    public AbstractController(RecipeDao recipeDao, IngredientDao ingredientDao, MainController mainController) {
        this.recipeDao = recipeDao;
        this.ingredientDao = ingredientDao;
        this.mainController = mainController;
    }

    /**
     * Метод, собирающий содержимое и отображающий его на форму при создании.
     */
    @FXML
    protected void initialize() {
        nameTextField = (TextField) descriptionTextArea.getParent().lookup("#nameTextField");
        Parent parent = descriptionTextArea.getParent();
        for (int i = 0; i < choiceBoxNumber; i++) {
            ingredientNameChoiceBoxes.add((ChoiceBox) parent.lookup("#choiceBox" + i));
            TextField textField = (TextField) parent.lookup("#textField" + i);
            textField.setTextFormatter(new TextFormatter<>(text -> {
                if (text.getText().matches("[^0-9]")) {
                    text.setText("");
                }
                return text;
            }));
            amountTextFields.add(textField);
            measureUnitLabels.add((Label) parent.lookup("#label" + i));
        }
        measureUnitByIngredientName = ingredientDao.findAll().stream().collect(Collectors.toMap(k -> k.getName(), v -> v.getMeasureUnit()));
        updateChoiceBoxes();
        logger.info("AbstractController::initialize()");
    }

    @FXML
    protected void choiceBoxSelected(ActionEvent event) {
        ChoiceBox<String> choiceBox = (ChoiceBox) event.getSource();
        int index = ingredientNameChoiceBoxes.indexOf(choiceBox);
        Label measureUnitLabel = measureUnitLabels.get(index);
        measureUnitLabel.setText(measureUnitByIngredientName.get(choiceBox.getValue()));
    }

    @FXML
    protected void createIngredient(ActionEvent event) {
        TextField ingredientNameTextField = (TextField) descriptionTextArea.getParent().lookup("#newIngredientNameTextField");
        TextField measureUnitTextField = (TextField) descriptionTextArea.getParent().lookup("#newIngredientMeasureUnitTextField");
        if ((!ingredientNameTextField.getText().equals("")) && (!measureUnitTextField.getText().equals(""))) {
            try {
                ingredientDao.create(new Ingredient(ingredientNameTextField.getText(), measureUnitTextField.getText()));
            } catch (DuplicateKeyException e) {
                logger.error("Attempt to create ingredient with existing name in DB");
                ViewUtils.errorShow("Don't repeat!");
                return;
            }
            measureUnitByIngredientName.put(ingredientNameTextField.getText(), measureUnitTextField.getText());
            updateChoiceBoxes();
            ingredientNameTextField.clear();
            measureUnitTextField.clear();
        }
        logger.info("Create new ingredient");
    }

    @FXML
    protected void toDeleteIngredient(ActionEvent event) {
        Stage stage = new Stage();
        Parent parent = SpringFxmlLoader.load("/deleteIngredients.fxml");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    protected void updateChoiceBoxes() {
        for (ChoiceBox<String> choiceBox : ingredientNameChoiceBoxes) {
            String value = choiceBox.getValue();
            choiceBox.setItems(FXCollections.observableArrayList(measureUnitByIngredientName.keySet()));
            choiceBox.getSelectionModel().select(value);
        }
    }

    protected Optional<Recipe> getRecipeFromView() {
        Map<Ingredient, Integer> ingredients = new HashMap<>();
        Set<String> checkSet = new HashSet<>();
        for (int i = 0; i < choiceBoxNumber; i++) {
            String ingredientName = (String) ingredientNameChoiceBoxes.get(i).getValue();
            if (ingredientName == null || ingredientName.equals("")) {
                continue;
            }
            checkSet.add(ingredientName);
            ingredients.put(new Ingredient(ingredientName, measureUnitLabels.get(i).getText()), Integer.valueOf(amountTextFields.get(i).getText()));
        }
        if (checkSet.size() == ingredients.size() && (!nameTextField.getText().equals("")) && ingredients.size() != 0) {
            return Optional.of(new Recipe(nameTextField.getText(), descriptionTextArea.getText(), ingredients));
        }
        return Optional.empty();
    }
}