package ru.sbt.impl.longs.fromstrings;

import ru.sbt.ConverterFrom;

public class StringToLongConverter implements ConverterFrom<Long> {
    @Override
    public Long convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Long.parseLong(value);
    }
}
