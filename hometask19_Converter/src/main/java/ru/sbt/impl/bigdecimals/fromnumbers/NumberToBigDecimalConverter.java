package ru.sbt.impl.bigdecimals.fromnumbers;

import ru.sbt.ConverterFrom;

import java.math.BigDecimal;

public class NumberToBigDecimalConverter implements ConverterFrom<BigDecimal> {
    @Override
    public BigDecimal convert(Object valueFrom) {
        Number value = (Number) valueFrom;
        return BigDecimal.valueOf(value.doubleValue());
    }
}
