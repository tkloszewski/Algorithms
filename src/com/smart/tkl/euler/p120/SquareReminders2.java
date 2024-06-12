package com.smart.tkl.euler.p120;

import com.smart.tkl.lib.utils.ModuloUtils;

public class SquareReminders2 {

    private final static long mod = (long) Math.pow(10, 9) + 7;
    private final static long modInv3 = ModuloUtils.modInv(3, mod);

    public static void main(String[] args) {
        for(long A = 10000; A <= 10010; A++) {
            System.out.println(sumReminders(A, 3));
        }
    }

    public static long sumReminders(long A, int e) {
        if(e == 2) {
           return calcSumOfSquareReminders(A);
        }
        if(e == 3) {
           return calcSumOfCubeReminders(A);
        }
        throw new IllegalArgumentException("e must be 2 or 3");
    }

    /*
    * (A-1) * A * (A+1) - (4 + A)(A/2 - 1) / 2
    * For a odd we have maximum R(a,2) = a * (a - 1)
    * For a even we have maximum R(a, 2) = max(a * (a - 2), 2) where 2 is for a = 2
    * */
    private static long calcSumOfSquareReminders(long A) {
       long s1 = ((((A - 1) * A) % mod) * (A + 1)) % mod;
       s1 = (s1 * modInv3) % mod;

       long s2 = 0;
       if(A >= 4) {
           s2 = ((2 + A / 2) *  (A / 2 - 1)) % mod;
       }

       long result = (s1 - s2) % mod;
       if(result < 0) {
          result += mod;
       }

       return result;
    }

    /*
    * (A(A+1)/2)^2 - A(A+1)/2 - A/2 * (A/2+1) for A even
    * (A(A+1)/2)^2 - A(A+1)/2 - (A-1)/2 * (A+1)/2 for A odd
    * For a odd we have maximum R(a,3) = a * (a^2 - 1)
    * For a even we have maximum R(a,3) = a * (a^2 - 2)
    * */
    private static long calcSumOfCubeReminders(long A) {
      long sum = ((A * (A + 1)) / 2)  % mod;
      long s1 = ((sum * sum) % mod - sum) % mod;
      long s2 = (A / 2 * (A / 2 + 1)) % mod;
      long result = (s1 - s2) % mod;
      if(result < 0) {
         result += mod;
      }
      return result;
    }
}
