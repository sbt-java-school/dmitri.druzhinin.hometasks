package ru.sbt;

import java.util.List;
import java.util.Map;

/**
 * Объекты этого класса являются самыми глубокими элементами в дереве.
 */
public class ParameterNode implements Node {
    private String keyName;

    public ParameterNode(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public double getResult(Map<String, Double> values) {
        return values.get(keyName);
    }

    @Override
    public void addNode(Node node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addNodes(Node... nodes) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Node> getChilds() {
        throw new UnsupportedOperationException();
    }
}
