package com.smart.tkl.euler.p93;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SingleParenthesesExpressionEvaluator extends BaseParenthesesExpressionEvaluator {     
    
    public SingleParenthesesExpressionEvaluator(double[] values, int[] signs, int size) {
        super(values, signs, size);
    }

    public static void main(String[] args) {
        double[] permutation = new double[]{2, 4, 6};
        int[] operators = new int[]{'*', '+'};
        SingleParenthesesExpressionEvaluator evaluator = new SingleParenthesesExpressionEvaluator(permutation, operators, 3);
        Set<Integer> evaluated = evaluator.evaluateValidValues();
        System.out.println(evaluated);
    }
    
    @Override    
    protected List<Integer> evaluateRawValues() {
        List<Integer> result = new ArrayList<>();
        
        double[] newValues = new double[values.length - this.size + 1];
        int[] newOperators = new int[operators.length - this.size + 1];
        
        for(int pos = 0; pos <= values.length - this.size; pos++) {
            Optional<Double> valueOpt = getValidSubValue(pos);
            
            if(valueOpt.isEmpty()) {
                continue;
            }            
            
            fillNewValues(newValues, valueOpt.get(), pos);
            fillNewOperators(newOperators, pos);            
            
            Optional<Integer> validValueOpt = getValidValue(new NoParenthesesExpressionEvaluator(newValues, newOperators)
                    .evaluate()
                    .orElse(null));
            
            validValueOpt.ifPresent(result::add);
        }
        
        return result;
    }   
    
}
