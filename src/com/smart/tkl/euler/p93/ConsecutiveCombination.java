package com.smart.tkl.euler.p93;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class ConsecutiveCombination {
    
     private int[] originalCombination;
     private Set<Integer> validValues;
     private int maxValue;
    
    public ConsecutiveCombination(int[] originalCombination, Set<Integer> validValues, int maxValue) {
        this.originalCombination = originalCombination;
        this.validValues = validValues;
        this.maxValue = maxValue;
    }
    
    public int[] getOriginalCombination() {
        return originalCombination;
    }
    
    public void setOriginalCombination(int[] originalCombination) {
        this.originalCombination = originalCombination;
    }
    
    public Set<Integer> getValidValues() {
        return validValues;
    }
    
    public void setValidValues(Set<Integer> validValues) {
        this.validValues = validValues;
    }
    
    public int getMaxValue() {
        return maxValue;
    }
    
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
    
    public Set<Integer> getSortedCombination() {
        Set<Integer> result = new TreeSet<>();
        for(int value : originalCombination) {
            result.add(value);
        }
        return result;
    }
    
    @Override
    public String toString() {
        return "ConsecutiveCombination{" +
                "originalCombination=" + Arrays.toString(originalCombination) +
                ", validValues=" + validValues +
                ", maxValue=" + maxValue +
                '}';
    }
}
