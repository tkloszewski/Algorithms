package com.smart.tkl.lib;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BinarySearch {

    public static void main(String[] args) {
        List<Integer> input = toList(new int[]{1, 3 , 4, 5, 9, 11, 19, 20, 21, 23, 26, 29, 31, 33, 34, 35, 39, 40, 41, 42, 43});
        int foundIndex = search(21, input);
        System.out.printf("Found at index %d%n", foundIndex);
    }

    public static Integer search(int value, List<Integer> sortedInput) {
        Integer result = null;
        int start = 0;
        int end = sortedInput.size();

        while (start < end) {
            int index = (start + end)/2;
            Integer foundValue = sortedInput.get(index);
            if(foundValue.equals(value)) {
               result = index;
               break;
            }
            else if(foundValue > value) {
                end = index;
            }
            else {
                start = index;
            }
        }

        if(start == end && sortedInput.get(start).equals(value)) {
            result = start;
        }

        return result;
    }

    private static List<Integer> toList(int[] input) {
        return Arrays.stream(input).boxed().collect(Collectors.toList());
    }
}
