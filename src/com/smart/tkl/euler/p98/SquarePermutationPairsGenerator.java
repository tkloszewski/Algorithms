package com.smart.tkl.euler.p98;

import com.smart.tkl.utils.MathUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SquarePermutationPairsGenerator {
    
    private long limit;
    
    public SquarePermutationPairsGenerator(long limit) {
        this.limit = limit;
    }
    
    public SquarePermutationPairsGenerator() {
    }    
    
    public List<NumberPermutationPair> generatePairs(int digitLength) {
        List<NumberPermutationPair> result = new ArrayList<>();
        
        long upperBound = (long)Math.pow(10, digitLength);
        long lowerBound = upperBound / 10;
        double sqrt = Math.sqrt(lowerBound);
        long base = (long)sqrt;
        if(sqrt != base) {
            base++;
        }
        
        List<Long> squares = new ArrayList<>();
        for(long square = base * base; square < upperBound; base++) {
            square = base * base;
            squares.add(square);
        }
        for(int i = 0; i < squares.size(); i++) {
            Long square1 = squares.get(i);
            for(int j = i + 1; j < squares.size(); j++) {
                Long square2 = squares.get(j);
                int[] digitFreqTab = MathUtils.getDigitFreqTab(square1);
                if(MathUtils.formDigitPermutations(digitFreqTab, square2)) {
                    NumberPermutationPair pair = new NumberPermutationPair(
                            square1,
                            square2,
                            MathUtils.getDigitsReversed(square1),
                            MathUtils.getDigitsReversed(square2),
                            digitFreqTab,
                            digitLength
                    );
                    result.add(pair);                    
                }              
            }
        }        
        return result;        
    }
    
    public Map<Integer, List<long[]>> generatePairs() {
        Map<Integer, List<long[]>> result = new LinkedHashMap<>();
        
        Map<Integer, List<Long>> squaresMap = new LinkedHashMap<>();
        for(long i = 1, square = 1; square <= limit; i++) {
            square = i * i;
            int digitLength = String.valueOf(square).length();
            if(squaresMap.containsKey(digitLength)) {
                squaresMap.get(digitLength).add(square);
            }
            else {
                List<Long> squares = new ArrayList<>();
                squares.add(square);
                squaresMap.put(digitLength, squares);
            }
        }
        
        for(Integer digitLength : squaresMap.keySet()) {
            List<Long> squares = squaresMap.get(digitLength);
            List<long[]> pairs = new LinkedList<>();
            for(int i = 0; i < squares.size(); i++) {
                Long square1 = squares.get(i);
                for(int j = i + 1; j < squares.size(); j++) {
                    Long square2 = squares.get(j);
                    if(MathUtils.formDigitPermutations(square1, square2)) {
                        List<Long> pair = new ArrayList<>(2);
                        pair.add(square1);
                        pair.add(square2);
                        pairs.add(new long[]{square1, square2});
                    }
                }
            }
            result.put(digitLength, pairs);
        }
        
        return result;
    }
    
}
