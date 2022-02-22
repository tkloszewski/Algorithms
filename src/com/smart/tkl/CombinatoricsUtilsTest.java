package com.smart.tkl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CombinatoricsUtilsTest {

    public static void main(String[] args) {
        Set<Integer> input = generateSetFromRange(1, 9);
        Set<int[]> combinations = CombinatoricsUtils.combinations(input, 2);

        System.out.printf("Combinations size: %s%n", combinations.size());

        if(combinations.size() <= 1000){
           printCombinations(combinations);
        }
    }

    private static Set<Integer> generateSetFromRange(int from, int to) {
        Set<Integer> set = new HashSet<>();
        for(int i = from; i <= to; i++) {
            set.add(i);
        }
        return set;
    }

    private static void printCombinations(Set<int[]> combinations) {
        for (int[] combination : combinations) {
            System.out.println(Arrays.toString(combination));
        }
    }
}
