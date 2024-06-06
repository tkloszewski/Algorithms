package com.smart.tkl.euler.p118.hk;

import com.smart.tkl.lib.combinatorics.permutation.SortedPermutationIterator;
import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PanDigitalPrimeSets2 {

    private final PrimesSieve primesSieve;

    public PanDigitalPrimeSets2(int digits) {
        int limit = (int)Math.pow(10, Math.min(digits, 8));
        primesSieve = new PrimesSieve(limit);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PanDigitalPrimeSets2 panDigitalPrimeSets = new PanDigitalPrimeSets2(9);
        int[] digits = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        long t1 = System.currentTimeMillis();
        List<Integer> result = panDigitalPrimeSets.findPrimeSums(digits);
        long time2 = System.currentTimeMillis();
        System.out.println(result.size());
        System.out.println("Searching time: " + (time2 - t1));
        System.out.println("Total time in ms: " + (time2 - time1));
    }

    public List<Integer> findPrimeSums(int[] digits) {
        List<Integer> sums = new ArrayList<>();
        SortedPermutationIterator permutationIterator = new SortedPermutationIterator(digits);
        while (permutationIterator.hasNext()) {
            digits = permutationIterator.next();
            int lastDigit = digits[digits.length - 1];
            if(lastDigit != 2 && lastDigit % 2 == 0) {
               continue;
            }
            LinkedList<Integer> primesSet = new LinkedList<>();
            fillPandigitalPrimes(digits, primesSet, sums);
        }
        Collections.sort(sums);
        return sums;
    }

    private void fillPandigitalPrimes(int[] digits, LinkedList<Integer> primesSet, List<Integer> sums) {
        fillPandigitalPrimes(digits, 0, primesSet, 0, sums);
    }

    private void fillPandigitalPrimes(int[] digits, int pos, LinkedList<Integer> primesSet, int sum, List<Integer> sums) {
        if(pos == digits.length) {
           sums.add(sum);
        }
        else {
           int currentValue = 0;
           while (pos < digits.length) {
               currentValue *= 10;
               currentValue += digits[pos++];

               if(!primesSet.isEmpty() && currentValue < primesSet.getLast()) {
                  continue;
               }

               if(isPrime(currentValue)) {
                  primesSet.add(currentValue);
                  fillPandigitalPrimes(digits, pos, primesSet, sum + currentValue, sums);
                  primesSet.removeLast();
               }
           }
        }
    }

    private boolean isPrime(int value) {
        if(value >= 100000000) {
           return false;
        }
        return primesSieve.isPrime(value);
    }
}
