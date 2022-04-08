package com.smart.tkl.euler.p93;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoubleGroupParenthesisExpressionEvaluator extends BaseParenthesesExpressionEvaluator {   
    
    public DoubleGroupParenthesisExpressionEvaluator(double[] values, int[] operators, int size) {
        super(values, operators, size);
        assert values.length >= 2 * size;
    }
    
    @Override
    protected List<Integer> evaluateRawValues() {        
        List<Integer> result = new ArrayList<>();
    
        double[] newValues = new double[values.length - 2 * this.size + 2];
        int[] newOperators = new int[operators.length - 2 * this.size + 2];        
    
        for(int pos1 = 0; pos1 <= values.length - this.size; pos1++) {
            if(pos1 + this.size > values.length - this.size) {
               break; 
            }            
            
            Optional<Double> valueOpt = getValidSubValue(pos1);
        
            if(valueOpt.isEmpty()) {
                continue;
            }        
            
            int valuePos = fillNewValues(newValues, 0, valueOpt.get(), 0, pos1, pos1);
            int operatorPos = fillNewOperators(newOperators, 0, 0, pos1, pos1);           
            
            for(int pos2 = pos1 + this.size; pos2 <= values.length - this.size; pos2++) {
                Optional<Double> valueOpt2 = getValidSubValue(pos2);
    
                if(valueOpt2.isEmpty()) {
                    continue;
                }            
    
                fillNewValues(newValues, valuePos, valueOpt2.get(), pos1 + this.size, pos2, values.length);
                fillNewOperators(newOperators, operatorPos, pos1 + this.size - 1, pos2, operators.length);              
    
                Optional<Integer> validValueOpt = getValidValue(new NoParenthesesExpressionEvaluator(newValues, newOperators)
                        .evaluate()
                        .orElse(null));                
    
                validValueOpt.ifPresent(result::add);
            }
        }
    
        return result;
    }
    
    protected int fillNewOperators(int[] newOperators,int valuePos, int from, int pos, int to) {
        for(int i = from; i < pos; i++) {
            newOperators[valuePos++] = operators[i];
        }
        for(int i = pos + this.size - 1; i < to; i++) {
            newOperators[valuePos++] = operators[i];
        }
        return valuePos;
    }
    
    protected int fillNewValues(double[] newValues, int valuePos, double parenthesesValue, int from, int pos, int to) {
        for(int i = from; i < pos; i++) {
            newValues[valuePos++] = values[i];
        }
        newValues[valuePos++] = parenthesesValue;
        for(int i = pos + this.size; i < to; i++) {
            newValues[valuePos++] = values[i];
        }
        return valuePos;
    }
}
