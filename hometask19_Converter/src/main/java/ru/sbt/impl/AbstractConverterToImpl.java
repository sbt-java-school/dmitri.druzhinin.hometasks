package ru.sbt.impl;

import ru.sbt.ConverterFrom;
import ru.sbt.ConverterTo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Основная реализация интерфейса {@code ConverterTo}. Все конверторы, которые будут делегировать конвертирование, могут
 * наследовать этот класс.
 *
 * @param <T>
 */
public abstract class AbstractConverterToImpl<T> implements ConverterTo<T> {
    protected Map<Class, ConverterFrom> convertersFrom = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public T convert(Object valueFrom) {
        ConverterFrom<T> converter = convertersFrom.get(valueFrom.getClass());
        if (converter == null) {
            throw new IllegalStateException("Отсутствует конвертор из " + valueFrom.getClass());
        }
        return converter.convert(valueFrom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addConverterFrom(Class<?> fromClass, ConverterFrom<T> converter) {
        convertersFrom.put(fromClass, Objects.requireNonNull(converter));
    }

    @Override
    public void removeConverterFrom(Class<?> fromClass) {
        convertersFrom.remove(fromClass);
    }
}
