package com.smart.tkl.euler.p51;

import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PrimeDigitReplacement {

    private final int limit;
    private final int familySize;
    private final int replaceCount;
    private PrimesSieve primesSieve;
    private List<int[]>[] combinations;

    public PrimeDigitReplacement(int limit, int replaceCount, int familySize) {
        this.limit = limit;
        this.replaceCount = replaceCount;
        this.familySize = familySize;
        this.combinations = new List[limit - replaceCount + 1];
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PrimeDigitReplacement primeDigitReplacement = new PrimeDigitReplacement(5,2, 7);
        List<Integer> primeFamily = primeDigitReplacement.findFamily();
        long time2 = System.currentTimeMillis();
        System.out.println("Found family: " + primeFamily);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public List<Integer> findFamily() {
        int max = (int)Math.pow(10, limit) - 1;
        int min = (int)Math.pow(10, limit - 1) + 1;

        primesSieve = new PrimesSieve(max);

        int step = 1;

        if(familySize == 1) {
            for (int n = min; n <= max; n += step) {
                if(isPrime(n)) {
                   DigitData digitData = toDigitData(n);
                   for(int digit : digitData.digits) {
                       List<DigitPosition> digitPositions = digitData.digitPositions[digit];
                       if(digitPositions.size() >= replaceCount) {
                          return List.of(n);
                       }
                   }
                }
                step = getStep(n, step);
            }
        }
        else {
            for(int i = 0; i < combinations.length; i++) {
                combinations[i] = generateCombinations(replaceCount + i, replaceCount);
            }
            for (int n = min; n <= max; n += step) {
                if (isPrime(n)) {
                    List<Integer> family = replaceDigits(n);
                    if (!family.isEmpty()) {
                        return family;
                    }
                }
                step = getStep(n, step);
            }
        }

        return List.of();
    }

    private List<Integer> replaceDigits(int prime) {
        DigitData digitData = toDigitData(prime);
        List<List<DigitPosition>> filteredDigitPositions = new ArrayList<>();
        for(int digit : digitData.digits) {
            if(digit == 9) {
                continue;
            }
            List<DigitPosition> digitPositions = digitData.digitPositions[digit];
            if(digitPositions.size() >= replaceCount) {
                filteredDigitPositions.add(digitPositions);
            }
        }

        if(filteredDigitPositions.isEmpty()) {
            return List.of();
        }

        List<List<DigitPosition>> toBeReplacedPositions = new ArrayList<>();
        for(List<DigitPosition> digitPositions : filteredDigitPositions) {
            //List<int[]> combinations = generateCombinations(digitPositions.size(), replaceCount);
            List<int[]> combinations = this.combinations[digitPositions.size() - replaceCount];
            for(int[] combination : combinations) {
                List<DigitPosition> replaceDigitPositions = new ArrayList<>(replaceCount);
                for(int pos : combination) {
                    replaceDigitPositions.add(digitPositions.get(pos));
                }
                toBeReplacedPositions.add(replaceDigitPositions);
            }
        }

        sortDigitPositions(toBeReplacedPositions);

        for(List<DigitPosition> digitPositionList : toBeReplacedPositions) {
            int digit = digitPositionList.get(0).digit;
            if(9 - digit + 1 < familySize) {
                continue;
            }
            int toAddDiff = 0;
            for(DigitPosition digitPosition : digitPositionList) {
                toAddDiff += digitPosition.pow;
            }

            List<Integer> primeFamily = new ArrayList<>();
            primeFamily.add(prime);

            int candidate = prime;

            for(int replacedDigit = digit + 1; replacedDigit <= 9; replacedDigit++) {
                candidate += toAddDiff;
                if(isPrime(candidate)) {
                    primeFamily.add(candidate);
                    if(primeFamily.size() == familySize) {
                        return primeFamily;
                    }
                }
            }
        }

        return List.of();
    }

    private boolean isPrime(int n) {
        return primesSieve.isPrime(n);
    }

    private static int getStep(int n, int step) {
        if(step == 1) {
            int mod = n % 6;
            return mod == 5 ? 2 : mod == 1 ? 4 : 1;
        }
        return step == 2 ? 4 : 2;
    }

    private static void sortDigitPositions(List<List<DigitPosition>> digitPositionsList) {
        digitPositionsList.sort((List<DigitPosition> positions1, List<DigitPosition> positions2) -> {
            int result = 0;
            for(int i = positions1.size() - 1; i >= 0; i--) {
                result = Integer.compare(positions1.get(i).pos, positions2.get(i).pos);
                if(result != 0) {
                    break;
                }
            }
            return result;
        });
    }

    private static DigitData toDigitData(int n) {
        Set<Integer> digits = new LinkedHashSet<>();
        List<DigitPosition>[] digitPositions = new ArrayList[10];

        int pos = 0, pow = 1, digitSum = 0;
        while (n > 0) {
            int digit = n % 10;
            digitSum += digit;
            digits.add(digit);
            DigitPosition digitPosition = new DigitPosition(pos, pow, digit);
            if(digitPositions[digit] == null) {
                digitPositions[digit] = new ArrayList<>();
            }
            digitPositions[digit].add(digitPosition);
            pos++;
            pow *= 10;
            n = n / 10;
        }

        return new DigitData(digits, digitPositions, digitSum);
    }

    private static List<int[]> generateCombinations(int n, int k) {
        List<int[]> combinations = new ArrayList<>();
        if(n == k) {
            int[] combination = new int[k];
            for(int i = 0; i < k; i++) {
                combination[i] = i;
            }
            combinations.add(combination);
        }
        else {
            boolean[] used = new boolean[n];
            generateCombinations(n, k, 0, 0, used, new int[k], combinations);
        }
        return combinations;
    }

    private static void generateCombinations(int n, int left, int pos, int startDigit, boolean[] used, int[] combination, List<int[]> combinations) {
        if(left == 1) {
            for(int i = startDigit; i < n; i++) {
                if(!used[i]) {
                    combination[pos] = i;
                    combinations.add(combination.clone());
                }
            }
        }
        else {
            for(int i = startDigit; i < n; i++) {
                if(!used[i]) {
                    used[i] = true;
                    combination[pos] = i;
                    generateCombinations(n, left - 1, pos + 1, i + 1, used, combination, combinations);
                    used[i] = false;
                }
            }
        }
    }

    private static class DigitData {
        Set<Integer> digits;
        List<DigitPosition>[] digitPositions;
        int digitSum;

        public DigitData(Set<Integer> digits, List<DigitPosition>[] digitPositions, int digitSum) {
            this.digits = digits;
            this.digitPositions = digitPositions;
            this.digitSum = digitSum;
        }
    }

    private static class DigitPosition {
        int pos;
        int pow;
        int digit;

        public DigitPosition(int pos, int pow, int digit) {
            this.pos = pos;
            this.pow = pow;
            this.digit = digit;
        }

        @Override
        public String toString() {
            return "{" +
                    "pos=" + pos +
                    ", pow=" + pow +
                    ", digit=" + digit +
                    '}';
        }
    }
}
