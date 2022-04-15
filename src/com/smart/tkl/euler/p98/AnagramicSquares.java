package com.smart.tkl.euler.p98;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnagramicSquares {
    
    private static final String PATH = "C:\\Projects\\microservices\\Algorithms\\src\\com\\smart\\tkl\\euler\\p98\\p098_words.txt";
    
    private final WordReader wordReader;
    private final WordPermutationPairsGenerator wordPermutationGenerator;
    private final SquarePermutationPairsGenerator squarePermutationGenerator;    
    
    public static void main(String[] args) {
        try {
            AnagramicSquares anagramicSquares = new AnagramicSquares();
            long maxSquare = anagramicSquares.findMaxSquare();
            System.out.println("Max square: " + maxSquare);
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
        List<NumberPermutationPair> squarePermutationPairs = squarePermutationGenerator.generatePairs(length);
        for(WordPermutationPair wordPair : wordPermutationPairs) {
            for(NumberPermutationPair squarePair : squarePermutationPairs) {
                if(matches(squarePair, wordPair)) {
                   if(maxSquare < squarePair.max) {
                      maxSquare = squarePair.max; 
                   }
                }
            }
        }
        return maxSquare != 0 ? Optional.of(maxSquare) : Optional.empty();
    }
    
    private static boolean matches(NumberPermutationPair squarePair, WordPermutationPair wordPair) {
        if(squarePair.length != wordPair.length) {
           return false; 
        }
        
        boolean foundMatch = false;
        
        Optional<Map<Integer, Integer>> wordToDigitMapOpt = getWordToDigitMapping(wordPair.pair[0], squarePair.digitsPair[0]);
        if(wordToDigitMapOpt.isEmpty()) {
           wordToDigitMapOpt = getWordToDigitMapping(wordPair.pair[0], squarePair.digitsPair[1]);
           if(wordToDigitMapOpt.isPresent()) {
              foundMatch = matchesWordToDigitMap(wordToDigitMapOpt.get(), wordPair.pair[1], squarePair.digitsPair[0]); 
           }
        }
        else {
            foundMatch = matchesWordToDigitMap(wordToDigitMapOpt.get(), wordPair.pair[1], squarePair.digitsPair[1]);
        }
        
        return foundMatch;
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
}
