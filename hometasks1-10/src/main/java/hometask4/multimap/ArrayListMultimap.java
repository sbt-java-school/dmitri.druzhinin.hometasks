package hometask4.multimap;

import hometask4.multimap.Multimap;

import java.util.*;

/**
 * По ключу хранит список значений.
 */
public class ArrayListMultimap<K, V> extends Multimap<K, V> {

    @Override
    protected Collection<V> createEmptyCollectionForValues() {
        return new ArrayList<V>();
    }
}
