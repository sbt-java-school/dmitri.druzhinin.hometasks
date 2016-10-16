package ru.sbt;

import java.util.Map;

/**
 * Нода, которая инкапсулирует в себе операцию деления.
 */
public class DivisionNode extends OperationNode {
    @Override
    public double getResult(Map<String, Double> values) {
        checkChilds();
        double result = childs.remove(0).getResult(values);
        for (Node node : childs) {
            result /= node.getResult(values);
        }
        return result;
    }
}
