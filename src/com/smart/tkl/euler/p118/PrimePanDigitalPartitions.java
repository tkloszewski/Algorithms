package com.smart.tkl.euler.p118;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimePanDigitalPartitions {

    public static List<List<Integer>> getPartitions(int size) {
        List<List<Integer>> result = new ArrayList<>();
        fillSumsOfSize(size, 9, 0,1, 0, new Integer[size], result);
        return result;
    }

    private static void fillSumsOfSize(int size, int target, int pos, int startNumber, int oneChosenCount, Integer[] currentChosen, List<List<Integer>> result) {
        int startElement = oneChosenCount < 4 ? Math.max(1, startNumber) : Math.max(2, startNumber);
        if(pos == size - 1) {
            if (target >= startElement) {
                currentChosen[pos] = target;
                result.add(Arrays.asList(currentChosen.clone()));
            }
        }
        else {
            for(int number = startElement; number <= 8; number++) {
                if(target - number < startElement) {
                    break;
                }
                currentChosen[pos] = number;
                fillSumsOfSize(size,target - number, pos + 1, number, number == 1 ?
                        oneChosenCount + 1 : oneChosenCount, currentChosen, result);
            }
        }
    }

}
