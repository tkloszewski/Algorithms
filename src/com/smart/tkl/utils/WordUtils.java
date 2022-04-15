package com.smart.tkl.utils;

import java.util.HashMap;
import java.util.Map;

public class WordUtils {
    
    public static boolean isAnagram(String word1, String word2) {
        if(word1.length() != word2.length()) {
            return false;
        }
        Map<Integer, Integer> freqMap1 = toFrequencyMap(word1);
        Map<Integer, Integer> freqMap2 = toFrequencyMap(word2);
        
        return freqMap1.equals(freqMap2);
    }
    
    public static boolean isAnagram(Map<Integer, Integer> freqMap1, String word2) {        
        Map<Integer, Integer> freqMap2 = toFrequencyMap(word2);        
        return freqMap1.equals(freqMap2);
    }
    
    public static Map<Integer, Integer> toFrequencyMap(String word) {
        Map<Integer, Integer> result = new HashMap<>();
        for(char ch : word.toCharArray()) {
            if(!result.containsKey((int)ch)) {
               result.put((int)ch, 1); 
            }
            else {
                result.put((int)ch, result.get((int)ch) + 1);
            }
        }
        return result;
    }
    
}
