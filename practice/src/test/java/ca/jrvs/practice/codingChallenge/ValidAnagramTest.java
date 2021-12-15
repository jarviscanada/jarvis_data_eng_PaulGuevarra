package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidAnagramTest {

    String validInput1 = "tommarvoloriddle";
    String validInput2 = "iamlordvoldemort";
    boolean expected = true;
    ValidAnagram va;
    @Before
    public void setUp() throws Exception {
        va = new ValidAnagram();
    }

    @Test
    public void isAnagram() {
        Assert.assertEquals(expected,va.isAnagram(validInput1,validInput2));
    }
}