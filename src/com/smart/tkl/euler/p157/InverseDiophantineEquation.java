package com.smart.tkl.euler.p157;

import com.smart.tkl.lib.utils.Fraction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class InverseDiophantineEquation {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long count = countAllSolutions();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("TIme in ms: " + (time2 - time1));
    }

    public static long countAllSolutions() {
       long count = 0;
       for(int n = 1; n <= 9; n++) {
           long solutions = countSolutions(n);
           count += solutions;
       }
       return count;
    }

    private static long countSolutions(int n) {
        long pow1 = (long)Math.pow(10, n);
        long pow2 = pow1 * pow1;
        List<Long> tenPowDivisors = getDivisorsOfTenPow(2 * n);

        Set<Solution> solutions = new HashSet<>();

        for(long div1 : tenPowDivisors) {
            if(div1 > pow1) {
               break;
            }
            long div2 = pow2 / div1;
            long pa = div1 + pow1;
            long pb = div2 + pow1;
            Fraction fraction = new Fraction(pb ,pa);
            List<Integer> divisors = listDivisors((int)pa);
            for(int div : divisors) {
                if(div % fraction.getDenominator() == 0) {
                   int b = ((int)fraction.getNumerator() * div) / (int)fraction.getDenominator();
                   solutions.add(new Solution(div, b));
                }
            }
        }

        return solutions.size();
    }

    private static List<Long> getDivisorsOfTenPow(int n) {
        List<Long> result = new ArrayList<>((n+1) * (n+1));
        List<Long> twoPowers = new ArrayList<>(n);
        List<Long> fivePowers = new ArrayList<>(n);

        long twoPow = 1;
        long fivePow = 1;
        for(int i = 0; i <= n; i++) {
            twoPowers.add(twoPow);
            fivePowers.add(fivePow);
            twoPow *= 2;
            fivePow *= 5;
        }
        for(long pow1 : twoPowers) {
            for(long pow2 : fivePowers) {
                result.add(pow1 * pow2);
            }
        }

        Collections.sort(result);

        return result;
    }

    private static List<Integer> listDivisors(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        result.add(n);

        int step = n % 2 == 0 ? 1 : 2;
        int start = n % 2 == 0 ? 2 : 3;
        int sqrt = (int)Math.sqrt(n);
        int end = sqrt;
        if(sqrt * sqrt == n) {
            result.add(sqrt);
            end = sqrt - 1;
        }

        for(int i = start; i <= end; i += step) {
            if(n % i == 0) {
                result.add(i);
                result.add(n/i);
            }
        }

        return result;
    }

    private static class Solution {
        int x, y;

        public Solution(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Solution solution = (Solution) o;
            return x == solution.x && y == solution.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
