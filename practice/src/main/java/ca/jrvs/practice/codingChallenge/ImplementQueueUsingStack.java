package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Stack;

public class ImplementQueueUsingStack {

    static Stack<Integer> s1 = new Stack<Integer>();
    static Stack<Integer> s2 = new Stack<Integer>();

    static int currentSize;

    public ImplementQueueUsingStack(){
        currentSize = 0;
    }

    public void push(int x){

        while(!s1.isEmpty())
        {
            s2.push(s1.pop());

        }
        s1.push(x);
        while(!s2.isEmpty())
        {
            s1.push(s2.pop());
        }


    }

    public int pop()
    {
        int i = s1.peek();
        s1.pop();
        return i;
    }

    public int peek()
    {
        return s1.peek();
    }

    public boolean isEmpty(){
        return s1.isEmpty();
    }

}
