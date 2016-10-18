package ru.sbt;

/**
 * Реализация этого интерфейса хранит в себе коллекцию конвертеров в тип {@code T}.
 *
 * @param <T> Тип, в который данный конвертор преобразует значения путем делегированием этих значений нужным
 *            конверторам {@code ConverterFrom}.
 */
public interface ConverterTo<T> {
    /**
     * Реализации интерфейса в этом методе не должны предоставлять правило конвертации, а должны делегировать
     * это нужной реализации {@code ConverterFrom}.
     *
     * @param valueFrom Значение, которое нужно сконвертировать.
     * @return Сконвертированное значение.
     */
    T convert(Object valueFrom);

    /**
     * Реализация интерфейса в этом методе должна добавлять параметр {@code converter} в коллекцию конверторов.
     */
    void addConverterFrom(Class<?> fromClass, ConverterFrom<T> converter);

    void removeConverterFrom(Class<?> fromClass);
}
