package ca.jrvs.practice.codingChallenge;
/*
 *https://www.notion.so/jarvisdev/Valid-Palindrome-d50eedad7b5d415193901fdaa2f09cb7
 */
public class ValidPalindrome {

    public boolean isPalindrome (String s){
        int len = s.length();
            for (int i = 0; i < len / 2; i++) {
                if (s.charAt(i) != s.charAt(len - i - 1)) {
                return false;
                }

            }
        return true;
    }
}
