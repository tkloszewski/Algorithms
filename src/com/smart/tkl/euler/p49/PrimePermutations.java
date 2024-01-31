package com.smart.tkl.euler.p49;

import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PrimePermutations {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        List<PermutationSequence> sequences = getAllSequences(1000000, 3);
        long time2 = System.currentTimeMillis();
        for(PermutationSequence sequence : sequences) {
            System.out.println(sequence.asString());
        }
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static List<PermutationSequence> getAllSequences(int limit, int sequenceLength) {
        List<PermutationSequence> result = new ArrayList<>();

        PrimesSieve primesSieve = new PrimesSieve(1000000);
        boolean[] checked = new boolean[1000000];

        for(int n = 1487, step = 4; n < limit; n += step) {
            if(checked[n]) {
               continue;
            }
            if(primesSieve.isPrime(n)) {
               Set<Integer> permutations = generatePrimePermutations(n, primesSieve, checked);
               if(permutations.size() >= sequenceLength) {
                   List<PermutationSequence> sequences = getSequences(permutations, sequenceLength, limit);
                   if(!sequences.isEmpty()) {
                       result.addAll(sequences);
                   }
               }
            }

            step = step == 4 ? 2 : 4;
        }

        Collections.sort(result);

        return result;
    }

    private static List<PermutationSequence> getSequences(Set<Integer> permutations, int sequenceLength, int limit) {
        List<PermutationSequence> result = new ArrayList<>();
        List<Integer> permutationList = new ArrayList<>(permutations);

        int lastPermutation = permutationList.get(permutationList.size() - 1);

        for(int i = 0; i < permutationList.size(); i++) {
            int prime1 = permutationList.get(i);
            if(prime1 >= limit) {
               break;
            }
            for(int j = i + 1; j < permutationList.size(); j++) {
                int prime2 = permutationList.get(j);
                int diff = prime2 - prime1;
                List<Integer> sequence = new ArrayList<>();
                sequence.add(prime1);
                sequence.add(prime2);
                for(int k = 2; k <= sequenceLength - 1; k++) {
                    int candidate = prime1 + k * diff;
                    if(candidate > lastPermutation || !permutations.contains(candidate)) {
                        break;
                    }
                    sequence.add(candidate);
                }
                if(sequence.size() == sequenceLength) {
                   result.add(new PermutationSequence(sequence));
                }
            }
        }

        return result;
    }

    private static Set<Integer> generatePrimePermutations(long prime, PrimesSieve primesSieve, boolean[] checked) {
         Set<Integer> permutations = new TreeSet<>();
         List<Integer> digits = getDigits(prime);
         Collections.sort(digits);
         Set<Integer> positions = new TreeSet<>();
         for(int i = 0; i < digits.size(); i++) {
             positions.add(i);
         }
         addPrimePermutation(digits, positions, 0, primesSieve, checked, permutations);
         return permutations;
    }

    private static void addPrimePermutation(List<Integer> digits, Set<Integer> positionsLeft, int value, PrimesSieve primesSieve, boolean[] checked, Set<Integer> permutations) {
        if(positionsLeft.size() == 1) {
           int lastPosition = positionsLeft.iterator().next();
           int lastDigit = digits.get(lastPosition);
           int permutation = value * 10 + lastDigit;
           if(primesSieve.isPrime(permutation)) {
              permutations.add(permutation);
              checked[permutation] = true;
           }
        }
        else {
            TreeSet<Integer> newPositions = new TreeSet<>(positionsLeft);
            for(int position : positionsLeft) {
                int digit = digits.get(position);
                if((digit == 0 && digits.size() == positionsLeft.size())) {
                   continue;
                }
                int newValue = value * 10 + digit;
                newPositions.remove(position);
                addPrimePermutation(digits, newPositions, newValue, primesSieve, checked, permutations);
                newPositions.add(position);
            }
        }
    }

    private static List<Integer> getDigits(long n) {
        List<Integer> digits = new ArrayList<>();
        while (n > 0) {
            digits.add((int)(n % 10));
            n = n / 10;
        }
        return digits;
    }

    private static class PermutationSequence implements Comparable<PermutationSequence>{
         List<Integer> values;

        public PermutationSequence(List<Integer> values) {
            this.values = values;
        }

        @Override
        public int compareTo(PermutationSequence o) {
            return this.values.get(0).compareTo(o.values.get(0));
        }

        @Override
        public String toString() {
            return "{" +
                    "seq=" + values +
                    '}';
        }

        public String asString() {
            StringBuilder sb = new StringBuilder();
            for(int value : values) {
                sb.append(value);
            }
            return sb.toString();
        }
    }
}
