package com.smart.tkl.cses.additional;

import java.util.Scanner;
public class CountingBits {

    public static void main(String[] args) {
        long n = new Scanner(System.in).nextLong();
        System.out.println(countBits(n));
    }

    public static long countBits(long n) {
        if(n <= 0) {
           return 0;
        }
        long pow2 = 1;
        int k = 0;
        while (pow2 * 2 < n) {
            pow2 *= 2;
            k++;
        }
        if(~n == 0) {
           return countAllOnes(k);
        }
        if(pow2 * 2 == n) {
           return 1 + countAllOnes(k + 1);
        }
        long left = n - pow2;
        return left + 1 + countBits(left) + countAllOnes(k);
    }

    /*Count bits from 1 to n = 2^k - 1*/
    private static long countAllOnes(int k) {
        if(k == 0) {
           return 0;
        }
        if(k == 1) {
           return 1;
        }
        return (long)Math.pow(2, k - 1) + 2 * countAllOnes(k - 1);
    }

}
