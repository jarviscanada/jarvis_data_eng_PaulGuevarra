package ca.jrvs.practice.codingChallenge;

import java.util.Locale;

public class StringToIntegerAtoi {

    public int convertStringToInteger(String s)
    {
        s = s.trim();
        char[] characters = s.toCharArray();
        boolean hasDigit = false;
        String result = "";

        for(char c : characters)
        {
            if(Character.isDigit(c))
            {
                result+=c;
                hasDigit = true;
            }
            else if(c=='+'||c=='-') {
                if (result.equals("+") || result.equals("-") || hasDigit == true) {
                    break;
                }
                result+=c;
            }

            else if(Character.isLetter(c)||!Character.isDigit(c)){
                break;
            }
        }
        if(hasDigit)
        {
            double convertedResult = Double.parseDouble(result);
            if (convertedResult > Integer.MAX_VALUE)
                return Integer.MAX_VALUE;

            if (convertedResult < Integer.MIN_VALUE)
                return Integer.MIN_VALUE;

            return (int) convertedResult;
        }

        return 0;
    }

}
