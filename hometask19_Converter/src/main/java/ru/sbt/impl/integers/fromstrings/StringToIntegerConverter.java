package ru.sbt.impl.integers.fromstrings;

import ru.sbt.ConverterFrom;

public class StringToIntegerConverter implements ConverterFrom<Integer> {
    @Override
    public Integer convert(Object valueFrom) {
        String value = (String) valueFrom;
        return Integer.parseInt(value);
    }
}
