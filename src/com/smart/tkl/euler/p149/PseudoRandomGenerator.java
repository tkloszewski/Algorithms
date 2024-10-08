package com.smart.tkl.euler.p149;

import java.util.Arrays;

public class PseudoRandomGenerator implements TriFunction<long[][], Integer, Integer, Long> {

    private final int l;
    private final int[] a;
    private final int[] f;
    private final int m;
    private final int[] b;
    private final int[] g;

    private int fSum;
    private int gSum;

    public PseudoRandomGenerator(int l, int[] a, int[] f5, int m, int[] b, int[] g5) {
        this.l = l;
        this.a = a;
        this.f = init(5, f5);
        this.m = m;
        this.b = b;
        this.g = init(5, g5);
        this.fSum = Arrays.stream(f5).sum() % l;
        this.gSum = Arrays.stream(g5).sum() % m;
    }

    @Override
    public Long apply(long[][] table, Integer k, Integer n) {
        if(k < 5) {
           return (long)(a[f[k]] + b[g[k]]);
        }
        else {
           int index = k % 5;
           int fNewValue = fSum;
           fSum = (fSum - f[index] + fNewValue) % l;
           f[index] = fNewValue;
           if(fSum < 0) {
              fSum += l;
           }
           int gNewValue = gSum;
           gSum = (gSum - g[index] + gNewValue) % m;
           g[index] = gNewValue;
           if(gSum < 0) {
              gSum += m;
           }

           return (long)(a[f[index]] + b[g[index]]);
        }
    }

    private static int[] init(int size, int[] t2) {
        int[] result = new int[size];
        System.arraycopy(t2, 0, result, 0, t2.length);
        return result;
    }
}
