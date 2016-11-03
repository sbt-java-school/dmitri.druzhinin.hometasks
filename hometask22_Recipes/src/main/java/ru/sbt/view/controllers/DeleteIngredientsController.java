package ru.sbt.view.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import ru.sbt.dao.IngredientDao;
import ru.sbt.entities.Ingredient;
import ru.sbt.view.ViewUtils;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Контроллер, координирующий работу со списком всех ингредиентов. Отслеживает item списка ингредиентов,
 * который нужно удалить.
 */
public class DeleteIngredientsController {
    private static final String ERROR_SHOW = "Denied. This ingregient serves.";
    private Logger logger = getLogger(DeleteIngredientsController.class);
    private IngredientDao ingredientDao;
    private List<Ingredient> ingredientList;
    @FXML
    private TableView<Ingredient> ingredientsTableView;
    @FXML
    private TableColumn<Ingredient, String> ingredientColumn;
    @FXML
    private TableColumn<Ingredient, String> unitColumn;

    public DeleteIngredientsController(IngredientDao ingredientDao) {
        this.ingredientDao = ingredientDao;
    }

    @FXML
    private void initialize() {
        ingredientList = ingredientDao.findAll();
        updateIngredientsTableView();
        logger.info("DeleteIngredientsControlles::initialize()");
    }

    private void updateIngredientsTableView() {
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        System.out.println(unitColumn == null);
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("measureUnit"));
        ingredientsTableView.setItems(FXCollections.observableList(ingredientList));
    }

    @FXML
    private void deleteIngredient(ActionEvent event) {
        try {
            Ingredient focusedIngredient = ingredientsTableView.getFocusModel().getFocusedItem();
            if (focusedIngredient != null) {
                ingredientDao.deleteByName(focusedIngredient.getName());
                ingredientList.remove(focusedIngredient);
                updateIngredientsTableView();
            }
        } catch (DataIntegrityViolationException e) {
            logger.error(ERROR_SHOW);
            ViewUtils.errorShow(ERROR_SHOW);
        }
    }
}
