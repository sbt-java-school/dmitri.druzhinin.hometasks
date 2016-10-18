package ru.sbt.impl.doubles.fromstrings;

import ru.sbt.ConverterFrom;

public class StringToDoubleConverter implements ConverterFrom<Double> {
    @Override
    public Double convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Double.parseDouble(value);
    }
}
