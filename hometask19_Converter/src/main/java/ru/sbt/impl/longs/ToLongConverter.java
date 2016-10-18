package ru.sbt.impl.longs;

import ru.sbt.impl.AbstractConverterToImpl;
import ru.sbt.impl.longs.fromnumbers.NumberToLongConverter;
import ru.sbt.impl.longs.fromstrings.StringToLongConverter;

import java.math.BigDecimal;

public class ToLongConverter extends AbstractConverterToImpl<Long> {
    public ToLongConverter() {
        convertersFrom.put(Long.class, new NumberToLongConverter());
        convertersFrom.put(Float.class, new NumberToLongConverter());
        convertersFrom.put(Double.class, new NumberToLongConverter());
        convertersFrom.put(BigDecimal.class, new NumberToLongConverter());
        convertersFrom.put(String.class, new StringToLongConverter());

    }
}
