package com.smart.tkl.euler.p149;

public class MaximumSubsequence {

    private static final long[][] EXAMPLE_TAB = {
            {-2, 5, 3, 2},
            {9, -6, 5, 1},
            {3, 2, 7, 3},
            {-1, 8, -4, 8}
    };

    private final int limit;
    private final TriFunction<long[][], Integer, Integer, Long> nextElementResolver;
    private final long[][] tab;
    private final long[] rowSums;
    private final long[] columnSums;
    private final long[] diagonalSums;
    private final long[] antiDiagonalSums;

    public MaximumSubsequence(int limit,TriFunction<long[][], Integer, Integer, Long> nextElementResolver) {
        this.limit = limit;
        this.nextElementResolver = nextElementResolver;
        this.tab = new long[limit][limit];
        this.rowSums = new long[limit];
        this.columnSums = new long[limit];
        this.diagonalSums = new long[2 * limit - 1];
        this.antiDiagonalSums = new long[2 * limit - 1];
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        MaximumSubsequence maximumSubsequence = new MaximumSubsequence(4, (tab, k, n) -> EXAMPLE_TAB[(k - 1) / 4][(k - 1) % 4]);
        long max = maximumSubsequence.resolveMax();
        long time2 = System.currentTimeMillis();
        System.out.println("Max: " + max);
        System.out.println("Time : " + (time2 - time1));

        time1 = System.currentTimeMillis();
        maximumSubsequence = new MaximumSubsequence(3000, MaximumSubsequence::nextElement);
        max = maximumSubsequence.resolveMax();
        time2 = System.currentTimeMillis();
        System.out.println("Max: " + max);
        System.out.println("Time : " + (time2 - time1));
    }

    public long resolveMax() {
        long max = 0;
        for(int k = 0; k < limit * limit; k++) {
            int rowIdx = k / limit;
            int colIdx = k % limit;
            int diagonalIdx = rowIdx + colIdx;
            int antiDiagonalIdx = rowIdx - colIdx + limit - 1;

            long value = nextElementResolver.apply(this.tab, k + 1, this.limit);

            long sum = Math.max(rowSums[rowIdx] + value, 0);
            rowSums[rowIdx] = sum;
            max = Math.max(max, sum);

            sum = Math.max(columnSums[colIdx] + value, 0);
            columnSums[colIdx] = sum;
            max = Math.max(max, sum);

            sum = Math.max(diagonalSums[diagonalIdx] + value, 0);
            diagonalSums[diagonalIdx] = sum;
            max = Math.max(max, sum);

            sum = Math.max(antiDiagonalSums[antiDiagonalIdx] + value, 0);
            antiDiagonalSums[antiDiagonalIdx] = sum;
            max = Math.max(max, sum);

            tab[rowIdx][colIdx] = value;
        }

        return max;
    }

    private static long nextElement(long[][] tab, int k, int limit) {
        if(k <= 55) {
          return (100003 - 200003L * k + 300007L * k * k * k) % 1000000 - 500000;
        }
        int m2 = (k - 25) / limit;
        int m3 = (k - 56) / limit;
        return (tab[m2][(k - 25) % limit] + tab[m3][(k - 56) % limit] + 1000000) % 1000000 - 500000;
    }

}
