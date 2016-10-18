package ru.sbt;

/**
 * Интерфейс, с которым работает пользователь конвертора.
 * У реализации должна быть коллекция конверторов, каждый из которых конвертирует в один определенный тип.
 */
public interface Converter {
    /**
     * @param toClass   Тип, в который нужно сконвертировать.
     * @param valueFrom Значение, которое нужно сконвертировать.
     * @return Сконвертированное значение.
     */
    <T> T convert(Class<T> toClass, Object valueFrom);

    /**
     * Метод позволяет добавлять новые типы, в которые можно конвертировать.
     *
     * @param toClass Класс, в который можно будет конвертировать.
     */
    <T> void addConverterTo(Class<T> toClass, ConverterTo<T> converterTo);

    <T> void removeConverterTo(Class<T> toClass);
}
