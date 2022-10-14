package com.smart.tkl.combinatorics.permutation;

import com.smart.tkl.SortingUtils;

import java.util.Arrays;

public class SortedPermutationWithRepetitionGenerator implements PermutationGenerator {

    private final int[] tab;
    private final int allSetMask;

    public SortedPermutationWithRepetitionGenerator(int[] tab) {
        this.tab = SortingUtils.bubbleSort(tab);
        this.allSetMask = (int)Math.pow(2, tab.length) - 1;
    }

    public static void main(String[] args) {
        int[] table = new int[]{1, 3, 3, 3, 5};
        SortedPermutationWithRepetitionGenerator permutationGenerator = new SortedPermutationWithRepetitionGenerator(table);
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
            Integer lastValue = null;
            for (int i = 0; i < tab.length; i++) {
                if(((freeSlotMask >> i) & 1) == 0) {
                    continue;
                }
                if(lastValue != null && tab[i] == lastValue) {
                    continue;
                }
                int bit = 1 << i;
                array[pos] = tab[i];
                permute(array, pos + 1, freeSlotMask & ~bit);

                lastValue = tab[i];
            }
        }
    }
}
