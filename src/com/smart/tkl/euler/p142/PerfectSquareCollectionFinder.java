package com.smart.tkl.euler.p142;

public class PerfectSquareCollectionFinder {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PerfectSquareCollection smallest = new PerfectSquareCollectionFinder().findSmallest();
        long time2 = System.currentTimeMillis();
        System.out.println("Smallest: " + smallest);
        System.out.println("Found solution in ms: " + (time2 - time1));
    }

    public PerfectSquareCollection findSmallest() {
        for(long a = 3;; a++) {
            for(long b = 2; b < a; b++) {
                if(isSquare(a * a - b * b)) {
                    for(long c = 1; c < b; c++) {
                        if(isSquare(a * a - c * c) && isSquare(b * b - c * c)) {
                            long x2 = a * a + b * b - c * c;
                            long y2 = a * a - b * b + c * c;
                            long z2 = b * b + c * c - a * a;
                            if (x2 % 2 == 0 && z2 > 0) {
                                long x = x2 / 2;
                                long y = y2 / 2;
                                long z = z2 / 2;
                                return new PerfectSquareCollection(x, y, z);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isSquare(long n) {
        double sqrt = Math.sqrt(n);
        return sqrt == (long)sqrt;
    }

    private static class PerfectSquareCollection {
        final long x;
        final long y;
        final long z;
        final long sum;

        public PerfectSquareCollection(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.sum = x + y + z;
        }

        @Override
        public String toString() {
            return "PerfectSquareCollection{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", sum=" + sum +
                    '}';
        }
    }

}
