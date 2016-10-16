package ru.sbt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ScoringTests {
    private static Map<String, Double> map = new HashMap<>();

    @Before
    public void setUp() {
        map.put("Salary", 50_000d);
        map.put("City", 54d);
        map.put("PeriodOfMonth", 36d);
        map.put("AmountOfCredit", 800_000d);
        map.put("RequiredCity", 54d);
        map.put("Divider", 2d);
    }

    @Test
    public void testCity() {
        map.put("City", 55d);
        double result = getSummingResult();
        Assert.assertNotEquals(1.0d, result, 0.0d);
    }

    @Test
    public void testAmountOfCredit() {
        map.put("AmountOfCredit", 600_000d);
        double result = getSummingResult();
        Assert.assertEquals(1.0d, result, 0.0d);
    }

    @Test
    public void testSalary() {
        map.put("Salary", 30_000d);
        double result = getSummingResult();
        Assert.assertNotEquals(1.0d, result, 0.0d);
    }

    private double getSummingResult() {
        Node amountOfMonth = new DivisionNode();
        amountOfMonth.addNodes(new ParameterNode("AmountOfCredit"), new ParameterNode("PeriodOfMonth"));

        Node halfOfSalary = new DivisionNode();
        halfOfSalary.addNodes(new ParameterNode("Salary"), new ParameterNode("Divider"));

        Node payAbility = new LessThanNode();
        payAbility.addNodes(amountOfMonth, halfOfSalary);

        Node suitableCity = new EqualsNode();
        suitableCity.addNodes(new ParameterNode("City"), new ParameterNode("RequiredCity"));

        Node result = new AndNode();
        result.addNodes(suitableCity, payAbility);

        return result.getResult(map);
    }
}
