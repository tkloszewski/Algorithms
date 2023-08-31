package com.smart.tkl.lib.combinatorics.permutation;

import java.util.Arrays;

public class PermutedElement {
    final int[] tab;
    final long value;

    public PermutedElement(int[] tab, long value) {
        this.tab = tab;
        this.value = value;
    }

    public int[] getTab() {
        return tab;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PermutedElement{" +
                "tab=" + Arrays.toString(tab) +
                ", value=" + value +
                '}';
    }
}
