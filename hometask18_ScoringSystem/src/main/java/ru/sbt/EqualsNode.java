package ru.sbt;

import java.util.Map;

public class EqualsNode extends OperationNode {
    /**
     * @return {@code double} 1.0d лишь в том случае, когда все дочерние ноды возвращают одинаковый результат,
     * иначе {@code double} 0.0d.
     */
    @Override
    public double getResult(Map<String, Double> values) {
        checkChilds();
        double current = childs.remove(0).getResult(values);
        for (Node node : childs) {
            if (current != node.getResult(values)) {
                return 0.0d;
            }
        }
        return 1.0d;
    }
}
