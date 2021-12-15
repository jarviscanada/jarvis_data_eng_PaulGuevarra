package ca.jrvs.practice.codingChallenge;

public class RotateString {

    public boolean isRotation(String word, String goal)
    {
        if(word.length() != goal.length())
        {
            return false;
        }
        String longWord = word+word;
        if(longWord.contains(goal))
        {
            return true;
        }
        return false;
    }
}
