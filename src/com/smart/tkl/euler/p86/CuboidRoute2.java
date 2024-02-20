package com.smart.tkl.euler.p86;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.Arrays;

public class CuboidRoute2 {

    private final int limitM;

    public CuboidRoute2(int limitM) {
        this.limitM = limitM;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int limit = 1000000;
        CuboidRoute2 cuboidRoute = new CuboidRoute2(limit);
        long[] counts = cuboidRoute.countAll();
        long time2 = System.currentTimeMillis();
        System.out.println(counts[limit]);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long[] countAll() {
        long[] cumulativeCount = new long[limitM + 1];
        long[] cuboids = countCuboids();

        long count = 0;
        for(int m = 1; m <= limitM; m++) {
            count += cuboids[m];
            cumulativeCount[m] = count;
        }

        return cumulativeCount;
    }

    public long[] countCuboids() {
        long[] cuboids = new long[limitM + 1];

        int mMax = (int)Math.sqrt((5.0 * limitM) / 2.0);

        for(int m = 1; m <= mMax; m++) {
            for(int n = 1; n < m; n++) {
                if((n + m) % 2 == 1 && MathUtils.GCD(m, n) == 1) {
                    int a = m * m - n * n;
                    int sum = 2 * m * n;

                    for(int k = 1; k * a <= limitM; k++) {
                        long count = count((long)k * a, (long)k * sum);
                        cuboids[k * a] += count;
                    }
                    for(int k = 1; k * sum <= limitM; k++) {
                        long count = count((long)k * sum, (long)k * a);
                        cuboids[k * sum] += count;
                    }
                }
            }
        }

        return cuboids;
    }

    private long count(long a, long sum) {
        if(2 * a < sum) {
           return 0;
        }
        if(a >= sum) {
           return sum / 2;
        }
        return a - (sum - 1) / 2;
    }
}
