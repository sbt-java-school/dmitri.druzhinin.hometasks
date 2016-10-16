package ru.sbt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Класс, который инкапсулирует в себе логику, общую для всех Нод, которые являются операциями.
 */
public abstract class OperationNode implements Node {
    protected List<Node> childs = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNode(Node node) {
        childs.add(Objects.requireNonNull(node));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNodes(Node... nodes) {
        childs.addAll(Arrays.asList(nodes));
    }

    @Override
    public List<Node> getChilds() {
        return new ArrayList<>(childs);
    }

    /**
     * Этот метод рекомендуется вызывать первой строчкой в методе наследника getResult(Map value).
     */
    protected void checkChilds() {
        if (childs.isEmpty()) {
            throw new IllegalStateException("Childs must not be empty. Use addNode()");
        }
    }
}
