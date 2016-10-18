package ru.sbt.impl.floats.fromstrings;

import ru.sbt.ConverterFrom;

public class StringToFloatConverter implements ConverterFrom<Float> {
    @Override
    public Float convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Float.parseFloat(value);
    }
}
