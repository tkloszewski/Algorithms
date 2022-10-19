package com.smart.tkl.combinatorics;

import java.util.*;

public class CombinatoricsUtils {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long count = countCombinations(60, 30);
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count + " in ms => " + (time2 - time1)) ;
    }

    public static long countCombinations(int n, int k) {
        assert n >= k;
        long[][] memo = new long[n + 1][k + 1];
        return countCombinations(n, k , memo);
    }

    private static long countCombinations(int n, int k, long[][] memo) {
        if(memo[n][k] != 0) {
            return memo[n][k];
        }
        long result;
        if(k == 0 || k == 1 || k == n - 1) {
            result = n;
        }
        else if (k == n) {
            result = 1;
        }
        else {
            result = countCombinations(n - 1, k - 1, memo) + countCombinations(n - 1, k, memo);
        }
        memo[n][k] = result;
        memo[n][n - k] = result;
        return result;
    }

    public static Set<int[]> combinations(Set<Integer> input, int length) {
        if(input == null || input.size() == 0) {
            throw new IllegalArgumentException("Input set must not be empty.");
        }
        if(length <= 0 || length > input.size()) {
            throw new IllegalArgumentException("Invalid combination length.");
        }
        return generateCombinations(input, length);
    }

    private static Set<int[]> generateCombinations(Set<Integer> input, int length) {
        Set<int[]> combinations = new LinkedHashSet<>();
        Context context = new Context(input, length);
        addCombinations(combinations, context);
        return combinations;
    }

    private static void addCombinations(Set<int[]> combinations, Context context) {
        if(context.index == context.data.length) {
            int[] combination = context.data.clone();
            combinations.add(combination);
        }
        else if(context.end - context.start + 1 == context.elementsLeft) {
            int i = 0;
            while (i < context.elementsLeft) {
                context.data[context.index + i] = context.input[context.start + i];
                i++;
            }
            int[] combination = context.data.clone();
            combinations.add(combination);
        }
        else if(context.start <= context.end) {
            context.data[context.index] = context.input[context.start];

            Context context1 = oneElementSmallerCombinationContext(context);
            Context context2 = oneElementSmallerInputCombinationContext(context);

            addCombinations(combinations, context1);
            addCombinations(combinations, context2);
        }
    }

    private static Context oneElementSmallerCombinationContext(Context context) {
        return context.copyWith(context.elementsLeft -1, context.start + 1, context.end, context.index + 1);
    }

    private static Context oneElementSmallerInputCombinationContext(Context context) {
        return context.copyWith(context.elementsLeft, context.start + 1, context.end, context.index);
    }

    private static class Context {
        int[] input;
        int[] data;
        int elementsLeft;
        int start;
        int end;
        int index;

        Context() {

        }

        Context(Set<Integer> input, int length) {
            this.input = input.stream().mapToInt(Integer::intValue).toArray();
            this.data = new int[length];
            this.elementsLeft = length;
            this.start = 0;
            this.end = input.size() - 1;
            this.index = 0;
        }

        Context copyWith(int elementsLeft, int start, int end, int index) {
            Context context = new Context();
            context.input = this.input;
            context.data = this.data;
            context.elementsLeft = elementsLeft;
            context.start = start;
            context.end = end;
            context.index = index;
            return context;
        }
    }
}
