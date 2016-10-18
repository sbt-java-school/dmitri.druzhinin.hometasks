package ru.sbt.impl.bigdecimals.fromstrings;

import ru.sbt.ConverterFrom;

import java.math.BigDecimal;

public class StringToBigDecimalConverter implements ConverterFrom<BigDecimal> {
    @Override
    public BigDecimal convert(Object valueFrom) {
        String value = (String) valueFrom;
        return BigDecimal.valueOf(Double.parseDouble(value));
    }
}
