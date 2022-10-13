package com.smart.tkl.euler.p93;

import com.smart.tkl.combinatorics.permutation.PermutationIterator;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ConsecutiveCombinationEvaluator {
    
    private final int[] combination;
    private final List<int[]> operatorVariations;
    
    private Set<Integer> validValues;    
    
    public ConsecutiveCombinationEvaluator(int[] combination, List<int[]> operatorVariations) {
        this.combination = combination;
        this.operatorVariations = operatorVariations;
    }
    
    public Set<Integer> getAllValidaValues() {
        Set<Integer> result = new TreeSet<>();        
        PermutationIterator permutationIterator = new PermutationIterator(combination);
        while (permutationIterator.hasNext()) {
            double[] permutation = toDouble(permutationIterator.next());
            for(int[] operatorVariation : operatorVariations) {
                List<ExpressionEvaluator> evaluators = getEvaluators(permutation, operatorVariation);
                for(ExpressionEvaluator evaluator : evaluators) {
                    Set<Integer> validValues = evaluator.evaluateValidValues();
                    if(validValues.size() > 0) {
                       result.addAll(validValues); 
                    }
                }
            }
        }
        
        return result;
    }
    
    public ConsecutiveCombination evaluate() {
        if(this.validValues == null) {
           this.validValues = getAllValidaValues(); 
        }
        int max = 0;
        while (validValues.contains(max + 1)) {
            max++;
        }        
        return new ConsecutiveCombination(combination, validValues, max);
    }
    
    public int[] getCombination() {
        return combination;
    }
    
    private List<ExpressionEvaluator> getEvaluators(double[] permutation, int[] operators) {
        return List.of(
                new NoParenthesesExpressionEvaluator(permutation, operators),
                new SingleParenthesesExpressionEvaluator(permutation, operators, 2),
                new SingleParenthesesExpressionEvaluator(permutation, operators, 3),
                new NestedParenthesesExpressionEvaluator(permutation, operators, 3, 2),
                new DoubleGroupParenthesisExpressionEvaluator(permutation, operators, 2));        
    }
    
    private static double[] toDouble(int[] permutation) {
        double[] result = new double[permutation.length];
        for(int i = 0; i < permutation.length; i++) {
            result[i] = permutation[i];
        }
        return result;
    }
    
}
