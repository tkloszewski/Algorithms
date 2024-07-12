package com.smart.tkl.euler.p139;

public class PythagoreanTiles2 {

    //10057761
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int T = 100000;
        for (int i = 0; i < T; i++) {
            count(1000000000000000000L);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long count(long P) {
        long count = 0;

        long a = 3;
        long b = 4;
        long c = 5;

        long perimeter = a + b + c;

        while (perimeter < P) {
            long k = (P - 1) / perimeter;
            count += k;

            long newA = 3 * a + 2 * c + 1;
            long newB = newA + 1;
            long newC = 4 * a + 3 * c + 2;

            a = newA;
            b = newB;
            c = newC;

            perimeter = a + b + c;
        }

        return count;
    }
}
