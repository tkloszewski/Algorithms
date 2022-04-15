package com.smart.tkl.euler.p98;

import java.util.Arrays;
import java.util.Map;

public class WordPermutationPair {    
    
    String[] pair;
    Map<Integer, Integer> freqMap;
    int length;
    
    public WordPermutationPair(String word1, String word2, Map<Integer, Integer> freqMap) {
        assert word1.length() == word2.length();
        pair = new String[]{word1, word2};
        this.freqMap = freqMap;
        this.length = word1.length();
    }
    
    @Override
    public String toString() {
        return "WordPermutationPair{" +
                "pair=" + Arrays.toString(pair) +
                ", freqMap=" + freqMap +
                ", length=" + length +
                '}';
    }
}
