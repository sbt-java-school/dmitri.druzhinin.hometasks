package ru.newlinesoft.view.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import ru.newlinesoft.dao.RecipeDao;
import ru.newlinesoft.entities.Ingredient;
import ru.newlinesoft.entities.Recipe;
import ru.newlinesoft.view.SpringFxmlLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Контроллер, работающий с основной формой представления.
 */
public class MainController {
    private Logger logger = getLogger(MainController.class);

    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<String> recipesListView;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TableView<Ingredient> ingredientsTableView;
    @FXML
    private TableColumn<Ingredient, String> ingredientColumn;
    @FXML
    private TableColumn<Ingredient, String> measureUnitColumn;
    @FXML
    private ListView<Integer> amountListView;

    private RecipeDao recipeDao;
    private List<Recipe> recipeList;
    private Stage creatorStage;
    private Stage updaterStage;

    public MainController(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    public void closeCreatorStage() {
        creatorStage.close();
    }

    public void closeUpdaterStage() {
        updaterStage.close();
    }

    /**
     * @return Возвращает другому контроллеру имя рецепта, который сейчас выбран.
     */
    public String getFocusedItem() {
        return recipesListView.getFocusModel().getFocusedItem();
    }

    /**
     * Этот метод вызывается другим контроллером, который хочет сообщить данному контроллеру о помещении в базу
     * нового объекта класса {@code Recipe}. В основном нужен чтобы не ходить за этим объектом снова в базу.
     *
     * @param recipe
     */
    public void notifyCreate(Recipe recipe) {
        if (recipeList == null) {
            recipeList = new ArrayList<>();
        }
        recipeList.add(recipe);
        updateRecipesListView();
    }

    /**
     * Этот метод вызывается другим контроллером, который хочет сообщить данному контроллеру о помещении в базу
     * измененного объекта класса {@code Recipe}. В основном нужен чтобы не ходить за этим объектом снова в базу.
     *
     * @param recipe
     */
    public void notifyUpdate(Recipe recipe) {
        recipeList.replaceAll(recipeOld -> {
            if (recipe.getName().equals(recipeOld.getName())) {
                return recipe;
            }
            return recipeOld;
        });
        updateRecipesListView();
    }

    @FXML
    private void deleteRecipe(ActionEvent event) {
        String recipeName = recipesListView.getFocusModel().getFocusedItem();
        if (recipeName != null) {
            recipeDao.deleteByName(recipeName);
            recipeList.removeIf(recipe -> recipe.getName().equals(recipeName));
            updateRecipesListView();
            logger.info(recipeName + " deleted");
        }

    }

    @FXML
    private void toCreateRecipe(ActionEvent event) throws IOException {
        creatorStage = new Stage();
        Parent parent = SpringFxmlLoader.load("/creator.fxml");
        creatorStage.setScene(new Scene(parent));
        creatorStage.show();
    }

    @FXML
    private void toUpdateRecipe(ActionEvent event) {
        if (recipesListView.getFocusModel().getFocusedIndex() != -1) {
            updaterStage = new Stage();
            Parent parent = SpringFxmlLoader.load("/updater.fxml");
            ;
            ((TextField) parent.lookup("#nameTextField")).setText(recipesListView.getFocusModel().getFocusedItem());
            updaterStage.setScene(new Scene(parent));
            updaterStage.show();
        }
    }

    @FXML
    private void getRecipeByName(ActionEvent event) {
        recipeList = new ArrayList<>();
        if (!searchTextField.getText().equals("")) {
            recipeDao.findByName(searchTextField.getText()).ifPresent(recipe -> recipeList.add(recipe));
            updateRecipesListView();
        }
    }

    private void updateRecipesListView() {
        recipesListView.setItems(FXCollections.observableList(recipeList.stream().map(Recipe::getName).collect(Collectors.toList())));
    }

    @FXML
    private void getAllRecipes(ActionEvent event) {
        recipeList = recipeDao.findAll();
        updateRecipesListView();
    }

    @FXML
    private void chooseRecipe(MouseEvent event) {
        Recipe recipe = recipeList.get(recipesListView.getFocusModel().getFocusedIndex());
        descriptionTextArea.setText(recipe.getDescription());
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        measureUnitColumn.setCellValueFactory(new PropertyValueFactory<>("measureUnit"));
        ingredientsTableView.setItems(FXCollections.observableArrayList(recipe.getIngredients().keySet()));
        amountListView.setItems(FXCollections.observableArrayList(recipe.getIngredients().values()));
    }
}
