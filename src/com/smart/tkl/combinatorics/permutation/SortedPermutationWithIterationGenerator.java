package com.smart.tkl.combinatorics.permutation;

import com.smart.tkl.SortingUtils;

import java.util.Arrays;

public class SortedPermutationWithIterationGenerator implements PermutationGenerator {

    private final int[] tab;
    private final int allSetMask;

    public SortedPermutationWithIterationGenerator(int[] tab) {
        this.tab = SortingUtils.bubbleSort(tab);
        this.allSetMask = (int)Math.pow(2, tab.length) - 1;
    }

    public static void main(String[] args) {
        int[] table = new int[]{1, 2, 2};
        SortedPermutationWithIterationGenerator permutationGenerator = new SortedPermutationWithIterationGenerator(table);
        permutationGenerator.generatePermutations();
    }

    @Override
    public void generatePermutations() {
        permute(new int[tab.length], 0, allSetMask);
    }

    private void permute(int[] array, int pos, int freeSlotMask) {
        if(freeSlotMask == 0) {
            System.out.println(Arrays.toString(array));
        }
        else {
            for (int i = 0; i < tab.length; i++) {
                if(((freeSlotMask >> i) & 1) == 0) {
                    continue;
                }
                int firstValue = tab[i];
                int newMask = freeSlotMask;
                int newPos = pos;
                int j = i;
                while (j < tab.length && tab[j] == firstValue) {
                    int bit = 1 << j;
                    array[newPos] = tab[j];
                    newMask = newMask & ~bit;
                    newPos++;
                    i = j;
                    j++;
                }
                permute(array, newPos, newMask);
            }
        }
    }
}
