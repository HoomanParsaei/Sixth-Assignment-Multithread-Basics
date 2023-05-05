package sbu.cs;

/*
    In this exercise, you must write a multithreaded program that finds all
    integers in the range [1, n] that are divisible by 3, 5, or 7. Return the
    sum of all unique integers as your answer.
    Note that an integer such as 15 (which is a multiple of 3 and 5) is only
    counted once.
    The Positive integer n > 0 is given to you as input. Create as many threads as
    you need to solve the problem. You can use a Thread Pool for bonus points.
    Example:
    Input: n = 10
    Output: sum = 40
    Explanation: Numbers in the range [1, 10] that are divisible by 3, 5, or 7 are:
    3, 5, 6, 7, 9, 10. The sum of these numbers is 40.
    Use the tests provided in the test folder to ensure your code works correctly.
 */

import java.util.HashSet;
import java.util.Set;

public class FindMultiples
{
    public static Set<Integer> set;
    public static int sum;

    public static class find_sum_divise implements Runnable{
        private final int n;
        private final int divisor;

        public find_sum_divise(int n, int divisor) {
            this.n = n;
            this.divisor = divisor;
        }

        public static synchronized void addToSum(int i){
            if(!set.contains(i)){
                sum += i;
                set.add(i);
            }
        }

        @Override
        public void run(){
            for(int i=divisor ; i <= n ; i+= divisor){
                addToSum(i);
            }
        }
    }
    /*
    The getSum function should be called at the start of your program.
    New Threads and tasks should be created here.
    */
    public int getSum(int n) {
        set = new HashSet<>();
        sum=0;
        Thread divise_three = new Thread(new find_sum_divise(n,3));
        Thread divise_five = new Thread(new find_sum_divise(n,5));
        Thread divise_seven = new Thread(new find_sum_divise(n,7));
        divise_seven.start();
        divise_five.start();
        divise_three.start();

        try {
            divise_three.join();
            divise_five.join();
            divise_seven.join();
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        return sum;
    }

    public static void main(String[] args) {
    }
}