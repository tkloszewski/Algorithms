package com.smart.tkl.euler.p93;

import com.smart.tkl.CombinatoricsUtils;
import com.smart.tkl.VariationsUtils;

import java.util.List;
import java.util.Set;

public class ArithmeticExpressions {
    
    private final int combinationSize;
    
    public ArithmeticExpressions(int combinationSize) {
        this.combinationSize = combinationSize;
    }
    
    public static void main(String[] args) {
        ArithmeticExpressions arithmeticExpressions = new ArithmeticExpressions(4);
        ConsecutiveCombination consecutiveCombination = arithmeticExpressions.solve();
        System.out.println("Found combination: " + consecutiveCombination);
        System.out.println("Sorted form: " + consecutiveCombination.getSortedCombination());
    }
    
    public ConsecutiveCombination solve() {
        List<int[]> variations = VariationsUtils.generateVariationWithRepetition(new int[]{'+', '-' , '*', '/'}, 3);
        Set<int[]> combinations = CombinatoricsUtils.combinations(Set.of(1, 2, 3, 4, 5, 6 ,7, 8, 9), this.combinationSize);
        
        ConsecutiveCombination longestConsecutiveCombination = null;
        for (int[] combination : combinations) {
            ConsecutiveCombinationEvaluator evaluator = new ConsecutiveCombinationEvaluator(combination, variations);
            ConsecutiveCombination evaluated = evaluator.evaluate();
            if(longestConsecutiveCombination == null || evaluated.getMaxValue() > longestConsecutiveCombination.getMaxValue()) {
                longestConsecutiveCombination = evaluated;
            }
        }        
        
        return longestConsecutiveCombination;
    }
}
