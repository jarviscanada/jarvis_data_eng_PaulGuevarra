package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RotateStringTest {

    String inputString = "hannah";
    String inputGoal = "hhanna";
    boolean expected = true;
    RotateString rs;

    @Before
    public void setUp() throws Exception {
        rs = new RotateString();
    }

    @Test
    public void isRotation() {
        Assert.assertEquals(expected, rs.isRotation(inputString,inputGoal));
    }
}