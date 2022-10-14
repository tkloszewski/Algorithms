package com.smart.tkl.combinatorics.permutation;

import com.smart.tkl.SortingUtils;
import com.smart.tkl.Stack;
import com.smart.tkl.utils.MathUtils;

import java.util.Arrays;
import java.util.Iterator;

public class SortedPermutationIterator implements Iterator<int[]> {


    private final int[] tab;
    private final long factorial;
    private final Stack<StackEntry> stack = new Stack<>();

    private int permutationCount = 0;

    public SortedPermutationIterator(int[] tab) {
        this.tab = SortingUtils.bubbleSort(tab);
        factorial = MathUtils.factorial(tab.length);
        stack.push(new StackEntry(0, new int[tab.length], 0, (int)Math.pow(2, tab.length) - 1));
    }

    public static void main(String[] args) {
        Iterator<int[]> iterator = new SortedPermutationIterator(new int[]{1, 2, 3, 4});
        while (iterator.hasNext()) {
            int[] array = iterator.next();
            System.out.println(Arrays.toString(array));
        }
    }

    @Override
    public boolean hasNext() {
        return permutationCount < factorial;
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
                    int bit = 1 << k;
                    stackEntry.i = k + 1;
                    if (((mask >> k) & 1) == 1) {
                        mask = mask & ~bit;
                        array[pos] = tab[k];
                        stack.push(new StackEntry(0, array, pos + 1, mask));
                        break;
                    }
                }
            }
        }
        return null;
    }

    private static final class StackEntry {
        int i;
        int[] array;
        int pos;
        int freeSlotMask;

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
