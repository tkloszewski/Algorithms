package com.smart.tkl.euler.p93;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseParenthesesExpressionEvaluator extends BaseExpressionEvaluator {
    
    protected final int size;
    
    public BaseParenthesesExpressionEvaluator(double[] values, int[] operators, int size) {
        super(values, operators);
        this.size = size;
    }
    
    public Set<Integer> evaluateValidValues() {
        if(allOperatorsEquals('+') || allOperatorsEquals('*')) {
            return new NoParenthesesExpressionEvaluator(this.values, this.operators).evaluateValidValues();
        }
        return new TreeSet<>(evaluateRawValues());
    }
    
    protected abstract List<Integer> evaluateRawValues();
    
    protected void fillNewValues(double[] newValues, double parenthesesValue, int pos) {        
        fillNewValues(newValues, parenthesesValue, 0, pos, values.length);
    }
    
    protected void fillNewValues(double[] newValues, double parenthesesValue, int from, int pos, int to) {
        int valuePos = 0;
        for(int i = from; i < pos; i++) {
            newValues[valuePos++] = values[i];
        }
        newValues[valuePos++] = parenthesesValue;
        for(int i = pos + this.size; i < to; i++) {
            newValues[valuePos++] = values[i];
        }
    }
    
    protected void fillNewOperators(int[] newOperators, int pos) {        
        fillNewOperators(newOperators, 0, pos, operators.length);
    }
    
    protected void fillNewOperators(int[] newOperators, int from, int pos, int to) {
        int valuePos = 0;
        for(int i = from; i < pos; i++) {
            newOperators[valuePos++] = operators[i];
        }
        for(int i = pos + this.size - 1; i < to; i++) {
            newOperators[valuePos++] = operators[i];
        }
    }
    
    protected Optional<Double> getValidSubValue(int pos) {
        double[] subValues = getSubValues(pos);
        int[] subOperators = getSubOperators(pos);
        return new NoParenthesesExpressionEvaluator(subValues, subOperators).evaluate();
    }
    
    protected double[] getSubValues(int pos) {
        return Arrays.copyOfRange(this.values, pos, pos + this.size);
    }
    
    protected int[] getSubOperators(int pos) {
        return Arrays.copyOfRange(this.operators, pos, pos + this.size - 1);
    }
    
}
