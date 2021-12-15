package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringToIntegerAtoiTest {


    String input = "          234";
    int expected = 234;
    StringToIntegerAtoi stia;

    @Before
    public void setUp() throws Exception {
        stia = new StringToIntegerAtoi();
    }

    @Test
    public void convertStringToInteger() {
        Assert.assertEquals(expected, stia.convertStringToInteger(input));
    }
}