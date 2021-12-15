package ca.jrvs.practice.codingChallenge;

/*
 * https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-eac71373175f4904a00ae3fa0b100187
 */
public class ClimbingStairs {

    public int uniqueWays(int numOfStairs){
        if(numOfStairs==0) {
            return 0;
        }
        else if (numOfStairs==1) {
            return 1;
        }
        return uniqueWays(numOfStairs-1)+uniqueWays(numOfStairs-2);
    }

    public int uniqueWaysDP(int numOfStairs){
        int[] arr = new int[numOfStairs+2];
        arr[0] = 0;
        arr[1] =1;

        for(int i=2;i<=numOfStairs;i++)
        {
            arr[i]=arr[i-1]+arr[i-2];
        }

        return arr[numOfStairs];

    }
}
