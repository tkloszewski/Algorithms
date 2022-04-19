package com.smart.tkl.euler.p95;

import com.smart.tkl.utils.MathUtils;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AmicableChains {
    
    private final int limit;
    private final int[] sieve;
    private final Map<Integer, List<Integer>> chainMap = new LinkedHashMap<>();
    private final boolean cacheChains;
    
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        AmicableChains amicableChains = new AmicableChains(1000000);
        int min = amicableChains.getMinElementOfLongestChain();
        long time2 = System.currentTimeMillis();
    
        System.out.println("Min element of longest chain: " + min + " Time in ms: " + (time2 - time1));
    }
    
    public AmicableChains(int limit) {
        this.limit = limit;
        this.cacheChains = true;    
        this.sieve = generateSieve(this.limit);       
    }
    
    public int getMinElementOfLongestChain() {
        List<Integer> longestChain = generateLongestChain();    
    
        int min = Integer.MAX_VALUE;
        for(int element : longestChain) {
            if(min > element) {
               min = element; 
            }
        }
        return min;
    }
     
    public List<Integer> generateLongestChain() {
        List<Integer> longestChain = null;
        for(int i = 1; i <= limit; i++) {
            Optional<List<Integer>> chainOpt = generateAmicableChain(i);
            if(chainOpt.isPresent() && (longestChain == null || longestChain.size() < chainOpt.get().size())) {
                longestChain = chainOpt.get();
            }
        }        
        
        return longestChain;
    }
    
    private Optional<List<Integer>> generateAmicableChain(int n) {
        if(cacheChains && chainMap.containsKey(n)) {
           return Optional.of(chainMap.get(n)); 
        }        
        
        Set<Integer> generatedElements = new LinkedHashSet<>();
        List<Integer> chain = new LinkedList<>();
        generatedElements.add(n);
        chain.add(n);
        
        int properDivisorSum = sieve[n];
        while (properDivisorSum <= limit && !generatedElements.contains(properDivisorSum)) {
            generatedElements.add(properDivisorSum);
            chain.add(properDivisorSum);
            properDivisorSum = sieve[properDivisorSum];
        }
        
        if(properDivisorSum > limit) {
           return Optional.empty(); 
        }
        
        //cache full length chain
        if (cacheChains) {
            if(properDivisorSum == n) {
               for(int i = 0; i < chain.size(); i++) {
                   List<Integer> newChain = new LinkedList<>();
                   for(int k = i, size = 0; size < chain.size(); k = (k + 1) % chain.size(), size++) {
                       newChain.add(chain.get(k));
                   }
                   chainMap.put(chain.get(i), newChain);
               }
            }
            else {
                //cache sub chains
                int foundIndex = chain.indexOf(properDivisorSum);
                List<Integer> baseSubChain = chain.subList(foundIndex, chain.size());
                for(int i = 0; i < baseSubChain.size(); i++) {
                    List<Integer> newChain = new LinkedList<>();
                    for(int k = i, size = 0; size < baseSubChain.size(); k = (k + 1) % baseSubChain.size(), size++) {
                        newChain.add(baseSubChain.get(k));
                    }
                    chainMap.put(baseSubChain.get(i), newChain);
                }
            }
        }
    
        
        return properDivisorSum == n ? Optional.of(chain) : Optional.empty();
    }
    
    
    
    private static int[] generateSieve(int limit) {
        int[] sieve = new int[limit + 1];
        for(int i = 1; i <= limit / 2; i++) {
            for(int j = 2 * i; j <= limit; j += i) {
                sieve[j] += i;
                if(sieve[j] < 0) {
                   System.out.println("Sieve overflow!!!!"); 
                }
            }
        }
        return sieve;
    }
    
}
