package com.smart.tkl.lib.utils;

import java.util.HashSet;
import java.util.Set;

public class GenericUtils {

    public static int[][] clone(int[][] in) {
        int[][] out = new int[in.length][];
        for(int i = 0; i < in.length; i++) {
            out[i] = in[i].clone();
        }
        return out;
    }

    public static long[][] clone(long[][] in) {
        long[][] out = new long[in.length][];
        for(int i = 0; i < in.length; i++) {
            out[i] = in[i].clone();
        }
        return out;
    }

    public static boolean hasUniqueChars(String s) {
        Set<Integer> set = new HashSet<>();
        for(char c : s.toCharArray()) {
            set.add((int)c);
        }
        return set.size() == s.length();
    }

    public static int[] newRangeReversed(int from, int to) {
        int[] t = new int[to - from + 1];
        for(int i = to, j = t.length - 1; i >= from; i--) {
            t[j--] = i;
        }
        return t;
    }

    public static int[] newRange(int from, int to) {
        int[] t = new int[to - from + 1];
        for(int i = from, j=0; i<= to; i++) {
            t[j++] = i;
        }
        return t;
    }

    public static void printTable(int[] t) {
        for (int j : t) {
            System.out.print(j + " ");
        }
        System.out.println();
    }


}
