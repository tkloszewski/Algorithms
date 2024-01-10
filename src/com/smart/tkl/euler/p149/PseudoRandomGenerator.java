package com.smart.tkl.euler.p149;

import java.util.Arrays;

public class PseudoRandomGenerator implements TriFunction<long[][], Integer, Integer, Long> {

    private int l;
    private int[] a;
    private int[] f;
    private int m;
    private int[] b;
    private int[] g;

    private int fSum;
    private int gSum;

    public PseudoRandomGenerator(int l, int[] a, int[] f5, int m, int[] b, int[] g5) {
        this.l = l;
        this.a = a;
        this.f = init(l, f5);
        this.m = m;
        this.b = b;
        this.g = init(m, g5);
        this.fSum = Arrays.stream(f5).sum();
        this.gSum = Arrays.stream(g5).sum();
    }

    @Override
    public Long apply(long[][] table, Integer k, Integer n) {
        if(k < 5) {
           return (long)(a[f[k]] + b[g[k]]);
        }
        else {
           f[k] = fSum;
           fSum = (2 * fSum - f[k - 5]) % l;
           g[k] = gSum;
           gSum = (2 * gSum - g[k - 5]) % m;

           return (long)(a[f[k]] + b[g[k]]);
        }
    }

    private static int[] init(int size, int[] t2) {
        int[] result = new int[size];
        for(int i = 0; i < t2.length; i++) {
            result[i] = t2[i];
        }
        return result;
    }
}
