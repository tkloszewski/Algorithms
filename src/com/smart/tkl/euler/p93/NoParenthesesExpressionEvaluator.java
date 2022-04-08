package com.smart.tkl.euler.p93;

import java.util.Optional;
import java.util.Set;

public class NoParenthesesExpressionEvaluator extends BaseExpressionEvaluator {
    
    public NoParenthesesExpressionEvaluator(double[] permutation, int[] signs) {
        super(permutation, signs);
    }
    
    public Set<Integer> evaluateValidValues() {
        Optional<Double> optValue = evaluate();
        if(optValue.isPresent()) {
            double value = optValue.get();
            if(value > 0 && value == (int)value) {
                return Set.of((int)value);
            }
        }
        return Set.of();
    }
    
    public Optional<Double> evaluate() {
        double value = values[0];
        
        for(int i = 0; i < operators.length; i++) {
            int sign = operators[i];
            if(values[i + 1] == 0 && sign == '/') {
                return Optional.empty();
            }
            if(i + 1 < operators.length && (sign == '+' || sign == '-') && (operators[i + 1] == '*' || operators[i + 1] == '/')) {
                int pos = i + 1;
                double accumulatedValue = values[pos];
                while (pos < operators.length && (operators[pos] == '*' || operators[pos] == '/')) {
                    if(values[pos + 1] == 0 && operators[pos] == '/') {
                        return Optional.empty();
                    }
                    accumulatedValue = ExpressionUtils.evaluate(accumulatedValue, values[pos + 1], operators[pos]);
                    pos++;
                }
                value = ExpressionUtils.evaluate(value, accumulatedValue, sign);
                i = pos;
            }
            else {
                value = ExpressionUtils.evaluate(value, values[i + 1], sign);
            }
        }
        return Optional.of(value);
    }
    
    protected String generateExpression() {
        StringBuilder sb = new StringBuilder();
        sb.append(values[0]);
        for(int i = 0; i < operators.length; i++) {
            sb.append(" ");
            sb.append((char) operators[i]);
            sb.append(" ");
            sb.append(values[i + 1]);
        }
        return sb.toString();
    }  
    
    
}
