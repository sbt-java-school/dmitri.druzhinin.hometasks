package ru.sbt;

/**
 * Интерфейс, реализации которого должны в методе {@code convert(Object valueFrom)} предоставлять правило конвертации
 * аргумента метода в параметр-тип.
 *
 * @param <T> параметр-тип, в который производится конвертация.
 */
public interface ConverterFrom<T> {
    /**
     * @param valueFrom Значение, которое нужно сконвертировать.
     * @return Сконвертированное значение.
     */
    T convert(Object valueFrom);
}
