package com.smart.tkl.euler.p62;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CubicPermutation {

    private static final long R = 1779033703;

    private final int permutationsCount;
    private final Map<Long, LinkedList<Long>> permutationMap = new LinkedHashMap<>();
    private Long smallestCube;

    public static void main(String[] args) {
        System.out.println("Smallest cube for 3 permutations: " + new CubicPermutation(3).getOrFindSmallestCube());
        long time1 = System.currentTimeMillis();
        Long smallestPermutation = new CubicPermutation(5).getOrFindSmallestCube();
        long time2 = System.currentTimeMillis();
        System.out.println("Smallest cube for 5 permutations: " + smallestPermutation + ". Time in ms: " + (time2 - time1));


        time1 = System.currentTimeMillis();
        smallestPermutation = new CubicPermutation(6).getOrFindSmallestCube();
        time2 = System.currentTimeMillis();
        System.out.println("Smallest cube for 6 permutations: " + smallestPermutation + ". Time in ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        smallestPermutation = new CubicPermutation(49).getOrFindSmallestCube();
        time2 = System.currentTimeMillis();
        System.out.println("Smallest cube for 10 permutations: " + smallestPermutation + ". Time in ms: " + (time2 - time1));


        time1 = System.currentTimeMillis();
        List<Long> result = new CubicPermutation(49).getOrFindSmallestCube(1000000);
        time2 = System.currentTimeMillis();
        System.out.println("Smallest cube for 10 permutations: " + result + ". Time in ms: " + (time2 - time1));

    }

    public CubicPermutation(int permutationsCount) {
        this.permutationsCount = permutationsCount;
    }

    public Long getOrFindSmallestCube() {
        if(this.smallestCube != null) {
            return this.smallestCube;
        }
        long i = 101;
        HashResult lastFoundHash = null;
        List<Long> smallestCubeCandidates = new ArrayList<>(10);

        while (true) {
            long cube = i * i * i;
            HashResult hashResult = toHashValue(cube);
            if(lastFoundHash != null && lastFoundHash.digitsCount < hashResult.digitsCount) {
               break;
            }

            LinkedList<Long> samePermutationCubes = permutationMap.computeIfAbsent(hashResult.hash, k -> new LinkedList<>());
            samePermutationCubes.add(i);
            if(samePermutationCubes.size() == this.permutationsCount) {
                smallestCubeCandidates.add(samePermutationCubes.getFirst());
                if(lastFoundHash == null) {
                    lastFoundHash = hashResult;
                }
            }
            else if(samePermutationCubes.size() > this.permutationsCount) {
              //  smallestCubeCandidates.remove(samePermutationCubes.get(0));
            }
            i++;
        }

        if(smallestCubeCandidates.size() > 0) {
           this.smallestCube = (long)Math.pow(smallestCubeCandidates.get(0), 3);
        }
        return this.smallestCube;
    }

    public List<Long> getOrFindSmallestCube(int limit) {
        long i = 101;

        while (i < limit) {
            long cube = i * i * i;
            HashResult hashResult = toHashValue(cube);
            LinkedList<Long> samePermutationCubes = permutationMap.computeIfAbsent(hashResult.hash, k -> new LinkedList<>());
            samePermutationCubes.add(i);
            i++;
        }

        List<Long> result = new ArrayList<>();
        for(long key : permutationMap.keySet()) {
            LinkedList<Long> values = permutationMap.get(key);
            if(values.size() == permutationsCount) {
                long first = values.getFirst();
                result.add(first * first * first);
            }
        }

        Collections.sort(result);

        return result;
    }

    private HashResult toHashValue(long cube) {
        long result = 1, n = cube;
        int digitsCount = 0;
        while (n > 0) {
            long digit = n % 10;
            result *= (R + 2 * digit);
            n = n / 10;
            digitsCount++;
        }
        return new HashResult(digitsCount, result);
    }

    private static class HashResult {
        int digitsCount;
        long hash;

        public HashResult(int digitsCount, long hash) {
            this.digitsCount = digitsCount;
            this.hash = hash;
        }
    }
}
