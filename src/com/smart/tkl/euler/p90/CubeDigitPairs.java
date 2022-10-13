package com.smart.tkl.euler.p90;

import com.smart.tkl.combinatorics.CombinatoricsUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CubeDigitPairs {
    
    public static void main(String[] args) {
        Set<Integer> input = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<int[]> combinations = new ArrayList<>(CombinatoricsUtils.combinations(input, 6));
        
        int count = 0;
        
        for(int i = 0; i < combinations.size(); i++) {
            for(int j = i + 1; j < combinations.size(); j++) {               
                Set<Integer> arrangement1  = toExtendedSet(combinations.get(i));
                Set<Integer> arrangement2  = toExtendedSet(combinations.get(j));              
              
                if(isValidArrangement(arrangement1, arrangement2)) {
                    count++;                    
                }               
            }
        }
        
        System.out.println("Count: " + count);
    }
    
    
    private static Set<Integer> toExtendedSet(int[] combination) {
        Set<Integer> arrangement  = new HashSet<>();
        for (int num : combination) {
            arrangement.add(num);
        }
        if(arrangement.contains(6) && !arrangement.contains(9)) {
            arrangement.add(9);
        }
        else if(arrangement.contains(9)) {
            arrangement.add(6);
        }
        return arrangement;
    }    
    
    
    private static boolean isValidArrangement(Set<Integer> arrangement1, Set<Integer> arrangement2) {   
        int[][] squareCombinations = {{0, 1}, {0, 4}, {0, 9}, {1, 6}, {2, 5}, {3, 6}, {4, 6}, {6, 4}, {8, 1}};
        
        for(int[] comb : squareCombinations) {
            boolean valid = (arrangement1.contains(comb[0]) && arrangement2.contains(comb[1])) ||
                    (arrangement1.contains(comb[1]) && arrangement2.contains(comb[0]));            
            if(!valid) {
               return false;               
            }
        }        
        return true;
    }
    
}
