package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
/*
 * The TwoSum Problem
 * https://www.notion.so/jarvisdev/Two-Sum-9d4fe50115e04d30adec52d89c754be1
 */

public class TwoSum {

    public int[] bruteForce(int[] nums, int target)
    {
        int [] result = new int[2];
        for(int i = 0;i<nums.length; i++)
        {
            for(int k = i+1; k< nums.length; k++)
            {
                if(nums[i]+nums[k]==target)
                {
                    result[0]=i;
                    result[1]=k;
                }
            }
        }
        return result;
    }

    public int[] mapSolution(int[] nums, int target)
    {
        Map<Integer,Integer>values = new HashMap<Integer, Integer>();
        int[] result = new int[2];

        for(int i = 0; i< nums.length;i++)
        {
           if(values.containsKey(target-nums[i]))
           {

               result[0] = values.get(target-nums[i]);
               result[1] = i;
               return result;
           }
           else {
               values.put(nums[i], i);
           }
        }
        return result;
    }

}
