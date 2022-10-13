package com.smart.tkl.euler.p68;

import com.smart.tkl.combinatorics.CombinatoricsUtils;

import java.math.BigDecimal;
import java.util.*;

public class MagicRing {

    private final int ringSize;
    private final Integer digitLength;

    public static void main(String[] args) {
        //Check test 3-gong magic ring
        MagicGong.main(args);
        long time1 = System.currentTimeMillis();

        MagicRing magicRing;
        List<int[]> greatestMagicRing;
        BigDecimal value;


        magicRing = new MagicRing(3, 9);
        greatestMagicRing = magicRing.findGreatestMagicRing();
        System.out.println("Greatest magic 3-gong ring: ");
        printMagicRing(greatestMagicRing);
        value = toBigDecimal(greatestMagicRing, magicRing.digitLength);
        System.out.println("Greatest magic 3-gong ring value: " + value);

        System.out.println("------------------------------------------------");

        magicRing = new MagicRing(4, 12);
        greatestMagicRing = magicRing.findGreatestMagicRing();
        System.out.println("Greatest magic 4-gon ring: ");
        printMagicRing(greatestMagicRing);
        value = toBigDecimal(greatestMagicRing, magicRing.digitLength);
        System.out.println("Greatest magic 4-gon ring value: " + value);

        System.out.println("------------------------------------------------");

        magicRing = new MagicRing(5, 16);
        greatestMagicRing = magicRing.findGreatestMagicRing();
        System.out.println("Greatest magic 5-gon ring: ");
        printMagicRing(greatestMagicRing);
        value = toBigDecimal(greatestMagicRing, magicRing.digitLength);
        System.out.println("Greatest magic 5-gon ring value: " + value);

        System.out.println("------------------------------------------------");

        magicRing = new MagicRing(6);
        greatestMagicRing = magicRing.findGreatestMagicRing();
        System.out.println("Greatest magic 6-gon ring: ");
        printMagicRing(greatestMagicRing);
        value = toBigDecimal(greatestMagicRing);
        System.out.println("Greatest magic 6-gon ring value: " + value);

        System.out.println("------------------------------------------------");

        magicRing = new MagicRing(7);
        greatestMagicRing = magicRing.findGreatestMagicRing();
        System.out.println("Greatest magic 7-gon ring: ");
        printMagicRing(greatestMagicRing);
        value = toBigDecimal(greatestMagicRing);
        System.out.println("Greatest magic 7-gon ring value: " + value);

        magicRing = new MagicRing(8);
        greatestMagicRing = magicRing.findGreatestMagicRing();
        System.out.println("Greatest magic 8-gon ring: ");
        printMagicRing(greatestMagicRing);
        value = toBigDecimal(greatestMagicRing);
        System.out.println("Greatest magic 8-gon ring value: " + value);

        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public MagicRing(int ringSize) {
        this.ringSize = ringSize;
        this.digitLength = null;
    }

    public MagicRing(int size, int digitLength) {
        this.ringSize = size;
        this.digitLength = digitLength;
    }

    public BigDecimal findGreatestMagicRingValue() {
        return findGreatestMagicRingValue(this.ringSize);
    }

    public BigDecimal findGreatestMagicRingValue(int ringSize) {
        List<int[]> greatestMagicRing = findGreatestMagicRing(ringSize);
        return this.digitLength != null ? toBigDecimal(greatestMagicRing, this.digitLength) : toBigDecimal(greatestMagicRing);
    }

    public List<int[]> findGreatestMagicRing() {
        return findGreatestMagicRing(this.ringSize);
    }

    public List<int[]> findGreatestMagicRing(int ringSize) {
        List<List<int[]>> magicRings = findAllMagicRings(ringSize);
        System.out.println("All magic ring sizes: " + magicRings.size());
        for(List<int[]> gong : magicRings) {
            printMagicRing(gong);
        }
        return getGreatestMagicRing(magicRings);
    }

    public List<List<int[]>> findAllMagicRings() {
        return findAllMagicRings(this.ringSize);
    }

    public List<List<int[]>> findAllMagicRings(int ringSize) {
        Set<Integer> input = getInput(ringSize * 2);
        Set<int[]> combinations = CombinatoricsUtils.combinations(input, 3);

        int minSum = getMinSum(ringSize);
        int maxSum = getMaxSum(ringSize);

        Map<Integer, List<int[]>> combinationsMap = new HashMap<>();
        for(int[] combination : combinations) {
            int sum = combinationSum(combination);
            if(sum >= minSum && sum <= maxSum) {
                if (combinationsMap.containsKey(sum)) {
                    combinationsMap.get(sum).add(combination);
                } else {
                    List<int[]> sameSumList = new ArrayList<>();
                    sameSumList.add(combination);
                    combinationsMap.put(sum, sameSumList);
                }
            }
        }
        Map<Integer, List<int[]>> filteredCombinationsMap = new LinkedHashMap<>();
        for(Map.Entry<Integer, List<int[]>> entry : combinationsMap.entrySet()) {
            if(entry.getValue().size() >= ringSize) {
                filteredCombinationsMap.put(entry.getKey(), entry.getValue());
            }
        }

        List<List<int[]>> allMagicRings = new ArrayList<>(filteredCombinationsMap.size() * 2);
        for(Map.Entry<Integer, List<int[]>> entry : filteredCombinationsMap.entrySet()) {
            Set<int[]> positionCombinations = CombinatoricsUtils.combinations(getInput(entry.getValue().size()), ringSize);
            for(int[] positionCombination : positionCombinations) {
                List<int[]> chosenCombination = chooseCombinations(positionCombination, entry.getValue());
                List<List<int[]>> magicRings = getMagicRings(chosenCombination, ringSize);
                if(magicRings.size() > 0) {
                    allMagicRings.addAll(magicRings);
                }
            }
        }

        return allMagicRings;
    }

    private int getMaxSum(int size) {
        int maxNum = 2 * size;
        int sumOffAllElements = size * (1 + 2 * size);
        int max3ElementsSum = maxNum + maxNum - 1 + maxNum - 2;
        return (sumOffAllElements + max3ElementsSum) / size;
    }

    private int getMinSum(int size) {
        int sumOffAllElements = size * (1 + 2 * size);
        int minSum = (sumOffAllElements + 6) / size;
        if(size * minSum < sumOffAllElements + 6) {
            minSum++;
        }
        return minSum;
    }

    private List<List<int[]>> getMagicRings(List<int[]> combinations, int magicRingSize) {
        List<Set<Integer>> sets = new ArrayList<>(combinations.size());
        Set<Integer> allNumbers = new HashSet<>();
        for(int[] combination : combinations) {
            Set<Integer> set = new LinkedHashSet<>();
            for(int val : combination) {
                set.add(val);
                allNumbers.add(val);
            }
            sets.add(set);
        }

        if(allNumbers.size() != magicRingSize * 2) {
            return new ArrayList<>();
        }

        int firstCombinationIdx = -1, smallestFirstElement = Integer.MAX_VALUE;
        for(int i = 0; i < combinations.size(); i++) {
            int[] combination = combinations.get(i);

            boolean foundExternalRingValue = false;
            for(int k = 0; k < combination.length; k++) {
                boolean externalRingValue = true;
                for(int j = (i + 1) % sets.size(); j != i; j = (j + 1) % sets.size()) {
                    if(sets.get(j).contains(combination[k])) {
                        externalRingValue = false;
                        break;
                    }
                }
                if(externalRingValue) {
                    if (k != 0) {
                        swap(combination, 0, k);
                    }
                    foundExternalRingValue = true;
                    break;
                }
            }

            if(!foundExternalRingValue) {
                return new ArrayList<>();
            }

            if(smallestFirstElement > combination[0]) {
                firstCombinationIdx = i;
                smallestFirstElement = combination[0];
            }
        }

        List<List<int[]>> magicRings = new ArrayList<>(2);
        List<int[]> magicRing1 = getMagicRing(combinations, firstCombinationIdx, magicRingSize);

        combinations = copyCombinations(combinations);
        int[] firstCombination = combinations.get(firstCombinationIdx);
        swap(firstCombination, 1, 2);

        List<int[]> magicRing2 = getMagicRing(combinations, firstCombinationIdx, magicRingSize);

        if (magicRing1.size() == magicRingSize) {
            magicRings.add(magicRing1);
        }
        if (magicRing2.size() == magicRingSize) {
            magicRings.add(magicRing2);
        }

        return magicRings;
    }

    private List<int[]> getMagicRing(List<int[]> combinations, int firstCombinationIdx, int magicRingSize) {
        List<int[]> result = new ArrayList<>(combinations.size());
        int[] firstCombination = combinations.get(firstCombinationIdx);

        result.add(firstCombination.clone());
        int nextCombinationIdx = firstCombinationIdx;
        int[] nextCombination = null;

        int flag = 1 << firstCombinationIdx;

        while (result.size() != magicRingSize && nextCombinationIdx != -1) {
            nextCombinationIdx = getNextCombinationIdx(flag, nextCombinationIdx, combinations);
            if (nextCombinationIdx != -1) {
                flag = flag | (1 << nextCombinationIdx);
                nextCombination = combinations.get(nextCombinationIdx);
                result.add(nextCombination.clone());
            }
        }

        if(result.size() != magicRingSize) {
           return new ArrayList<>();
        }

        if(nextCombination != null && firstCombination[1] != nextCombination[2]) {
            return new ArrayList<>();
        }

        return result;
    }

    private static int getNextCombinationIdx(int flag, int nextCombinationIdx, List<int[]> combinations) {
        int size = combinations.size();
        int[] firstCombination = combinations.get(nextCombinationIdx);

        int result = -1;

        for(int j = (nextCombinationIdx + 1) % size; j != nextCombinationIdx; j = (j + 1) % size) {
            if(((flag >> j) & 1) == 1) {
                continue;
            }
            int[] combination = combinations.get(j);
            if(firstCombination[2] == combination[1]) {
                result = j;
                break;
            }
            else if(firstCombination[2] == combination[2]) {
                swap(combination, 1 ,2);
                result = j;
                break;
            }
        }

        return result;
    }

    private List<int[]> getGreatestMagicRing(List<List<int[]>> magicRings) {
        List<int[]> result = null;
        BigDecimal maxValue = BigDecimal.ZERO;
        boolean withDigitLength = this.digitLength != null;
        for(List<int[]> magicRing : magicRings) {
            BigDecimal magicRingValue = withDigitLength ? toBigDecimal(magicRing, this.digitLength) : toBigDecimal(magicRing);
            if(maxValue.compareTo(magicRingValue) < 0) {
                maxValue = magicRingValue;
                result = magicRing;
            }
        }
        return result;
    }

    private static BigDecimal toBigDecimal(List<int[]> magicRing) {
        StringBuilder sb = new StringBuilder();
        for(int[] combination : magicRing) {
            for(int value : combination) {
                sb.append(value);
            }
        }
        return new BigDecimal(sb.toString());
    }

    private static BigDecimal toBigDecimal(List<int[]> magicRing, int digitLength) {
        StringBuilder sb = new StringBuilder();
        for(int[] combination : magicRing) {
            for(int value : combination) {
                sb.append(value);
            }
        }
        String s = sb.toString();
        return s.length() == digitLength ?  new BigDecimal(sb.toString()) : BigDecimal.ZERO;
    }

    private static int combinationSum(int[] combination) {
        int result = 0;
        for(int num : combination) {
            result += num;
        }
        return result;
    }

    private static List<int[]> copyCombinations(List<int[]> combinations) {
        List<int[]> result = new ArrayList<>(combinations.size());
        for(int[] combination : combinations) {
            result.add(combination.clone());
        }
        return result;
    }

    private static List<int[]> chooseCombinations(int[] combination, List<int[]> combinations) {
        List<int[]> result = new ArrayList<>();
        for(int pos : combination) {
            result.add(combinations.get(pos - 1));
        }
        return result;
    }

    private static Set<Integer> getInput(int size) {
        Set<Integer> result = new HashSet<>();
        for(int i = 1; i <= size; i++) {
            result.add(i);
        }
        return result;
    }

    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }

    private static void printMagicRing(List<int[]> combinations) {
        System.out.print("[");
        for(int[] combination : combinations) {
            System.out.print(Arrays.toString(combination));
        }
        System.out.println("]");
    }


}
