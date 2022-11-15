package com.smart.tkl.euler.p137;

import java.util.ArrayList;
import java.util.List;

public class FibGoldenNuggets {

    private final int nuggetsCount;

    public FibGoldenNuggets(int nuggetsCount) {
        this.nuggetsCount = nuggetsCount;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        FibGoldenNuggets goldenNuggets = new FibGoldenNuggets(15);
        long goldenNugget = goldenNuggets.find();
        long time2 = System.currentTimeMillis();
        System.out.println("Found golden nugget: " + goldenNugget);
        System.out.println("Time elapsed in ms: " + (time2 - time1));
    }

    /*Generate pythagorean triplet using fibonacci sequence
    * (2h_{{n+1}}h_{{n+2}},h_{n}h_{{n+3}},2h_{{n+1}}h_{{n+2}}+h_{n}^{2})*/
    public long find() {
        List<Long> goldenNuggets = new ArrayList<>();
        long f1 = 1, f2 = 1, f3 = 2, f4 = 3;
        while (true) {
            long a = 2 * f2 * f3;
            long b = f1 * f4;
            long c = 2 * f2 * f3 + f1 * f1;

            long max = Math.max(a, b);
            long min = Math.min(a, b);

            if(max == 2 * min - 2) {
                long n = max / 2;
                goldenNuggets.add(n);
                if(goldenNuggets.size() == this.nuggetsCount) {
                    break;
                }
            }

            long sum = f3 + f4;
            f1 = f2;
            f2 = f3;
            f3 = f4;
            f4 = sum;
        }

        return goldenNuggets.get(goldenNuggets.size() - 1);
    }
}
