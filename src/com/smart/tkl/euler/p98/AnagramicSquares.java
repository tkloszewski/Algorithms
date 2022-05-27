package com.smart.tkl.euler.p98;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class AnagramicSquares {
    
    private static final String PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p98\\p098_words.txt";
    
    private final WordReader wordReader;
    private final WordPermutationPairsGenerator wordPermutationGenerator;
    private final SquarePermutationPairsGenerator squarePermutationGenerator;
    
    public static void main(String[] args) {
        try {
            AnagramicSquares anagramicSquares = new AnagramicSquares();
            long time1 = System.currentTimeMillis();
            long maxSquare = anagramicSquares.findMaxSquare();
            long time2 = System.currentTimeMillis();            
            
            System.out.println("Max square: " + maxSquare + " Time in ms: " + (time2 - time1));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public AnagramicSquares() throws Exception {
        this.wordReader = new WordReader(PATH);
        this.wordPermutationGenerator = new WordPermutationPairsGenerator(wordReader.readWords());
        this.squarePermutationGenerator = new SquarePermutationPairsGenerator();
    }
    
    public long findMaxSquare() {
        Optional<Long> maxSquareOpt = Optional.empty();
        while (maxSquareOpt.isEmpty()) {
            List<WordPermutationPair> wordPermutationPairs = new ArrayList<>();
            int currentLength = 0;
            while (wordPermutationPairs.size() == 0) {
                currentLength = wordPermutationGenerator.getCurrentLength();
                if(!wordPermutationGenerator.hasNext()) {
                    break;
                }
                wordPermutationPairs = wordPermutationGenerator.generateNext();
            }
            if(!wordPermutationGenerator.hasNext()) {
                break;
            }
            maxSquareOpt = getMaxSquareOpt(currentLength, wordPermutationPairs);
        }
        
        return maxSquareOpt.orElse(0L);
    }
    
    private Optional<Long> getMaxSquareOpt(int length, List<WordPermutationPair> wordPermutationPairs) {
        long maxSquare = 0;
        
        Map<Long, List<HashedPermutation>> hashedPermutationMap = squarePermutationGenerator.generateHashedPermutation(length);        
        
        for(WordPermutationPair wordPair : wordPermutationPairs) {
            for(Long hash : hashedPermutationMap.keySet()) {
               Optional<Long> optMax = computeMaxSquare(hashedPermutationMap.get(hash), wordPair);
               maxSquare = Math.max(optMax.orElse(0L), maxSquare);
            }             
        }        
        
        return maxSquare != 0 ? Optional.of(maxSquare) : Optional.empty();
    }
    
    private Optional<Long> computeMaxSquare(List<HashedPermutation> hashedPermutations, WordPermutationPair wordPermutationPair) {        
        List<HashedPermutationWithWord> filteredPermutations1 = hashedPermutations
                .stream().map(p -> {
                    String word = wordPermutationPair.pair[0];
                    Optional<Map<Integer, Integer>> wordToDigitMapOpt = getWordToDigitMapping(word, p.digits);
                    return wordToDigitMapOpt.map(
                            integerIntegerMap -> new HashedPermutationWithWord(p, word, integerIntegerMap));
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        
        List<HashedPermutationWithWord> filteredPermutations2 = hashedPermutations
                .stream().map(p -> {
                    String word = wordPermutationPair.pair[1];
                    Optional<Map<Integer, Integer>> wordToDigitMapOpt = getWordToDigitMapping(word, p.digits);
                    return wordToDigitMapOpt.map(
                            integerIntegerMap -> new HashedPermutationWithWord(p, word, integerIntegerMap));
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        
        long maxSquare = 0;
        for(HashedPermutationWithWord hw1 : filteredPermutations1) {
            for(HashedPermutationWithWord hw2 : filteredPermutations2) {
                if(hw1.value != hw2.value) {
                    long localMax = Math.max(hw1.value, hw2.value);
                    if(maxSquare < localMax && matchesWordToDigitMap(hw1.wordToDigitMap, hw2.word, hw2.digits)) {
                       maxSquare = localMax; 
                    }
                }
            }
        }        
        
        return maxSquare > 0 ? Optional.of(maxSquare) : Optional.empty();
    }   
    
    private static boolean matchesWordToDigitMap(Map<Integer, Integer> map, String word, List<Integer> digits) {
        for(int i = 0; i < word.length(); i++) {
            int key = word.charAt(i);
            int digit = digits.get(i);
            if(!map.containsKey(key) || map.get(key) != digit) {
                return false;
            }
        }
        return true;
    }
    
    private static Optional<Map<Integer, Integer>> getWordToDigitMapping(String word, List<Integer> digits) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        // Set<Integer> values
        for(int i = 0; i < word.length(); i++) {
            int key = word.charAt(i);
            int digit = digits.get(i);
            if (!map.containsKey(key)) {
                if (!map.containsValue(digit)) {
                    map.put(key, digit);
                }
                else {
                    return Optional.empty();
                }
            }
            else if(map.get(key) != digit){
                return Optional.empty();
            }
        }
        return Optional.of(map);
    }
    
    private static class HashedPermutationWithWord {
        
        long value;
        long hash;
        List<Integer> digits;
        String word;
        Map<Integer, Integer> wordToDigitMap;
        
        public HashedPermutationWithWord(HashedPermutation hashedPermutation, String word, Map<Integer, Integer> wordToDigitMap) {
            this.value = hashedPermutation.value;
            this.hash = hashedPermutation.hash;
            this.digits = hashedPermutation.digits;
            this.word = word;
            this.wordToDigitMap = wordToDigitMap;
        }
        
        
    }
}
