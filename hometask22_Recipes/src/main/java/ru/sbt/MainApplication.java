package ru.sbt;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import ru.sbt.view.SpringFxmlLoader;

import java.io.*;
import java.util.Properties;

public class MainApplication extends Application {
    private static Logger logger = LoggerFactory.getLogger(MainApplication.class);
    private static final String JDBC_PROPERTIES = "/jdbc.properties";
    private static final String ERROR_MESSAGE = "Incorrect database directory";

    public static void main(String[] args) {
        if (checkPath()) {
            logger.info("Launch");
            DataBaseCreator.createDatabase();
            launch(args);
        } else {
            logger.error(ERROR_MESSAGE);
        }
    }

    private static boolean checkPath() {
        try (InputStream in = new FileInputStream(MainApplication.class.getResource(JDBC_PROPERTIES).getFile());
             InputStreamReader reader = new InputStreamReader(in, "UTF-8")) {
            Properties properties = new Properties();
            properties.load(reader);
            String jdbcUrl = properties.getProperty("jdbc.url");
            String[] pieces = jdbcUrl.split("h2:");//разбить url на 2 части. pieces[1]-путь к директории, который нужно проверить.
            if (pieces.length < 2) {//если директория не указана.
                return false;
            }
            return validate(pieces[1]);
        } catch (IOException e) {
            logger.error("jdbc.properties file missing");
        }
        return false;
    }

    private static boolean validate(String path) {
        if (!StringUtils.hasText(path)) {
            return false;
        }
        boolean result = false;
        File temp = new File(path);
        try {
            result = temp.createNewFile();
            if (result) {
                temp.delete();
            }
        } catch (IOException e) {
            logger.error(ERROR_MESSAGE);
        }
        return result;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = SpringFxmlLoader.load("/main.fxml");
        primaryStage.setTitle("Recipes");
        primaryStage.setWidth(750);
        primaryStage.setHeight(600);
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

}
