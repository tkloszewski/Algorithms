package com.smart.tkl.euler.p93;

import java.util.Arrays;
import java.util.Optional;

public abstract class BaseExpressionEvaluator implements ExpressionEvaluator {
    
    protected final double[] values;
    protected final int[] operators;
    
    public BaseExpressionEvaluator(double[] values, int[] operators) {
        this.values = values;
        this.operators = operators;
    }   
    
    protected Optional<Integer> getValidValue(Double val) {
        if(val == null) {
           return Optional.empty(); 
        }
        double rawValue = val;
        return rawValue > 0 && rawValue == (int)rawValue ? Optional.of((int)rawValue) : Optional.empty();
    } 
    
    protected boolean allOperatorsEquals(int oper) {
        return Arrays.stream(this.operators).allMatch(i -> i == oper);
    }
    
    protected static char[] toCharTable(int[] t) {
        char[] chars = new char[t.length];
        for(int i = 0; i < t.length; i++) {
            chars[i] = (char)t[i];
        }
        return chars;
    }
}
