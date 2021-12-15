package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSumTest {

    TwoSum ts;
    int[] input = {1,2,5,6,7,9,13,14};
    int inputTarget = 12;
    int[] expected= {2,4};

    @Before
    public void setUp() throws Exception {
    ts = new TwoSum();
    }

    @Test
    public void bruteForce() {
        Assert.assertArrayEquals(expected,ts.bruteForce(input, inputTarget));
    }

    @Test
    public void mapSolution() {
        Assert.assertArrayEquals(expected,ts.mapSolution(input,inputTarget));
    }
}