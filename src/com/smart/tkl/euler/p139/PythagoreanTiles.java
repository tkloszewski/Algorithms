package com.smart.tkl.euler.p139;

import com.smart.tkl.utils.MathUtils;

public class PythagoreanTiles {

    private final int perimeterLimit;

    public PythagoreanTiles(int perimeterLimit) {
        this.perimeterLimit = perimeterLimit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PythagoreanTiles pythagoreanTiles = new PythagoreanTiles(100000000);
        long count = pythagoreanTiles.countTriangles();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public long countTriangles() {
        long count = 0;
        double sqrt = Math.sqrt(2 * perimeterLimit + 1);
        long limitM = (long) ((sqrt - 1) / 2.0);
        if(sqrt == (int)sqrt && (int)sqrt % 2 == 1) {
            limitM--;
        }

        for(long m = 2; m <= limitM; m++){
            for(long n = 1; n < m; n++) {
                if((m + n) % 2 == 1 && MathUtils.GCD(m, n) == 1) {
                    long a = m * m - n * n;
                    long b = 2 * m * n;
                    long diff = b - a;
                    if((a * a + b * b) % (diff * diff) == 0) {
                        long p = 2 * m * (m + n);
                        long baseCount = perimeterLimit / p;

                        if(perimeterLimit % p == 0) {
                           baseCount--;
                        }
                        count += baseCount;
                    }
                }
            }
        }

        return count;
    }
}
