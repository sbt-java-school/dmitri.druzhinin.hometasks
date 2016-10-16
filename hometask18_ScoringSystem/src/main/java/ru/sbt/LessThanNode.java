package ru.sbt;

import java.util.Map;

public class LessThanNode extends OperationNode {
    /**
     * @return {@code double} 1.0d если нулевая дочерняя нода возвращает результат меньший, чем первая дочерняя нода,
     * иначе {@code double} 0.0d.
     */
    @Override
    public double getResult(Map<String, Double> values) {
        checkChilds();
        if (childs.size() < 2) {
            throw new IllegalStateException("Requires at least two childs");
        }
        if (childs.get(0).getResult(values) < childs.get(1).getResult(values)) {
            return 1.0d;
        } else {
            return 0.0d;
        }
    }
}
