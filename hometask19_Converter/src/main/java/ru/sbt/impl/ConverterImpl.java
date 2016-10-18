package ru.sbt.impl;

import ru.sbt.Converter;
import ru.sbt.ConverterTo;
import ru.sbt.impl.bigdecimals.ToBigDecimalConverter;
import ru.sbt.impl.doubles.ToDoubleConverter;
import ru.sbt.impl.integers.ToIntegerConverter;
import ru.sbt.impl.strings.ToStringConverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConverterImpl implements Converter {

    private Map<Class, ConverterTo> convertersTo = new HashMap<>();

    public ConverterImpl() {
        convertersTo.put(Integer.class, new ToIntegerConverter());
        convertersTo.put(Double.class, new ToDoubleConverter());
        convertersTo.put(BigDecimal.class, new ToBigDecimalConverter());
        convertersTo.put(String.class, new ToStringConverter());
    }

    /**
     * {@inheritDoc}
     */
    public <T> T convert(Class<T> toClass, Object valueFrom) {
        ConverterTo<T> converterTo = convertersTo.get(toClass);
        if (converterTo == null) {
            throw new IllegalStateException("Отсутствует конвертер в " + toClass);
        }
        return converterTo.convert(valueFrom);
    }

    /**
     * {@inheritDoc}
     */
    public <T> void addConverterTo(Class<T> toClass, ConverterTo<T> converterTo) {
        convertersTo.put(toClass, Objects.requireNonNull(converterTo));
    }

    public <T> void removeConverterTo(Class<T> toClass) {
        convertersTo.remove(toClass);
    }

}
