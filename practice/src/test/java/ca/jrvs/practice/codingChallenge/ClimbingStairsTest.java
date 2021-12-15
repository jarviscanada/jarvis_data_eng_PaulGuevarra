package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClimbingStairsTest {

    ClimbingStairs cs;
    int input =7;
    int expected =13;

    @Before
    public void setUp() throws Exception {
        cs= new ClimbingStairs();
    }

    @Test
    public void uniqueWays() {
        Assert.assertEquals(expected,cs.uniqueWays(input));
    }

    @Test
    public void uniqueWaysDP() {
        Assert.assertEquals(expected,cs.uniqueWaysDP(input));
    }
}