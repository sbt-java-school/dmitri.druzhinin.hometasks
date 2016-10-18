package ru.sbt;

import org.junit.Assert;
import org.junit.Test;
import ru.sbt.impl.AbstractConverterToImpl;
import ru.sbt.impl.ConverterImpl;

import java.math.BigDecimal;

public class ConverterTests {
    private final Converter converter = new ConverterImpl();

    @Test
    public void testStringToInteger() {
        Assert.assertEquals(333, 1 + converter.convert(Integer.class, "332"));
    }

    @Test
    public void testStringToBigDecimal() {
        String value = "14.1759999999998";
        Assert.assertEquals(new BigDecimal(value), converter.convert(BigDecimal.class, value));
    }

    @Test
    public void testDoubleToInteger() {
        Assert.assertEquals(2, 1 + converter.convert(Integer.class, Double.valueOf(1.9d)));
    }

    @Test
    public void testMinuteToSecond() {
        converter.addConverterTo(Second.class, new AbstractConverterToImpl<Second>() {
            {
                this.addConverterFrom(Minute.class, valueFrom -> {
                    Minute value = (Minute) valueFrom;
                    return new Second(value.getMinutes() * 60 + value.getSeconds());
                });
            }
        });

        Assert.assertEquals(new Second(185), converter.convert(Second.class, new Minute(3, 5)));
    }
}
