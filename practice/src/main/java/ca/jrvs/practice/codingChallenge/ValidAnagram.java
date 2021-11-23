package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

public class ValidAnagram {

    public boolean isAnagram(String s, String t)
    {
        if(s.length()!=t.length())
        {
            return false;
        }
        Map<Character, Integer> letters = new HashMap<Character, Integer>();
        for (int i = 0;i<s.length();i++)
        {
            char currentLetterS = s.charAt(i);
            if(letters.containsKey(currentLetterS))
            {
                letters.put(currentLetterS,letters.get(currentLetterS)+1);
            }
            else
            {
                letters.put(currentLetterS,1);
            }
        }
        for (int i = 0;i<t.length();i++)
        {
            char currentLetterT = t.charAt(i);
            if(letters.containsKey(currentLetterT)) {
                if (letters.get(currentLetterT) == 1) {
                    letters.remove(currentLetterT, 1);
                } else {
                    letters.put(currentLetterT, letters.get(currentLetterT) - 1);
                }
            }
        }
        if(letters.size()!=0) {
            return false;
        }
        return true;
    }
}
