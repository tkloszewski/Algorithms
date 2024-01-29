package com.smart.tkl.euler.p41;

import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.ArrayList;
import java.util.List;

public class PandigitalPrime {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PrimesSieve sieve = new PrimesSieve(10000000);
        List<Integer> allPandigitalPrimes = getAllPandigitalPrimes(sieve);
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
        System.out.println(allPandigitalPrimes);
        System.out.println(search(allPandigitalPrimes, 10000000));
    }

    public static int search(List<Integer> pandigitalPrimes, int N) {
        return search(0, pandigitalPrimes.size() - 1, pandigitalPrimes, N);
    }

    private static int search(int left, int right, List<Integer> pandigitalPrimes, int N) {
        if(left == right) {
            int value = pandigitalPrimes.get(left);
            if(value > N) {
                value = left == 0 ? -1 : pandigitalPrimes.get(left - 1);
            }
            return value;
        }

        int middle = (left + right) / 2;
        int middleValue = pandigitalPrimes.get(middle);
        int leftValue = pandigitalPrimes.get(left);
        int rightValue = pandigitalPrimes.get(right);
        if(N == middleValue || N == rightValue || N == leftValue) {
           return N;
        }
        else if(N < middleValue) {
            return search(left, middle, pandigitalPrimes, N);
        }
        else {
            return search(middle + 1, right, pandigitalPrimes, N);
        }
    }

    private static List<Integer> getAllPandigitalPrimes(PrimesSieve sieve) {
        List<Integer> result = new ArrayList<>();
        List<Integer> pandigitalPrimes4 = getPandigitalPrimes(4, sieve);
        List<Integer> pandigitalPrimes7 = getPandigitalPrimes(7, sieve);
        result.addAll(pandigitalPrimes4);
        result.addAll(pandigitalPrimes7);
        return result;
    }

    private static List<Integer> getPandigitalPrimes(int digits, PrimesSieve sieve) {
        List<Integer> pandigitalPrimes = new ArrayList<>();
        boolean[] used = new boolean[digits + 1];
        fillPandigitalPrimes(digits, digits, 0, used, pandigitalPrimes, sieve);
        return pandigitalPrimes;
    }

    private static void fillPandigitalPrimes(int digitsLeft, int maxDigit, int value, boolean[] used,
                                             List<Integer> pandigitalPrimes, PrimesSieve sieve) {
         if(digitsLeft == 1) {
            for(int digit = 1; digit <= maxDigit; digit += 2) {
                if(!used[digit]) {
                   int num = 10 * value + digit;
                   if(sieve.isPrime(num)) {
                      pandigitalPrimes.add(num);
                   }
                }
            }
         }
         else {
             for(int digit = 1; digit <= maxDigit; digit++) {
                 if(!used[digit]) {
                     used[digit] = true;
                     fillPandigitalPrimes(digitsLeft - 1, maxDigit, 10 * value + digit, used, pandigitalPrimes, sieve);
                     used[digit] = false;
                 }
             }
         }
    }
}
