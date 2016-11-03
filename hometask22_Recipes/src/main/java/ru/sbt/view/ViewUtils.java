package ru.sbt.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Предоставляет служебные методы контроллерам.
 */
public class ViewUtils {
    private static final String ERROR_PATH = "/error.fxml";
    private static Logger logger = getLogger(ViewUtils.class);

    public static void errorShow(String text) {
        Stage errorStage = new Stage();
        try {
            Parent parent = FXMLLoader.load(ViewUtils.class.getResource(ERROR_PATH));
            ((Label) parent.lookup("#errorLabel")).setText(text);
            errorStage.setScene(new Scene(parent));
            errorStage.setWidth(400);
            errorStage.show();
        } catch (IOException e) {
            logger.error("Can't load resource " + ERROR_PATH);
            throw new RuntimeException(e);
        }
    }
}
