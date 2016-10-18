package ru.sbt.impl.integers;

import ru.sbt.impl.AbstractConverterToImpl;;
import ru.sbt.impl.integers.fromnumbers.NumberToIntegerConverter;
import ru.sbt.impl.integers.fromstrings.StringToIntegerConverter;

import java.math.BigDecimal;

public class ToIntegerConverter extends AbstractConverterToImpl<Integer> {
    public ToIntegerConverter() {
        convertersFrom.put(Long.class, new NumberToIntegerConverter());
        convertersFrom.put(Float.class, new NumberToIntegerConverter());
        convertersFrom.put(Double.class, new NumberToIntegerConverter());
        convertersFrom.put(BigDecimal.class, new NumberToIntegerConverter());
        convertersFrom.put(String.class, new StringToIntegerConverter());
    }

}
