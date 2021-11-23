package ca.jrvs.practice.codingChallenge;

import java.util.Map;
/*
 * Compare Two Maps
 * https://www.notion.so/jarvisdev/How-to-compare-two-maps-b6e0b4f961ed499487823ee875b62920
 */
public class CompareTwoMaps {

    public <K,V> boolean compareMaps(Map<K,V> m1, Map<K,V>m2){
    if(m1.size()!=m2.size()){return false;}

    for(K key: m1.keySet())
    {
        if(m1.get(key)!=m2.get(key))
        {
            return false;
        }
    }
    return true;
    }

}
