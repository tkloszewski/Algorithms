package com.smart.tkl.euler.p74;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DigitFactorialChainsGenerator {

    private final int limit;

    private final int[] factorials = new int[10];
    private int[] digitFactorialSums;

    private Map<Integer, List<Integer>> chainSizeMap = new HashMap<>();

    public DigitFactorialChainsGenerator(int limit) {
        this.limit = limit;
        init();
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        DigitFactorialChainsGenerator generator = new DigitFactorialChainsGenerator(147);
        List<Integer> terms = generator.getTerms(147 ,1);
        long time2 = System.currentTimeMillis();
        System.out.println(terms);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public List<Integer> getTerms(int limit, int chainSize) {
        List<Integer> result = new ArrayList<>();
        for(int n = 0; n <= limit; n++) {
            if(isChainWithLength(n, chainSize)) {
               result.add(n);
            }
        }
        return result.isEmpty() ? List.of(-1) : result;
    }

    public List<Integer> getTerms(int limit, int chainSize, Map<Integer, List<Integer>> chainSizeMap) {
        List<Integer> result = new ArrayList<>();
        List<Integer> terms = chainSizeMap.get(chainSize);
        for(int term : terms) {
            if(term <= limit) {
                result.add(term);
            }
            else {
                break;
            }
        }
        return result.isEmpty() ? List.of(-1) : result;
    }

    public Map<Integer, List<Integer>> getChainSizeMap(List<Integer> chainSizes) {
        Map<Integer, List<Integer>> chainSizeMap = new HashMap<>();

        int maxSize = 0;
        for(int size : chainSizes) {
            maxSize = Math.max(maxSize, size);
            chainSizeMap.put(size, new ArrayList<>());
        }

        for(int n = 0; n <= limit; n++) {
            int chainSize = getChainSize(n, maxSize);
            if(chainSizeMap.containsKey(chainSize)) {
               chainSizeMap.get(chainSize).add(n);
            }
        }

        return chainSizeMap;
    }

    private boolean isChainWithLength(int n, int length) {
        int nonRepeatingTerms = 1;
        Set<Integer> chain = new LinkedHashSet<>();
        chain.add(n);
        int next = toFactorialSum(n);

        while (!chain.contains(next)) {
           nonRepeatingTerms++;
           if(nonRepeatingTerms > length) {
              return false;
           }
           chain.add(next);
           next = toFactorialSum(next);
        }

        return nonRepeatingTerms == length;
    }

    private int getChainSize(int n, int maxLength) {
        int nonRepeatingTerms = 1;
        Set<Integer> chain = new LinkedHashSet<>();
        chain.add(n);
        int next = toFactorialSum(n);

        while (!chain.contains(next)) {
            nonRepeatingTerms++;
            if(nonRepeatingTerms > maxLength) {
                return -1;
            }
            chain.add(next);
            next = toFactorialSum(next);
        }

        return nonRepeatingTerms;
    }

    private int toFactorialSum(int n) {
        if(n < digitFactorialSums.length) {
           return digitFactorialSums[n];
        }
        int num = n;
        int sum = 0;
        while (num > 0) {
            int digit = num % 10;
            sum += factorials[digit];
            num = num / 10;
        }
        return sum;
    }

    private void init() {
        factorials[0] = 1;
        for(int i = 1; i <= 9; i++) {
            factorials[i] = factorials[i - 1] * i;
        }

        int maxFactorialSum = String.valueOf(limit).length() * factorials[9];
        digitFactorialSums = new int[maxFactorialSum + 1];
        digitFactorialSums[0] = 1;

        for(int n = 1; n <= maxFactorialSum; n++) {
            int num = n;
            int sum = 0;
            while (num > 0) {
                int digit = num % 10;
                sum += factorials[digit];
                num = num / 10;
            }
            digitFactorialSums[n] = sum;
        }
    }
}
