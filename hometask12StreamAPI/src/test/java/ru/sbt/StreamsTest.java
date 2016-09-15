package ru.sbt;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StreamsTest {
    /**
     * Исходная коллекция остается неизменной.
     */
    @Test
    public void unmodifiedOriginalCollectionTest() {
        List<String> originalList = new ArrayList<>(Arrays.asList("Mike", "Anna", "Bob", "Camila"));
        Streams.of(originalList).filter(s -> s.length() >= 4).toList();

        Assert.assertTrue(originalList.size() == 4);
        Assert.assertTrue(originalList.contains("Mike"));
        Assert.assertTrue(originalList.contains("Anna"));
        Assert.assertTrue(originalList.contains("Bob"));
        Assert.assertTrue(originalList.contains("Camila"));
    }

    @Test
    public void filterTest() {
        List<String> result = Streams.of(Arrays.asList("A", "BB", "CCC", "DDDD")).filter(s -> s.length() > 2).toList();

        Assert.assertTrue(result.contains("CCC"));
        Assert.assertTrue(result.contains("DDDD"));
        Assert.assertFalse(result.contains("A"));
        Assert.assertFalse(result.contains("BB"));
    }

    @Test
    public void complexTest() {
        Map<String, Apple> appleMap = Streams.of(Arrays.asList(
                new Apple("apple8", 80),
                new Apple("apple4", 150),
                new Apple("apple9", 70),
                new Apple("apple3", 90),
                new Apple("apple1", 100)
        )).filter(s -> s.getWeight() > 80)
                .transform(s -> {
                    s.setWeight(s.getWeight() + 50);
                    return s;
                })
                .filter(s -> s.getWeight() >= 150)
                .toMap(s -> s.getName(), s -> s);

        Assert.assertTrue(appleMap.containsKey("apple4") && appleMap.containsKey("apple1"));
        Assert.assertFalse(appleMap.containsKey("apple3"));
        Assert.assertFalse(appleMap.containsKey("apple8"));
        Assert.assertFalse(appleMap.containsKey("apple9"));
    }
}
