package ru.sbt;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.sbt.view.SpringFxmlLoader;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root= SpringFxmlLoader.load("/main.fxml");
        primaryStage.setTitle("Recipes");
        primaryStage.setWidth(750);
        primaryStage.setHeight(600);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
}
