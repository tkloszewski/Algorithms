package com.smart.tkl.euler.p152;

import com.smart.tkl.combinatorics.CombinatoricsUtils;
import com.smart.tkl.utils.MathUtils;
import com.smart.tkl.utils.PrimeFactor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class InverseSquares {

    private final int limit;

    public InverseSquares(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        InverseSquares inverseSquares = new InverseSquares(80);
        long count = inverseSquares.countWaysForInverseSquareSums();
        System.out.println("Ways: " + count);
        long time2 = System.currentTimeMillis();
        System.out.println("Time consumed: " + (time2 - time1));
    }

    public long countWaysForInverseSquareSums() {
        long count = 0;

        List<Integer> filtered = filterList();

        long lcm = MathUtils.LCM(filtered);
        long lcmSquared = lcm * lcm;

        List<Integer> sublist1 = filtered.stream()
                .filter(i -> i < this.limit / 2)
                .map(i -> i * i)
                .collect(Collectors.toList());

        List<Integer> sublist2 = filtered.stream()
                .filter(i -> i >= this.limit / 2)
                .map(i -> i * i)
                .collect(Collectors.toList());

        Map<Long, Long> numeratorSums = new LinkedHashMap<>();

        long halfLcmSquared = lcmSquared / 2;

        long maxSize = (long) Math.pow(2, sublist2.size());
        for(long mask = 1; mask < maxSize; mask++) {
            List<Integer> subset = getSubset(sublist2, mask);
            long numeratorSum = getNumeratorSum(subset, lcmSquared);
            long newValue = numeratorSums.getOrDefault(numeratorSum, 0L) + 1;
            numeratorSums.put(numeratorSum, newValue);

            if(numeratorSum == halfLcmSquared) {
               count++;
            }
        }

        maxSize = (long) Math.pow(2, sublist1.size());
        for(long mask = 1; mask < maxSize; mask++) {
            List<Integer> subset = getSubset(sublist1, mask);
            long numeratorSum = getNumeratorSum(subset, lcmSquared);
            long sumToCheck = halfLcmSquared - numeratorSum;
            if(numeratorSums.containsKey(sumToCheck)) {
               long sumCount = numeratorSums.get(sumToCheck);
               count += sumCount;
            }
            if(numeratorSum == halfLcmSquared) {
                count++;
            }
        }

        return count;
    }

    private static long getNumeratorSum(List<Integer> subset, long lcm) {
         long numerator = 0;
         for(int value : subset) {
             numerator += lcm / (long)value;
         }
         return numerator;
    }

    private List<Integer> getSubset(List<Integer> list, long bitMask) {
        List<Integer> result = new ArrayList<>();
        int i = 0;
        while (bitMask != 0) {
            if((bitMask & 1) == 1) {
                result.add(list.get(i));
            }
            bitMask >>= 1;
            i++;
        }
        return result;
    }

    private List<Integer> filterList() {
        List<Integer> result = new ArrayList<>();
        List<Integer> excludedPrimeFactors = getExcludedPrimeFactorsGreaterThan3();
        for(int i = 2; i <= this.limit; i++) {
            List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(i);
            boolean exclude = false;
            for(PrimeFactor primeFactor : primeFactors) {
                if(excludedPrimeFactors.contains(primeFactor.getFactor())) {
                   exclude = true;
                   break;
                }
            }
            if(!exclude) {
               result.add(i);
            }
        }
        return result;
    }

    private List<Integer> getExcludedPrimeFactorsGreaterThan3() {
        List<Integer> excluded = new ArrayList<>();
        List<Long> primes = MathUtils.generatePrimesUpTo(this.limit);
        for(long prime : primes) {
            if(prime > 3 && isPrimeFactorExcluded((int)prime)) {
              excluded.add((int)prime);
            }
        }
        return excluded;
    }

    private boolean isPrimeFactorExcluded(int prime) {
        int maxMultiplier = this.limit / prime;
        if(maxMultiplier == 1) {
           return true;
        }
        Set<Integer> squares = new LinkedHashSet<>();
        for(int i = 1; i <= maxMultiplier; i++) {
            squares.add(i * i);
        }
        int primeSquared = prime * prime;
        boolean excluded = true;

        for(int combinationSize = 2; combinationSize <= squares.size(); combinationSize++) {
            for(int[] combination : CombinatoricsUtils.combinations(squares, combinationSize)) {
                List<Integer> list = Arrays.stream(combination)
                        .boxed()
                        .collect(Collectors.toList());
                long lcm = MathUtils.LCM(list);
                int numerator = 0;
                for(int value : list) {
                    numerator += lcm / value;
                }
                if(numerator % primeSquared == 0) {
                    excluded = false;
                    break;
                }
            }
        }
        return excluded;
    }
}
