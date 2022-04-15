package com.smart.tkl.euler.p98;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SquarePermutationPairsGenerator {
    
    private static final long R = 1779033703;    
    
    private long limit;
    
    public SquarePermutationPairsGenerator(long limit) {
        this.limit = limit;
    }
    
    public SquarePermutationPairsGenerator() { 
        
    }      
    
    public Map<Long, List<HashedPermutation>> generateHashedPermutation(int digitLength) {
        Map<Long, List<HashedPermutation>> result = new HashMap<>();
    
        long upperBound = (long)Math.pow(10, digitLength);
        long lowerBound = upperBound / 10;
        double sqrt = Math.sqrt(lowerBound);
        long base = (long)sqrt;
        if(sqrt != base) {
            base++;
        }
    
        for(long square = base * base; square < upperBound; base++) {
            square = base * base;
            HashedPermutation hashedPermutation = toHashPermutation(square);
            if(result.containsKey(hashedPermutation.hash)) {
                result.get(hashedPermutation.hash).add(hashedPermutation);
            }
            else {
                List<HashedPermutation> permutations = new ArrayList<>();
                permutations.add(hashedPermutation);
                result.put(hashedPermutation.hash, permutations);
            }
        }
        
        return result;
    }
    
    private HashedPermutation toHashPermutation(long square) {
        long hash = 1, n = square;
        LinkedList<Integer> digits = new LinkedList<>();
        while (n > 0) {
            long digit = n % 10;
            digits.addFirst((int)digit);
            hash *= (R + 2 * digit);
            n = n / 10;            
        }
        return new HashedPermutation(square, hash, digits);
    }
    
}
