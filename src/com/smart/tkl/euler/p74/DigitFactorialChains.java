package com.smart.tkl.euler.p74;
import com.smart.tkl.utils.MathUtils;
import java.util.*;

public class DigitFactorialChains {

    private final int digitLimit;
    private final int expectedChainLength;

    private final int[] factorials;
    private final Map<Integer, ChainEntry> chainLengthCache = new HashMap<>();

    public DigitFactorialChains(int digitLimit, int expectedChainLength) {
        this.digitLimit = digitLimit;
        this.expectedChainLength = expectedChainLength;
        this.factorials = new int[Math.max(9, digitLimit) + 1];
        init();
    }

    public static void main(String[] args) {
        for(int digitsLength = 2; digitsLength <= 12; digitsLength++) {
            for (int chainSize = 1; chainSize <= 65; chainSize++) {
                System.out.println("------------------------------------------------------------------------");
                System.out.printf("Digits length: %d, chainSize: %d \n", digitsLength, chainSize);
                DigitFactorialChains digitFactorialChains = new DigitFactorialChains(digitsLength, chainSize);
                long time1 = System.currentTimeMillis();
                int count = digitFactorialChains.count();
                long time2 = System.currentTimeMillis();
                System.out.printf("Digit factorials chains count: %d. Solution took %d ms \n", count, (time2 - time1));


                /*time1 = System.currentTimeMillis();
                int slowCount = digitFactorialChains.slowCount();
                time2 = System.currentTimeMillis();
                System.out.printf("Digit factorials chains slow count: %d. Solution took %d ms \n", slowCount, (time2 - time1));

                if(slowCount != count) {
                    System.out.println("Unequal!! ");
                }*/
            }
        }
    }

    public int count() {
        int result = 0;
        for(int digitsLength = digitLimit; digitsLength >= 1; digitsLength--) {
            int[] combination = new int[10];
            int combinationsCount = countCombinations(0, digitsLength, combination, digitsLength);
            result += combinationsCount;
        }
        return result;
    }

    public int slowCount() {
        int result = 0;
        int limit = (int)Math.pow(10, digitLimit);
        for(int i = 1; i < limit; i++) {
            if(containsExactlySixtyNonRepeatingTerms2(i)) {
                result ++;
            }
        }
        return result;
    }

    private int countCombinations(int idx, int left, int[] combination, int digitsLength) {
        if(idx + 1 == combination.length) {
            combination[idx] = left;
            if(combination[0] == digitsLength) {
                return 0;
            }
            CheckResult checkResult = checkChainForCombination(combination);
            if(checkResult.hasChain) {
                int permutationsCount;
                if (!checkResult.includeOnlyOne) {
                    permutationsCount = countPermutations(combination, digitsLength);
                    if(checkResult.excludeOnlyOne) {
                        permutationsCount--;
                    }
                }
                else {
                    permutationsCount = 1;
                }
                //  System.out.println("Permutation count for combination: " + Arrays.toString(combination) + " = " + permutationsCount);
                return permutationsCount;
            }
            return 0;
        }
        int result = 0;
        for(int i = 0; i <= left; i++) {
            combination[idx] = i;
            result += countCombinations(idx + 1, left - i, combination, digitsLength);
        }
        return result;
    }

    private void init() {
        factorials[0] = 1;
        int limit = Math.max(9, digitLimit);
        for(int i = 1; i <= limit ;i++) {
            factorials[i] = i * factorials[i - 1];
        }
    }

    private CheckResult checkChainForCombination(int[] combination) {
        int sum = toFactorialSum(combination);
        int lastSum = sum;
        int chainLength;

        if (!chainLengthCache.containsKey(sum)) {
            Set<Integer> chain = new LinkedHashSet<>();
            Map<Integer, Integer> chainIndexMap = new HashMap<>();
            int i = 0;
            while (!chain.contains(sum) && chain.size() <= expectedChainLength) {
                chainIndexMap.put(sum, i++);
                chain.add(sum);
                lastSum = sum;
                sum = toFactorialSum(sum);
            }

            if(chain.contains(sum)) {
                int index = chainIndexMap.get(sum);
                int pos = 0, prevTermValue = 0;
                for(int termValue : chain) {
                    if(pos <= index) {
                        chainLengthCache.put(termValue, new ChainEntry(chain.size() - pos, lastSum));
                    }
                    else {
                        chainLengthCache.put(termValue, new ChainEntry(chain.size() - index, prevTermValue));
                    }
                    prevTermValue = termValue;
                    pos++;
                }
            }

            chainLength = chain.size();
        }
        else {
            ChainEntry chainEntry = chainLengthCache.get(sum);
            chainLength = chainEntry.length;
            lastSum = chainEntry.lastSum;
        }

        if(chainLength + 1 == expectedChainLength) {
            if(MathUtils.formDigitPermutations(combination.clone(), lastSum)) {
                return new CheckResult(true, true);
            }
            return new CheckResult(true, false);
        }
        else if(chainLength == expectedChainLength && MathUtils.formDigitPermutations(combination.clone(), lastSum)) {
            return new CheckResult(true, false, true);
        }

        return new CheckResult(false, false);
    }

    private boolean containsExactlySixtyNonRepeatingTerms2(int sum) {
        Set<Integer> chain = new LinkedHashSet<>();
        while (!chain.contains(sum)) {
            chain.add(sum);
            sum = toFactorialSum(sum);
        }

        //we must include the number that produces the first sum
        return chain.size() == expectedChainLength;
    }

    private int chainLength(int sum) {
        Set<Integer> uniqueSums = new LinkedHashSet<>();
        while (!uniqueSums.contains(sum)) {
            uniqueSums.add(sum);
            sum = toFactorialSum(sum);
        }
        return uniqueSums.size();
    }

    private int toFactorialSum(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += factorials[digit];
            n = n / 10;
        }
        return sum;
    }

    private int toFactorialSum(int[] combination) {
        int sum = 0;
        for(int i = 0; i <= 9; i++) {
            int freq = combination[i];
            sum += factorials[i] * freq;
        }
        return sum;
    }

    private int countPermutations(int[] combination, int length) {
        int result = 1;
        if(combination[0] == 0) {
            int denominator = 1;
            for(int freq : combination) {
                denominator *= factorials[freq];
            }
            result = factorials[length] / denominator;
        }
        else if(combination[0] < length) {
            int denominator = 1;
            for(int i = 1; i < combination.length; i++) {
                int freq = combination[i];
                denominator *= factorials[freq];
            }
            int numerator = factorials[length - combination[0]];
            result = (int)MathUtils.combinations(length - 1, combination[0]) * (numerator / denominator);
        }
        return result;
    }

    private static class CheckResult {
        boolean hasChain;
        boolean excludeOnlyOne;
        boolean includeOnlyOne;

        public CheckResult(boolean hasChain, boolean excludeOnlyOne) {
            this.hasChain = hasChain;
            this.excludeOnlyOne = excludeOnlyOne;
        }

        public CheckResult(boolean hasChain, boolean excludeOnlyOne, boolean includeOnlyOne) {
            this.hasChain = hasChain;
            this.excludeOnlyOne = excludeOnlyOne;
            this.includeOnlyOne = includeOnlyOne;
        }
    }

    private static class ChainEntry {
        int length;
        int lastSum;

        public ChainEntry(int length, int lastSum) {
            this.length = length;
            this.lastSum = lastSum;
        }
    }
}
