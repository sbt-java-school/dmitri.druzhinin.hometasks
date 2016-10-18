package ru.sbt.impl.doubles;

import ru.sbt.impl.AbstractConverterToImpl;
import ru.sbt.impl.doubles.fromnumbers.NumberToDoubleConverter;
import ru.sbt.impl.doubles.fromstrings.StringToDoubleConverter;

import java.math.BigDecimal;

public class ToDoubleConverter extends AbstractConverterToImpl<Double> {
    public ToDoubleConverter() {
        convertersFrom.put(Integer.class, new NumberToDoubleConverter());
        convertersFrom.put(Long.class, new NumberToDoubleConverter());
        convertersFrom.put(Float.class, new NumberToDoubleConverter());
        convertersFrom.put(BigDecimal.class, new NumberToDoubleConverter());
        convertersFrom.put(String.class, new StringToDoubleConverter());
    }
}
