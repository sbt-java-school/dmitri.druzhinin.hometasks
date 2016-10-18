package ru.sbt.impl.strings.fromnumbers;

import ru.sbt.ConverterFrom;

public class NumberToStringConverter implements ConverterFrom<String> {
    @Override
    public String convert(Object valueFrom) {
        Number value= (Number) valueFrom;
        return String.valueOf(value);
    }
}
