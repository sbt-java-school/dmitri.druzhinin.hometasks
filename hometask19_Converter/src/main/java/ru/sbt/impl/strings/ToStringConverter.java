package ru.sbt.impl.strings;

import ru.sbt.impl.AbstractConverterToImpl;
import ru.sbt.impl.strings.fromnumbers.NumberToStringConverter;

import java.math.BigDecimal;

public class ToStringConverter extends AbstractConverterToImpl<String> {
    public ToStringConverter() {
        convertersFrom.put(Integer.class, new NumberToStringConverter());
        convertersFrom.put(Double.class, new NumberToStringConverter());
        convertersFrom.put(Float.class, new NumberToStringConverter());
        convertersFrom.put(Long.class, new NumberToStringConverter());
        convertersFrom.put(BigDecimal.class, new NumberToStringConverter());
    }
}
