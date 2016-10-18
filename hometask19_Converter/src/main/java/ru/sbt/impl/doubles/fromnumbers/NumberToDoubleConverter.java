package ru.sbt.impl.doubles.fromnumbers;

import ru.sbt.ConverterFrom;

public class NumberToDoubleConverter implements ConverterFrom<Double> {
    @Override
    public Double convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return value.doubleValue();
    }
}
