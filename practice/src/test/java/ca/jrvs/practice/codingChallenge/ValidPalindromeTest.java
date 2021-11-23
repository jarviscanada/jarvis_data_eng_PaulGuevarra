package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeTest {

    String validInput = "racecar";
    ValidPalindrome vp;
    @Before
    public void setUp() throws Exception {
        vp = new ValidPalindrome();
    }

    @Test
    public void isPalindrome() {
        Assert.assertEquals(true, vp.isPalindrome(validInput));
    }
}