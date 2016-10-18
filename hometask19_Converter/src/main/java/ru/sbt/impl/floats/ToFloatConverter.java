package ru.sbt.impl.floats;

import ru.sbt.impl.AbstractConverterToImpl;
import ru.sbt.impl.floats.fromnumbers.NumberToFloatConverter;
import ru.sbt.impl.floats.fromstrings.StringToFloatConverter;

import java.math.BigDecimal;

public class ToFloatConverter extends AbstractConverterToImpl<Float> {
    public ToFloatConverter() {
        convertersFrom.put(Integer.class, new NumberToFloatConverter());
        convertersFrom.put(Long.class, new NumberToFloatConverter());
        convertersFrom.put(Double.class, new NumberToFloatConverter());
        convertersFrom.put(BigDecimal.class, new NumberToFloatConverter());
        convertersFrom.put(String.class, new StringToFloatConverter());

    }
}
