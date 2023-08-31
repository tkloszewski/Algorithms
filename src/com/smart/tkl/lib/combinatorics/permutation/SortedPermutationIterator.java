package com.smart.tkl.lib.combinatorics.permutation;

import com.smart.tkl.lib.SortingUtils;
import com.smart.tkl.lib.Stack;

import java.util.Arrays;
import java.util.Iterator;

public class SortedPermutationIterator implements Iterator<int[]> {

    private final int[] tab;
    private final long totalPermutationsCount;
    private final Stack<StackEntry> stack = new Stack<>();

    private int permutationCount = 0;
    private long value;

    public SortedPermutationIterator(int[] tab) {
        this(tab, true);
    }

    public SortedPermutationIterator(int[] tab, boolean ascending) {
        int[] sortedTab = SortingUtils.bubbleSort(tab);
        this.tab = ascending ? sortedTab : revert(sortedTab);
        value = 0;
        totalPermutationsCount = countTotalPermutations(this.tab);
        stack.push(new StackEntry(0, new int[tab.length], 0, (int)Math.pow(2, tab.length) - 1));
    }

    public static void main(String[] args) {
        SortedPermutationIterator iterator = new SortedPermutationIterator(new int[]{1, 1, 3, 3});
        while (iterator.hasNext()) {
            int[] array = iterator.next();
            System.out.println(Arrays.toString(array));
        }
    }

    @Override
    public boolean hasNext() {
        return permutationCount < totalPermutationsCount;
    }

    @Override
    public int[] next() {
        while (!stack.isEmpty()) {
            StackEntry stackEntry = stack.peek();
            int mask = stackEntry.freeSlotMask;
            int[] array = stackEntry.array;
            int pos = stackEntry.pos;

            if(mask == 0 || stackEntry.i >= tab.length) {
                stack.pop();
                if (mask == 0) {
                    permutationCount++;
                    return array;
                }
            }
            else {
                for (int k = stackEntry.i; k < tab.length; k++) {
                    stackEntry.i = k + 1;
                    if(((mask >> k) & 1) == 0) {
                        continue;
                    }
                    if(stackEntry.lastValue != null && tab[k] == stackEntry.lastValue) {
                        continue;
                    }
                    int bit = 1 << k;
                    array[pos] = tab[k];
                    stackEntry.lastValue = tab[k];
                    stack.push(new StackEntry(0, array, pos + 1, mask & ~bit));
                    break;
                }
            }
        }
        return null;
    }

    public PermutedElement nextPermutedElement() {
        while (!stack.isEmpty()) {
            StackEntry stackEntry = stack.peek();
            int mask = stackEntry.freeSlotMask;
            int[] array = stackEntry.array;
            int pos = stackEntry.pos;

            if(mask == 0 || stackEntry.i >= tab.length) {
                stack.pop();
                if (mask == 0) {
                    permutationCount++;
                    return new PermutedElement(array, value);
                }
            }
            else {
                for (int k = stackEntry.i; k < tab.length; k++) {
                    stackEntry.i = k + 1;
                    if(((mask >> k) & 1) == 0) {
                        continue;
                    }
                    if(stackEntry.lastValue != null && tab[k] == stackEntry.lastValue) {
                        continue;
                    }
                    int bit = 1 << k;

                    long pow = (long)Math.pow(10, tab.length - 1 - pos);
                    value -= pow * array[pos];
                    array[pos] = tab[k];
                    value += pow * array[pos];
                    stackEntry.lastValue = tab[k];
                    stack.push(new StackEntry(0, array, pos + 1, mask & ~bit));
                    break;
                }
            }
        }
        return null;
    }

    private long countTotalPermutations(int[] sortedTab) {
        long result = 1;
        long numerator = 1;
        long denominator = 1;
        int factorialNumerator = 1;

        for(int i = 1; i < sortedTab.length; i++) {
            if(sortedTab[i] == sortedTab[i - 1]) {
                int k = i;
                int factorialDenominator = 1;
                while (k < sortedTab.length && sortedTab[k] == sortedTab[k - 1]) {
                    numerator *= ++factorialNumerator;
                    denominator *= ++factorialDenominator;
                    k++;
                }
                i = k - 1;
            }
            else {
                numerator *= ++factorialNumerator;
            }
            if(denominator != 1 && numerator % denominator == 0) {
                result *= (double) numerator / denominator;
                numerator = 1;
                denominator = 1;
            }
        }
        result *= numerator;
        result /= denominator;

        return  result;
    }

    private static int[] revert(int[] tab) {
        int[] result = new int[tab.length];
        for(int i = tab.length - 1, k = 0; i >=0 ;i--, k++) {
            result[k] = tab[i];
        }
        return result;
    }

    private long toValue(int[] arr) {
        long result = 0, pow = 1;
        for(int i = arr.length - 1;  i >= 0; i--, pow *= 10) {
            result += pow * arr[i];
        }
        return result;
    }

    private static final class StackEntry {
        int i;
        int[] array;
        int pos;
        int freeSlotMask;
        Integer lastValue = null;

        public StackEntry(int i, int[] array, int pos, int freeSlotMask) {
            this.i = i;
            this.array = array;
            this.pos = pos;
            this.freeSlotMask = freeSlotMask;
        }

        @Override
        public String toString() {
            return "StackEntry{" +
                    "i=" + i +
                    ", array=" + Arrays.toString(array) +
                    ", pos=" + pos +
                    ", freeSlotMask=" + Integer.toBinaryString(freeSlotMask) +
                    '}';
        }
    }
}
