package com.smart.tkl.lib.combinatorics.permutation;

import java.util.Arrays;

public class BitMaskPermutationGenerator implements PermutationGenerator {

    private final int[] tab;
    private final int allSetMask;

    public BitMaskPermutationGenerator(int[] tab) {
        this.tab = tab;
        this.allSetMask = (int)Math.pow(2, tab.length) - 1;
    }

    public static void main(String[] args) {
        int[] table = new int[]{1, 2, 3, 4};
        BitMaskPermutationGenerator permutationGenerator = new BitMaskPermutationGenerator(table);
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
                int bit = 1 << i;
                if (((freeSlotMask >> i) & 1) == 1) {
                    array[pos] = tab[i];
                    permute(array, pos + 1, freeSlotMask & ~bit);
                }
            }
        }
    }
}
