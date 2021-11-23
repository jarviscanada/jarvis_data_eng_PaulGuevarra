package ca.jrvs.practice.codingChallenge;
/*
 * https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-c885613cbe0a45dca62abdc58be3fba2
 */
public class EvenOrOdd {


    public String oddEvenMod(int num){
        return (num%2 ==0 ? "Even": "Odd");
    }

    public String oddEvenBit(int num)
    {
        return ((num & 1)!=1 ? "Even" : "Odd");
    }

}
