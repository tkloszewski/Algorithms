package com.smart.tkl.euler.p103;

import java.util.*;

public class SpecialOptimumSubsetSum {

    private final List<Integer> nearOptimumSet;
    private final int maxElementDistance;

    private List<Integer> optimumSet;
    private int optimumSum;

    public SpecialOptimumSubsetSum(List<Integer> nearOptimumSet, int maxElementDistance) {
        this.nearOptimumSet = nearOptimumSet;
        this.maxElementDistance = maxElementDistance;
        this.optimumSet = new ArrayList<>(nearOptimumSet);
        this.optimumSum = sum(nearOptimumSet);
    }

    public List<Integer> find() {
        findAndSetOptimum(new ArrayList<>(nearOptimumSet), 0);
        return optimumSet;
    }

    private void findAndSetOptimum(List<Integer> workingSet, int currentPos) {
        int oldValue = workingSet.get(currentPos);
        int minValue = Math.max(1, oldValue - maxElementDistance);
        int maxValue = oldValue + maxElementDistance;
        boolean lastElement = currentPos == workingSet.size() - 1;
        for(int value = minValue; value <= maxValue; value++) {
            workingSet.set(currentPos, value);
            if(lastElement) {
               int workingSum = sum(workingSet);
               if(workingSum < optimumSum) {
                  List<Integer> newWorkingSet = new ArrayList<>(workingSet);
                  if(isSpecialSet(newWorkingSet)) {
                     optimumSet = newWorkingSet;
                     optimumSum = workingSum;
                  }
               }
            }
            else {
                findAndSetOptimum(workingSet, currentPos + 1);
            }
        }
        workingSet.set(currentPos, oldValue);
    }

    private boolean isSpecialSet(List<Integer> workingSet) {
        return areSumsOfDisjointSetNotEqual(workingSet) && areMoreNumerousSubsetsGreater(workingSet);
    }

    private boolean areSumsOfDisjointSetNotEqual(List<Integer> workingSet) {
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

    private Map<Integer, List<Integer>> createBitCountMaskMap(int setSize) {
        Map<Integer, List<Integer>> result = new LinkedHashMap<>();
        for(int i = 1; i <= setSize; i++) {
            result.put(i, new ArrayList<>());
        }

        int maxMask = (int)Math.pow(2, setSize) - 1;
        for(int mask = 1; mask <= maxMask; mask++) {
            int bitsCount = countBits(mask);
            result.get(bitsCount).add(mask);
        }
        return result;
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

    private int countBits(int mask) {
        int result = 0;
        while (mask > 0) {
            if((mask & 1) == 1) {
               result++;
            }
            mask = mask >> 1;
        }
        return result;
    }

    /*
        a1 + a2 > an
        a1 + a2 + a3 > an + a(n-1)
        ....
        a1 + a2 + a(n/2 + 1) > an + a(n-1) + a(n/2 + 2)
        * */
    private boolean areMoreNumerousSubsetsGreater(List<Integer> workingSet) {
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

    private int sum(List<Integer> list) {
        return list.stream().reduce(0, Integer::sum);
    }
}
