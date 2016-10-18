package ru.sbt.impl.integers.fromnumbers;

import ru.sbt.ConverterFrom;

public class NumberToIntegerConverter implements ConverterFrom<Integer> {
    @Override
    public Integer convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return value.intValue();
    }
}
