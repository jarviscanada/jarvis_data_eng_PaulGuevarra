package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EvenOrOddTest {

    int input1 = 1; String expected1 = "Odd";
    int input2 = 2; String expected2 = "Even";
    EvenOrOdd evo;

    @Before
    public void setUp() throws Exception {
        evo = new EvenOrOdd();
    }

    @Test
    public void oddEvenMod() {
        Assert.assertEquals(expected1, evo.oddEvenMod(input1));
        Assert.assertEquals(expected2, evo.oddEvenMod(input2));

    }

    @Test
    public void oddEvenBit() {
        Assert.assertEquals(expected1, evo.oddEvenMod(input1));
        Assert.assertEquals(expected2, evo.oddEvenMod(input2));
    }
}