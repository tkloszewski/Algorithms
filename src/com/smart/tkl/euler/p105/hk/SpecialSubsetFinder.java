package com.smart.tkl.euler.p105.hk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SpecialSubsetFinder {

    public static void main(String[] args) {
        boolean subsetSumsUnique = isSpecialSumSet(new ArrayList<>(List.of(157, 150, 164, 119, 79, 159, 161, 139, 158)));
        System.out.println("Special sums: " + subsetSumsUnique);

        subsetSumsUnique = isSpecialSumSet(new ArrayList<>(List.of(81, 88, 75, 42, 87, 84, 86, 65)));
        System.out.println("Special sums: " + subsetSumsUnique);

        subsetSumsUnique = isSpecialSumSet(new ArrayList<>(List.of(81)));
        System.out.println("Special sums: " + subsetSumsUnique);
    }

    public static boolean isSpecialSumSet(List<Integer> set) {
        Collections.sort(set);
        return sumIsGreaterForSubsetsWithMoreElements(set) && areSubsetSumsUnique(set);
    }

    private static boolean areSubsetSumsUnique(List<Integer> set) {
        if(set.size() != new HashSet<>(set).size()) {
           return false;
        }

        int minSum = set.get(0);
        int maxSum = 0;
        for(int el : set) {
            maxSum += el;
        }

        int size = set.size();
        int sumsSize = maxSum - minSum + 1;

        if(size < 63) {
           long subsetsCount = (long) Math.pow(2, size) - 1;
           if(sumsSize < subsetsCount) {
              return false;
           }
        }
        else {
            if(Math.log10(sumsSize) < size * Math.log10(2)) {
                return false;
            }
        }

        boolean[][] sums = new boolean[size + 1][sumsSize];

        for(int i = 1; i <= size; i++) {
            int elem = set.get(i - 1);
            sums[i][elem - minSum] = true;
            for(int sum = minSum; sum <= maxSum; sum++) {
                int idx = sum - minSum;
                if(sums[i -1][idx]) {
                   sums[i][idx] = true;
                   sums[i][idx + elem] = true;
                }
            }
        }

        long distinctSums = 0;
        for(int sum = minSum; sum <= maxSum; sum++) {
            if(sums[size][sum - minSum]) {
               distinctSums++;
            }
        }

        long subsets = (long)Math.pow(2, size) - 1;
        return distinctSums == subsets;
    }

    private static boolean sumIsGreaterForSubsetsWithMoreElements(List<Integer> workingSet) {
        workingSet = new ArrayList<>(workingSet);
        int maxIndex = workingSet.size() / 2;

        int[] sums = new int[workingSet.size()];
        sums[0] = workingSet.get(0);
        for(int i = 1; i < workingSet.size(); i++) {
            sums[i] = sums[i - 1] + workingSet.get(i);
        }

        for(int index = 1; index <= maxIndex; index++) {
            int moreNumerousSubsetSum = getSum(workingSet, sums, 0, index);
            int lessNumerousSubsetSum = getSum(workingSet, sums, workingSet.size() - index, workingSet.size() - 1);
            if(moreNumerousSubsetSum <= lessNumerousSubsetSum) {
                return false;
            }
        }

        return true;
    }

    private static int getSum(List<Integer> set, int[] sums, int from, int to) {
        return sums[to] - sums[from] + set.get(from);
    }

}
