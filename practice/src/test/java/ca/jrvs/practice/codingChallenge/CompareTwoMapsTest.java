package ca.jrvs.practice.codingChallenge;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompareTwoMapsTest {

    CompareTwoMaps ctm;
    Map<Character, Integer> map1= new HashMap<Character,Integer>();
    Map<Character, Integer> map2= new HashMap<Character,Integer>();

    @Before
    public void setUp() throws Exception {
    ctm = new CompareTwoMaps();
    map1.put('a',1);
    map1.put('b',2);
    map1.put('c',3);

    map2.put('a',1);
    map2.put('b',2);
    map2.put('c',3);

    }

    @Test
    public void compareMaps() {
        Assert.assertEquals(true,ctm.compareMaps(map1,map2));

    }

}