package com.smart.tkl.euler.p105;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpecialSubsetTester {

    private static final String PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p105\\p105_sets.txt";

    public static void main(String[] args) throws IOException {
        List<List<Integer>> sets = new SetsReader(PATH).readSets();
        System.out.println("Number of sets read from file: " + sets.size());

        SpecialSubsetTester specialSubsetTester = new SpecialSubsetTester();
        List<List<Integer>> specialSumSets = specialSubsetTester.identifySpecialSets(sets);
        System.out.println("Number of special sum sets: " + specialSumSets.size());

        int totalSum = 0;
        for(List<Integer> specialSumSet : specialSumSets) {
            totalSum += sum(specialSumSet);
        }
        System.out.println("Total sum of all special sum sets: " + totalSum);
    }

    public List<List<Integer>> identifySpecialSets(List<List<Integer>> sets) {
        List<List<Integer>> result = new ArrayList<>();
        for(List<Integer> set : sets) {
            if(isSpecialSumSet(set)) {
                result.add(set);
            }
        }
        return result;
    }

    private boolean isSpecialSumSet(List<Integer> workingSet) {
        return sumsAreNotEqualForDisjointSets(workingSet) && sumIsGreaterForSubsetsWithMoreElements(workingSet);
    }

    private boolean sumIsGreaterForSubsetsWithMoreElements(List<Integer> workingSet) {
        workingSet = new ArrayList<>(workingSet);
        Collections.sort(workingSet);
        int maxIndex = workingSet.size() / 2;
        for(int index = 1; index <= maxIndex; index++) {
            List<Integer> moreNumerousSubset = workingSet.subList(0, index + 1);
            List<Integer> lessNumerousSubset = workingSet.subList(workingSet.size() - index, workingSet.size());
            int moreNumerousSubsetSum = sum(moreNumerousSubset);
            int lessNumerousSubsetSum = sum(lessNumerousSubset);
            if(moreNumerousSubsetSum <= lessNumerousSubsetSum) {
                return false;
            }
        }
        return true;
    }

    private boolean sumsAreNotEqualForDisjointSets(List<Integer> workingSet) {
        int maxMask = (int)Math.pow(2, workingSet.size()) - 1;
        for(int mask1 = 1; mask1 <= maxMask; mask1++) {
            int sum1 = getSum(workingSet, mask1);
            for(int mask2 = mask1 + 1; mask2 <= maxMask; mask2++) {
                if(isDisjoint(mask1, mask2)) {
                    int sum2 = getSum(workingSet, mask2);
                    if(sum1 == sum2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isDisjoint(int mask1, int mask2) {
        return (mask1 & mask2) == 0;
    }

    private int getSum(List<Integer> workingSum, int bitMask) {
        int sum = 0, pos = 0;
        while (bitMask > 0) {
            if((bitMask & 1) == 1) {
                sum += workingSum.get(pos);
            }
            pos++;
            bitMask = bitMask >> 1;
        }
        return sum;
    }

    private static int sum(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }

}
