package com.smart.tkl.euler.p152;

import com.smart.tkl.combinatorics.CombinatoricsUtils;
import com.smart.tkl.primes.Primes;
import com.smart.tkl.utils.MathUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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

        List<Integer> filtered = getFilteredValues();

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
            long numeratorSum = getSubsetSum(sublist1, mask, lcmSquared, halfLcmSquared);
            if(numeratorSum > halfLcmSquared) {
                //  System.out.println("Subset exceeded: " + numeratorSum);
                continue;
            }
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

    private long getSubsetSum(List<Integer> list, long bitMask, long lcm, long halfLcmSquared) {
        long numerator = 0;
        int i = 0;
        while (bitMask != 0) {
            if((bitMask & 1) == 1) {
                long value = list.get(i);
                numerator += lcm / value;
                if(numerator > halfLcmSquared) {
                    return numerator;
                }
            }
            bitMask >>= 1;
            i++;
        }
        return numerator;
    }

    private List<Integer> getFilteredValues() {
        List<Integer> result = new ArrayList<>();

        Set<Integer> excluded = new TreeSet<>();
        for(int n = this.limit; n >= 4; n--) {
            if(Primes.isPrime(n)) {
                int maxMultiplier = this.limit / n;
                if(maxMultiplier == 1) {
                   excluded.add(n);
                }
                else {
                    Set<Integer> multiples = new LinkedHashSet<>();
                    Set<Integer> relevantMultiples = new HashSet<>();
                    for(int i = 1; i <= maxMultiplier; i++) {
                        if(!excluded.contains(i * n)) {
                            multiples.add(i);
                            relevantMultiples.add(i);
                        }
                    }
                    int primeSquared = n * n;
                    for(int combinationSize = 2; combinationSize <= multiples.size(); combinationSize++) {
                        for(int[] combination : CombinatoricsUtils.combinations(multiples, combinationSize)) {
                            List<Integer> list = Arrays.stream(combination)
                                    .boxed()
                                    .collect(Collectors.toList());
                            List<Integer> squared = list.stream().map(i -> i * i).collect(Collectors.toList());

                            long lcm = MathUtils.LCM(squared);
                            int numerator = 0;
                            for(int value : squared) {
                                numerator += lcm / value;
                            }
                            if(numerator % primeSquared == 0) {
                                list.forEach(relevantMultiples::remove);
                            }
                        }
                    }
                    for(int multiple : relevantMultiples) {
                        excluded.add(n * multiple);
                    }
                }
            }
        }

        for(int i = 2; i <= this.limit; i++) {
            if(!excluded.contains(i)) {
               result.add(i);
            }
        }

        return result;
    }
}
