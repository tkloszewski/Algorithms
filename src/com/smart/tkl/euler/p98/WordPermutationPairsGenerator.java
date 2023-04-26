package com.smart.tkl.euler.p98;

import com.smart.tkl.lib.utils.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordPermutationPairsGenerator {
    
    private final Map<Integer, List<String>> wordMap;
    private int maxLength = 0;
    private int currentLength = 0;
    
    public WordPermutationPairsGenerator(List<String> words) {
        this.wordMap = toWordMap(words);
    }
    
    public List<WordPermutationPair> generateNext() {
        List<WordPermutationPair> result = new ArrayList<>();
        
        while (!wordMap.containsKey(currentLength) && currentLength > 0) {
            currentLength--;
        }
        
        if(!wordMap.containsKey(currentLength)) {
           return result; 
        }
        
        List<String> words = wordMap.get(currentLength);
        for(int i = 0; i < words.size(); i++) {
            for(int j = i + 1; j < words.size(); j++) {
                String word1 = words.get(i);
                String word2 = words.get(j);
                Map<Integer, Integer> freqMap = WordUtils.toFrequencyMap(word1);
                if(WordUtils.isAnagram(freqMap, word2)) {
                   result.add(new WordPermutationPair(word1, word2, freqMap)); 
                }
            }
        }
        
        currentLength--;
        
        return result;
    }
    
    public boolean hasNext() {
        return currentLength > 0;
    }
    
    public int getCurrentLength() {
        return currentLength;
    }
    
    private Map<Integer, List<String>> toWordMap(List<String> words) {
         Map<Integer, List<String>> result = new HashMap<>();
         for(String word : words) {
             int key = word.length();
             if(result.containsKey(key)) {
                result.get(key).add(word); 
             }
             else {
                List<String> list = new ArrayList<>();
                list.add(word);
                result.put(key, list); 
             }
             if(maxLength < key) {
                maxLength = key; 
             }
         }
         currentLength = maxLength;
         return result;
    }
    
}
