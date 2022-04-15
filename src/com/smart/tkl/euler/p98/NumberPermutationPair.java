package com.smart.tkl.euler.p98;

import java.util.Arrays;
import java.util.List;

public class NumberPermutationPair {    
    
    long[] pair;
    long max;    
    List<Integer>[] digitsPair;    
    int[] freqTab;
    int length;   
    
    public NumberPermutationPair(long a, long b, List<Integer> digitsA, List<Integer> digitsB, int[] freqTab, int length) {
        this.pair = new long[] {a, b};        
        this.max = Math.max(a, b);
        this.digitsPair = new List[]{digitsA, digitsB};
        this.freqTab = freqTab;
        this.length = length;
    }
    
    @Override
    public String toString() {
        return "NumberPermutationPair{" +
                "pair=" + Arrays.toString(pair) +
                ", max=" + max +
                ", digitsPair=" + Arrays.toString(digitsPair) +
                ", freqTab=" + Arrays.toString(freqTab) +
                ", length=" + length +
                '}';
    }
}
