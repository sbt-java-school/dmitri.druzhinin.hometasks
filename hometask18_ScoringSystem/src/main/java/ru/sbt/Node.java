package ru.sbt;

import java.util.List;
import java.util.Map;

/**
 * Общий интерфейс для всех нод.
 */
public interface Node {
    /**
     * @return результат, смысл которого зависит от конкретной реализации  Node.
     */
    double getResult(Map<String, Double> values);

    /**
     * Добавляет {@code node} в коллекцию дочерних нод.
     */
    void addNode(Node node);

    /**
     * Добавляет {@code nodes} в коллекцию дочерних нод.
     */
    void addNodes(Node... nodes);

    List<Node> getChilds();
}
