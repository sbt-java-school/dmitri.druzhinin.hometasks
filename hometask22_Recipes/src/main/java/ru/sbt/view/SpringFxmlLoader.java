package ru.sbt.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

public class SpringFxmlLoader {
    private static ApplicationContext context = new ClassPathXmlApplicationContext("app-context-xml.xml");
    private static Logger logger = getLogger(SpringFxmlLoader.class);

    /**
     * Метод нужен чтобы контроллеры могли быть Spring beans.
     *
     * @param url файла .fxml, из которого будет создан возвращаемый объект.
     */
    public static Parent load(String url) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(param -> context.getBean(param));
        try (InputStream in = SpringFxmlLoader.class.getResourceAsStream(url)) {
            return fxmlLoader.load(in);
        } catch (IOException e) {
            logger.error("Can't load resource " + url);
            throw new RuntimeException(e);
        }
    }
}
