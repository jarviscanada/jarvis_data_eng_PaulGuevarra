package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class ValidParentheses {

    public boolean isValid(String s){

        Stack<Character> stack = new Stack<Character>();

        for (int i =0; i <s.length(); i++) {

            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                stack.push(s.charAt(i));
            }

            if (stack.empty()) {
                return false;
            }
            char check;
            switch (s.charAt(i)) {
                case ')':
                    check = stack.pop();
                    if(check!='('){
                        return false;
                    }
                    break;
                case '}':
                    check = stack.pop();
                    if(check!='{'){
                        return false;
                    }
                    break;
                case ']':
                    check = stack.pop();
                    if(check!='['){
                        return false;
                    }
                    break;


            }
        }
        return (stack.empty());
    }
}
