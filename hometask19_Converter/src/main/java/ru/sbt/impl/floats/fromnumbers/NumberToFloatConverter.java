package ru.sbt.impl.floats.fromnumbers;

import ru.sbt.ConverterFrom;

public class NumberToFloatConverter implements ConverterFrom<Float> {
    @Override
    public Float convert(Object valueFrom) {
        Number value= (Number) valueFrom;
        return value.floatValue();
    }
}
