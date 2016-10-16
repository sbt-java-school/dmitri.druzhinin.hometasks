package ru.sbt;

import java.util.Map;

/**
 * Нода, которая инкапсулирует в себе логическое And.
 */
public class AndNode extends OperationNode {
    /**
     * @return {@code double} 1.0d лишь в том случае, когда все дочерние ноды возвращают 1.0d, иначе {@code double} 0.0d.
     */
    @Override
    public double getResult(Map<String, Double> values) {
        checkChilds();
        if (childs.size() < 2) {
            throw new IllegalStateException("Requires at least two childs");
        }
        for (Node node : childs) {
            if (node.getResult(values) != 1.0d) {
                return 0.0d;
            }
        }
        return 1.0d;
    }
}
