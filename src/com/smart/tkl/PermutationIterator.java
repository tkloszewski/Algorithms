package com.smart.tkl;

import com.smart.tkl.utils.GenericUtils;
import com.smart.tkl.utils.MathUtils;

import java.util.Arrays;
import java.util.Iterator;

public class PermutationIterator implements Iterator<int[]> {

    private final int[] current;
    private long count = 0;
    private final long limit;

    private final int[] c;
    private int pos;
    private int lastPos;
    private int lastCPos;

    public static void main(String[] args) {
        int[] tab = new int[]{1, 2, 3};
        PermutationIterator permutationIterator = new PermutationIterator(tab);
        while(permutationIterator.hasNext()) {
            int[] permutation = permutationIterator.next();
            System.out.println("Permutation: " + Arrays.toString(permutation));
        }
    }

    public PermutationIterator(int[] tab) {
        this.current = tab.clone();
        this.limit = MathUtils.factorial(tab.length);

        this.c = new int[tab.length];
        for(int i = 0; i < c.length; i++) {
            c[i] = i + 1;
        }

        pos = tab.length - 1;
        lastPos = pos;
        lastCPos = c[pos] - 1;
    }

    @Override
    public boolean hasNext() {
        return count < limit && pos >= 0;
    }

    @Override
    public int[] next() {
        if(count == 0) {
           count++;
           return current.clone();
        }
        int[] result = null;
        if(pos >= 0 && pos < current.length) {
            while (pos >= 0 && c[pos] >= current.length) {
                c[pos] = pos + 1;
                pos -= 1;
                swap(current, lastPos, lastCPos);
            }
            if(pos >= 0 && c[pos] < current.length) {
                lastPos = pos;
                lastCPos = c[pos];

                swap(current, pos, c[pos]);

                c[pos] += 1;
                pos = current.length - 1;

                result = current.clone();
                count++;
            }
            swap(current, lastPos, lastCPos);
        }
        return result;
    }

    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }
}
