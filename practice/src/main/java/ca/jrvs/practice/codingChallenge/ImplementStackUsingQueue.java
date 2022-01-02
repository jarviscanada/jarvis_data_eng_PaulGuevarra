package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

public class ImplementStackUsingQueue {
    static Queue<Integer> q1 = new LinkedList<>();
    static Queue<Integer> q2 = new LinkedList<>();

    static int currentSize;

    public ImplementStackUsingQueue(){
        currentSize = 0;
    }

    static void push(int x)
    {
        currentSize++;
        q2.add(x);

        while (!q1.isEmpty()){
            q2.add(q1.peek());
            q1.remove();
        }
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    static int pop()
    {
        if(q1.isEmpty())
        {
            return -1;
        }
        int topOfStack = q1.peek();
        q1.remove();
        currentSize--;
        return topOfStack;
    }

    static int top()
    {
        if(q1.isEmpty())
        {
            return -1;
        }
        return(q1.peek());
    }
    static int size()
    {
       return currentSize;
    }

    public boolean empty() {
        if(currentSize==0)
        {
            return true;
        }
        return false;
    }
}
