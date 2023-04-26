package com.smart.tkl.lib.combinatorics.permutation;

import java.util.Arrays;

public class SwapPermutationGenerator {

    private static volatile long time1, time2;

    private PermutationListener permutationListener;

    public SwapPermutationGenerator(PermutationListener permutationListener) {
        this.permutationListener = permutationListener;
    }

    public void generate(int length) {
        generate(length, this.permutationListener);
    }

    public void generate(int length, PermutationListener permutationListener) {
        int[] tab = newRange(0, length -1);
        permutation(tab, 0, permutationListener);
    }

    public void generate(int[] tab) {
        permutation(tab, 0, this.permutationListener);
    }

    public void generate(int[] tab, PermutationListener permutationListener) {
        permutation(tab, 0, permutationListener);
    }

    public void generateSorted(int[] tab) {
        sortedPermutation(tab, 0, this.permutationListener);
    }

    public void generateSortedDesc(int[] tab) {
        sortedPermutationDesc(tab, 0, this.permutationListener);
    }

    private static int[] newRange(int from, int to) {
        int[] t = new int[to - from + 1];
        for(int i = from, j=0; i<= to; i++) {
            t[j++] = i;
        }
        return t;
    }

    private static void permutation(int[] t, int pos, PermutationListener permutationListener) {
        if(pos == t.length - 1) {
            permutationListener.permutation(t);
        }
        else {
            permutation(t, pos + 1, permutationListener);
            for(int i = pos + 1; i < t.length; i++) {
                swap(t, pos, i);
                permutation(t, pos + 1, permutationListener);
                swap(t, pos, i);
            }
        }
        //Number of swaps: n! + 2(n-1)
    }

    private static void sortedPermutation(int[] t, int pos, PermutationListener permutationListener) {
        if(pos == t.length - 1) {
            permutationListener.permutation(t);
        }
        else {
            sortedPermutation(t, pos + 1, permutationListener);
            for(int i = pos + 1; i < t.length; i++) {
                swap(t, pos, i);

                int[] preSorted = Arrays.copyOf(t, t.length);

                //sort rest of the table with simple bubble sort
                bubbleSort(t, pos + 1);
                sortedPermutation(t, pos + 1, permutationListener);

                //go back to pre sorted table state
                copyTable(preSorted, t, pos + 1);

                //swap the same element
                swap(t, pos, i);
            }
        }
    }

    private static void sortedPermutationDesc(int[] t, int pos, PermutationListener permutationListener) {
        if(pos == t.length - 1) {
           permutationListener.permutation(t);
        }
        else {
            sortedPermutationDesc(t, pos + 1, permutationListener);
            for(int i = pos + 1; i < t.length; i++) {
                swap(t, pos, i);

                int[] preSorted = Arrays.copyOf(t, t.length);

                //sort rest of the table with simple bubble sort
                bubbleSortDesc(t, pos + 1);
                sortedPermutationDesc(t, pos + 1, permutationListener);


                //go back to pre sorted table state
                copyTable(preSorted, t, pos + 1);

                //swap the same element
                swap(t, pos, i);
            }
        }
    }

    private static void permutationIterative(int len) {
        int[] tab = newRange(0, len -1);
        permutationIterative(tab, new PermutationListener(){});
    }

    private static void permutationIterative(int[] t, PermutationListener permutationListener) {
        int[] c = new int[t.length];
        for(int i = 0; i < c.length; i++) {
            c[i] = i+1;
        }

        permutationListener.permutation(t);
        int pos = t.length - 1;
        int lastPos = pos, lastCPos = c[pos] - 1;
        while(pos >= 0) {
            if(c[pos] < t.length) {
                lastPos = pos;
                lastCPos = c[pos];
                swap(t, pos, c[pos]);

                permutationListener.permutation(t);

                c[pos] += 1;
                pos = t.length - 1;
            }
            else {
                c[pos] = pos + 1;
                pos -= 1;
            }
            swap(t, lastPos, lastCPos);
        }
    }

    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }

    private static void printTable(int[] t) {
        for (int j : t) {
            System.out.print(j + " ");
        }
        System.out.println();
    }

    private static int[] bubbleSort(int[] t, int pos) {
        for(int i = pos; i < t.length; i++) {
            for(int j = i + 1; j < t.length; j++) {
                if(t[i] > t[j]) {
                    swap(t, i , j);
                }
            }
        }
        return t;
    }

    private static int[] bubbleSortDesc(int[] t, int pos) {
        for(int i = pos; i < t.length; i++) {
            for(int j = i + 1; j < t.length; j++) {
                if(t[i] < t[j]) {
                    swap(t, i , j);
                }
            }
        }
        return t;
    }

    private static void copyTable(int[] from, int[] to, int pos) {
        for(int k = pos; k < from.length; k++) {
            to[k] = from[k];
        }
    }


}
