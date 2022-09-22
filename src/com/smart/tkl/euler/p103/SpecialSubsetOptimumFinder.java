package com.smart.tkl.euler.p103;

import java.util.ArrayList;
import java.util.List;

public class SpecialSubsetOptimumFinder {


    public static void main(String[] args) {
        List<Integer> nearOptimumSet = toNearOptimumNextSet(List.of(6, 9, 11, 12, 13));
        System.out.println("Near optimum set for n = 6: " + nearOptimumSet);
        SpecialOptimumSubsetSum specialOptimumSubsetSum = new SpecialOptimumSubsetSum(nearOptimumSet, 3);
        List<Integer> optimumSet = specialOptimumSubsetSum.find();
        System.out.println("Optimum set for n = 6: " + optimumSet);
        nearOptimumSet = toNearOptimumNextSet(optimumSet);
        System.out.println("Near optimum set for n = 7: " + nearOptimumSet);
        specialOptimumSubsetSum = new SpecialOptimumSubsetSum(nearOptimumSet, 3);
        long time1 = System.currentTimeMillis();
        optimumSet = specialOptimumSubsetSum.find();
        long time2 = System.currentTimeMillis();
        System.out.println("Optimum set for n = 7: " + optimumSet + " Solution took ms: " + (time2 - time1));
        System.out.println("Solution as string: " + joinList(optimumSet));

    }

    private static String joinList(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for(Integer element : list) {
            sb.append(element);
        }
        return sb.toString();
    }

    private static List<Integer> toNearOptimumNextSet(List<Integer> optimumSet) {
        List<Integer> result = new ArrayList<>(optimumSet.size() + 1);
        int middle = optimumSet.size() / 2;
        int middleElement = optimumSet.get(middle);

        result.add(middleElement);
        for(int optimumElement : optimumSet) {
            result.add(optimumElement + middleElement);
        }
        return result;
    }
}
