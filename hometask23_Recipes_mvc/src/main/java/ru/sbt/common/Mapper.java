package ru.sbt.common;

import java.util.List;

/**
 * Интерфейс, отвечающий за взаимное отображение Entity и DTO.
 */
public interface Mapper {
    /**
     * @param sourceObject     объект, который необходимо отобразить.
     * @param destinationClass тип, в который необходимо отобразить.
     * @return отображенный объект целевого типа.
     */
    <S, D> D map(S sourceObject, Class<D> destinationClass);

    /**
     * @param source           Коллекция объектов, которые необходимо отобразить.
     * @param destinationClass тип, в который необходимо отобразить.
     * @return коллекцию отображенных объектов целевого типа.
     */
    <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass);
}
