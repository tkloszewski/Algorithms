package com.smart.tkl.lib.combinatorics;

import java.util.Arrays;
import java.util.Iterator;

public class VariationWithRepetitionIterator implements Iterator<int[]> {

    private final int[] originalTab;
    private final int variationLength;
    private final int[] array;
    private int pos = 0;
    private final int[] posArray;
    private final long totalVariationsCount;
    private long variationsCount = 0;

    public VariationWithRepetitionIterator(int[] originalTab, int variationLength) {
        assert variationLength <= originalTab.length;
        this.originalTab = originalTab;
        this.variationLength = variationLength;
        this.array = new int[variationLength];
        this.posArray = new int[variationLength];
        this.totalVariationsCount = (long)Math.pow(originalTab.length, variationLength);
    }

    public static void main(String[] args) {
        VariationWithRepetitionIterator iterator = new VariationWithRepetitionIterator(new int[]{1, 2, 9, 11}, 3);
        while (iterator.hasNext()) {
            int[] array = iterator.next();
            System.out.println(Arrays.toString(array));
        }
    }

    @Override
    public boolean hasNext() {
        return variationsCount < totalVariationsCount;
    }

    @Override
    public int[] next() {
        variationsCount++;
        if(pos == variationLength - 1) {
           if(posArray[pos] < originalTab.length) {
              array[pos] = originalTab[posArray[pos]];
              posArray[pos]++;
           }
           else {
               posArray[pos] = 0;
               array[pos] = originalTab[0];
               int prevPos = pos - 1;
               while (prevPos >= 0 && posArray[prevPos] == originalTab.length - 1) {
                   posArray[prevPos] = 0;
                   array[pos] = originalTab[0];
                   prevPos--;
               }
               if(prevPos < 0) {
                  return null;
               }
               posArray[prevPos]++;
               array[prevPos] = originalTab[posArray[prevPos]];
               pos = variationLength - 1;
               posArray[pos] = 1;
           }
           return array;
        }
        else {
            for(int i = pos; i < variationLength; i++) {
                array[i] = originalTab[posArray[i]];
            }
            posArray[variationLength - 1]++;
            pos = variationLength - 1;
            return array;
        }
    }
}
