package com.smart.tkl.euler.p90;

import com.smart.tkl.lib.combinatorics.CombinatoricsUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CubeDigitPairs2 {

    private final int cubes;
    private final int squareCount;

    public CubeDigitPairs2(int cubes, int squareCount) {
        this.cubes = cubes;
        this.squareCount = squareCount;
    }

    public static void main(String[] args) {
        int cubes = 3;
        int squares = 31;
        long time1 = System.currentTimeMillis();
        CubeDigitPairs2 cubeDigitPairs2 = new CubeDigitPairs2(cubes, squares);
        long count = cubeDigitPairs2.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        if(cubes == 1) {
            if(squareCount == 3) {
                long count = CombinatoricsUtils.countCombinations(7, 3);
                long intersection = CombinatoricsUtils.countCombinations(6, 2);
                return count + count - intersection;
            }
            else if(squareCount == 2) {
                return CombinatoricsUtils.countCombinations(8, 4);
            }
            else if(squareCount == 1) {
                return CombinatoricsUtils.countCombinations(9, 5);
            }
        }
        else {
            Set<Integer> input = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
            List<Digits> combinations = new ArrayList<>(CombinatoricsUtils.combinations(input, 6))
                    .stream().map(CubeDigitPairs2::toDigits).collect(Collectors.toList());
            List<int[]> squares = getSquares();

            int count = 0;

            if(cubes == 2) {
               List<int[]> permutations = generatePermutations(2);
               for(int i = 0; i < combinations.size(); i++) {
                   for(int j = i; j < combinations.size(); j++) {
                       boolean validArrangement = true;
                       for(int[] square : squares) {
                           if(!isDoubleArrangement(combinations.get(i), combinations.get(j), square, permutations)) {
                              validArrangement = false;
                              break;
                           }
                       }
                       if(validArrangement) {
                         count++;
                       }
                   }
               }
            }
            else if(cubes == 3) {
                List<int[]> permutations = generatePermutations(3);
                for(int i = 0; i < combinations.size(); i++) {
                    Digits combination1 = combinations.get(i);
                    for(int j = i; j < combinations.size(); j++) {
                        Digits combination2 = combinations.get(j);
                        for(int k = j; k < combinations.size(); k++) {
                            Digits combination3 = combinations.get(k);
                            boolean validArrangement = true;
                            for(int[] square : squares) {
                                if(!isTripleArrangement(combination1, combination2, combination3, square, permutations)) {
                                    validArrangement = false;
                                    break;
                                }
                            }
                            if(validArrangement) {
                                count++;
                            }
                        }
                    }
                }
            }



            return count;

        }
        return 0;
    }

    private List<int[]> getSquares() {
        List<int[]> squares = new ArrayList<>(squareCount);
        for(int i = 1; i <= squareCount; i++) {
            int square = i * i;
            int[] squareDigits = new int[cubes];
            int pos = squareDigits.length - 1;
            while (square > 0) {
                int digit = square % 10;
                squareDigits[pos] = digit;
                pos--;
                square = square / 10;
            }
            squares.add(squareDigits);
        }
        return squares;
    }

    private static boolean isDoubleArrangement(Digits digits1, Digits digits2, int[] square, List<int[]> permutations) {
        for(int[] permutation : permutations) {
            if(digits1.hasDigit(square[permutation[0]]) &&
                    digits2.hasDigit(square[permutation[1]])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTripleArrangement(Digits digits1, Digits digits2, Digits digits3, int[] square, List<int[]> permutations) {
        for(int[] permutation : permutations) {
            if(digits1.hasDigit(square[permutation[0]]) &&
                    digits2.hasDigit(square[permutation[1]]) &&
                    digits3.hasDigit(square[permutation[2]])) {
                return true;
            }
        }
        return false;
    }

    private static Digits toDigits(int[] combination) {
        int[] digitFreq = new int[10];
        for(int digit : combination) {
            digitFreq[digit]++;
            if(digit == 6) {
               digitFreq[9] = 1;
            }
            else if(digit == 9) {
               digitFreq[6] = 1;
            }
        }
        return new Digits(digitFreq);
    }

    private static List<int[]> generatePermutations(int k) {
        List<int[]> permutations = new ArrayList<>();
        boolean[] used = new boolean[k];
        generatePermutations(new int[k], 0, k - 1, used, permutations);
        return permutations;
    }

    private static void generatePermutations(int[] permutation, int pos, int max, boolean[] used, List<int[]> permutations) {
        if(pos == permutation.length - 1) {
            for (int i = 0; i <= max; i++) {
                if (!used[i]) {
                    permutation[pos] = i;
                    permutations.add(permutation.clone());
                }
            }
        }
        else {
            for (int i = 0; i <= max; i++) {
                if (!used[i]) {
                    used[i] = true;
                    permutation[pos] = i;
                    generatePermutations(permutation, pos + 1, max, used, permutations);
                    used[i] = false;
                }
            }
        }
    }

    private static class Digits {

        int[] digitFreq;

        public Digits(int[] digitFreq) {
            this.digitFreq = digitFreq;
        }

        boolean hasDigit(int digit) {
            return digitFreq[digit] > 0;
        }
    }
}
