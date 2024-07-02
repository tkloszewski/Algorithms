package com.smart.tkl.euler.p125;

import java.util.Set;
import java.util.TreeSet;

public class PalindromicSums2 {

    public static void main(String[] args) {
        //5, 55, 77, 181, 313, 434, 505, 545, 595, 636, 818, 1001, 1111, 1441, 1771, 4334, 6446
        long time1 = System.currentTimeMillis();
        System.out.println(findPalindromeSums(1000000000, 1));
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long findPalindromeSums(long limit, long d) {
        long result = 0;
        Set<Long> palindromeSums = new TreeSet<>();
        for(long a = 1; 2 * a * a + 2 * a * d < limit - d * d; a++) {
            long sum = a * a;
            long next = a + d;
            sum += next * next;

            while (sum < limit) {
                if(isPalindrome(sum)) {
                   if(palindromeSums.add(sum)) {
                      result += sum;
                   }
                }
                next += d;
                sum += next * next;
            }
        }
        return result;
    }

    private static boolean isPalindrome(long n) {
        long num = n;
        long reversed = 0;
        while (n > 0) {
            int digit = (int)(n % 10);
            if(digit == 0 && reversed == 0) {
               return false;
            }
            reversed = reversed * 10 + digit;
            n = n / 10;
        }
        return num == reversed;
    }
}
