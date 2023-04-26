package com.smart.tkl.euler.p118;

import com.smart.tkl.lib.combinatorics.permutation.SortedPermutationIterator;
import com.smart.tkl.lib.utils.MathUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PanDigitalPrimeSets {

    private final boolean[] primesSieve;

    public PanDigitalPrimeSets() {
        primesSieve = MathUtils.primesSieve(100000000);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PanDigitalPrimeSets panDigitalPrimeSets = new PanDigitalPrimeSets();
        int totalCount = panDigitalPrimeSets.countAllPrimeSetsFromPermutations();
        long time2 = System.currentTimeMillis();
        System.out.println("Total count: " + totalCount);
        System.out.println("Solution in ms: " + (time2 - time1));
    }

    public int countAllPrimeSetsFromPermutations() {
        int totalCount = 0;
        List<List<Integer>> partitions = new ArrayList<>();
        for(int size = 2; size <= 6; size++) {
            partitions.addAll(PrimePanDigitalPartitions.getPartitions(size));
        }
        Set<Set<Integer>> allPrimeSets = new LinkedHashSet<>();

        int[] allDigits = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        SortedPermutationIterator permutationIterator = new SortedPermutationIterator(allDigits);
        while (permutationIterator.hasNext()) {
            int[] permutation = permutationIterator.next();
            for(List<Integer> partition : partitions) {
                int pos = 0;
                boolean allPartitionsArePrimes = true;
                Set<Integer> primesSet = new LinkedHashSet<>();
                for(int partitionSize : partition) {
                    int number = toNumber(permutation, pos, partitionSize);
                    if (!primesSieve[number]) {
                        allPartitionsArePrimes = false;
                        break;
                    }
                    primesSet.add(number);
                    pos += partitionSize;
                }
                if(allPartitionsArePrimes && !allPrimeSets.contains(primesSet)) {
                    allPrimeSets.add(primesSet);
                    totalCount++;
                }
            }
        }

        return totalCount;
    }

    private static Integer toNumber(int[] digits, int pos, int size) {
        int number = 0;
        int pow = 1;
        for(int i = pos + size - 1; i >= pos; i--) {
            number += pow * digits[i];
            pow *= 10;
        }
        return number;
    }
}
