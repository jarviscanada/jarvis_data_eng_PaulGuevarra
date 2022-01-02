package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParenthesesTest {

    String testBrackets= "{{[]}}";
    String failTestBrackets = "[{{}]";
    ValidParentheses vp;
    @Before
    public void setUp() throws Exception {
       vp= new ValidParentheses();
    }

    @Test
    public void isValid() {
        Assert.assertEquals(true, vp.isValid(testBrackets));
        Assert.assertEquals(false,vp.isValid(failTestBrackets));

    }
}