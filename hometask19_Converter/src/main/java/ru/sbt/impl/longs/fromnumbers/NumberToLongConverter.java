package ru.sbt.impl.longs.fromnumbers;

import ru.sbt.ConverterFrom;

public class NumberToLongConverter implements ConverterFrom<Long> {
    @Override
    public Long convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return value.longValue();
    }
}
