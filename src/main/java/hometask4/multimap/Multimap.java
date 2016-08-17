package hometask4.multimap;

import java.util.*;

public abstract class Multimap<K, V> {
    protected Map<K, Collection<V>> dataMap=new HashMap<>();

    /**
     * Реализации определяют где хранить значения.
     * @return пустой контейнер для хранения значений.
     */
    protected abstract Collection<V> createEmptyCollectionForValues();

    /**
     * @param key ключ, по которому в мультимапу кладется значение.
     * @param value значение, которое требуется положить в мультимапу.
     * @return true, если по key уже есть значения. False, если value - первое значение по данному key.
     */
    public boolean put(K key, V value) {
        return putAll(key, Collections.singleton(value));
    }

    public boolean putAll(K key, Collection<V> values){
        Collection<V> valuesByKey=dataMap.get(key);
        boolean isContains = valuesByKey!=null;
        if(!isContains){
            valuesByKey=createEmptyCollectionForValues();
            dataMap.put(key, valuesByKey);
        }
        valuesByKey.addAll(values);
        return isContains;
    }

    /**
     * @return контейнер значений по key. null если такого ключа нет.
     */
    public Collection<V> get(K key) {
        return dataMap.get(key);
    }

    public Set<K> keySet(){
        return dataMap.keySet();
    }
}
