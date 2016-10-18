package ru.sbt.impl.bigdecimals;

import ru.sbt.impl.AbstractConverterToImpl;
import ru.sbt.impl.bigdecimals.fromnumbers.NumberToBigDecimalConverter;
import ru.sbt.impl.bigdecimals.fromstrings.StringToBigDecimalConverter;

import java.math.BigDecimal;

public class ToBigDecimalConverter extends AbstractConverterToImpl<BigDecimal> {
    public ToBigDecimalConverter() {
        convertersFrom.put(Integer.class, new NumberToBigDecimalConverter());
        convertersFrom.put(Double.class, new NumberToBigDecimalConverter());
        convertersFrom.put(Float.class, new NumberToBigDecimalConverter());
        convertersFrom.put(Long.class, new NumberToBigDecimalConverter());
        convertersFrom.put(String.class, new StringToBigDecimalConverter());
    }
}
