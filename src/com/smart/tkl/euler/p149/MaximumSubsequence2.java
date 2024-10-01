package com.smart.tkl.euler.p149;

import java.util.ArrayList;
import java.util.List;

public class MaximumSubsequence2 {

    private static final long[][] TEST_TAB1 = {
            {-2, 5, 3, 2},
            {9, -6, 5, 1},
            {3, 2, 7, 3},
            {-1, 8, -4, 8}
    };

    private static final long[][] TEST_TAB2 = {
            {-2, -3, -3, -4},
            {-9, -6, -5, -1},
            {-3, -2, -7, -3},
            {-9, -8, -4, -8}
    };

    private static final long[][] TEST_TAB3 = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 1}
    };

    private static final long[][] TEST_TAB4 = {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
    };

    private final int N;
    private final long[][] table;
    private final long[][] maxRowSubsequences;
    private final long[][] maxColumnSubsequences;
    private final long[][] maxDiagonalSubsequences;
    private final long[][] antiDiagonalSums;
    private final AntiDiagonal[] maxAntiDiagonalSubsequence;

    public MaximumSubsequence2(int n, int l, int[] a, int[] f5, int m, int[] b, int[] g5) {
        this(n, getTable(n, l, a, f5, m, b, g5));
    }

    public MaximumSubsequence2(int n, TriFunction<long[][], Integer, Integer, Long> nextElementResolver) {
        this(n, getTable(n, nextElementResolver));
    }

    public MaximumSubsequence2(int n, long[][] table) {
        this.N = n;
        this.table = table;
        this.maxRowSubsequences = new long[n][4];
        this.maxColumnSubsequences = new long[n][4];
        this.maxDiagonalSubsequences = new long[2 * n - 1][4];
        this.antiDiagonalSums = new long[2 * n - 1][];
        this.maxAntiDiagonalSubsequence = new AntiDiagonal[2 * n - 1];

        initAntiDiagonalSums();
    }

    private void initAntiDiagonalSums() {
        for(int rowIdx = 0; rowIdx < N; rowIdx++) {
            int size = rowIdx + 1;
            long[] antiDiagonalSum = new long[size];
            long sum = 0;
            for (int i = rowIdx, j = 0, k = 0; i >= 0; i--, j++, k++) {
                long a = this.table[i][j];
                sum += a;
                antiDiagonalSum[k] = sum;
            }
            antiDiagonalSums[rowIdx] = antiDiagonalSum;
        }
        for(int colIdx = 1; colIdx < N; colIdx++) {
            int size = N - colIdx;
            int index = N + colIdx - 1;
            long[] antiDiagonalSum = new long[size];
            long sum = 0;
            for (int i = N - 1, j = colIdx, k = 0; j < N; i--, j++, k++) {
                long a = this.table[i][j];
                sum += a;
                antiDiagonalSum[k] = sum;
            }
            antiDiagonalSums[index] = antiDiagonalSum;
        }
    }

    public static void main(String[] args) {
        long[][] table1 = getTable(8,
                4, new int[]{81, -89, 45, 6}, new int[]{3, 2, 2, 1, 0},
                3, new int[]{-78, -45, 54}, new int[]{1, 0, 0, 1, 2});

        MaximumSubsequence2 maximumSubsequence2 = new MaximumSubsequence2(4, TEST_TAB1);
        List<Long> maxSquareValues = maximumSubsequence2.getConsecutiveMaxSquareValues();
        System.out.println(maxSquareValues);
        System.out.println("------------------");

        maximumSubsequence2 = new MaximumSubsequence2(4, TEST_TAB2);
        maxSquareValues = maximumSubsequence2.getConsecutiveMaxSquareValues();
        System.out.println(maxSquareValues);
        System.out.println("------------------");

        maximumSubsequence2 = new MaximumSubsequence2(4, TEST_TAB3);
        maxSquareValues = maximumSubsequence2.getConsecutiveMaxSquareValues();
        System.out.println(maxSquareValues);
        System.out.println("------------------");

        maximumSubsequence2 = new MaximumSubsequence2(4, TEST_TAB4);
        maxSquareValues = maximumSubsequence2.getConsecutiveMaxSquareValues();
        System.out.println(maxSquareValues);
        System.out.println("------------------");

        maximumSubsequence2 = new MaximumSubsequence2(8, table1);
        maxSquareValues = maximumSubsequence2.getConsecutiveMaxSquareValues();
        System.out.println(maxSquareValues);
        System.out.println("------------------");

        long time1 = System.currentTimeMillis();
        int limit = 3000;
        long[][] table2 = getTable(limit,
                4, new int[]{81, -89, 45, 6}, new int[]{3, 2, 2, 1, 0},
                3, new int[]{-78, -45, 54}, new int[]{1, 0, 0, 1, 2});

        maximumSubsequence2 = new MaximumSubsequence2(limit, table2);
        maxSquareValues = maximumSubsequence2.getConsecutiveMaxSquareValues();
        long time2 = System.currentTimeMillis();
        System.out.println(maxSquareValues);
        System.out.println("Time in ms: " + (time2 - time1));
        System.out.println("------------------");

        int limit2 = 2000;
        maximumSubsequence2 = new MaximumSubsequence2(limit2, new NextElementResolver());
        maxSquareValues = maximumSubsequence2.getConsecutiveMaxSquareValues();
        System.out.println(maxSquareValues);
        System.out.println("------------------");
    }

    public List<Long> getConsecutiveMaxSquareValues() {
        List<Long> result = new ArrayList<>();

        List<Long> maxSquareForRowValues = getConsecutiveMaxSquareValuesForRows();
        List<Long> maxSquareForColumnValues = getConsecutiveMaxSquareValuesForColumns();
        List<Long> maxSquareForDiagonalValues = getConsecutiveMaxSquareValuesForDiagonals();
        List<Long> maxSquareForAntiDiagonalValues = getConsecutiveMaxSquareValuesForAntiDiagonals();

        for(int i = 0; i < N; i++) {
            long max1 = maxSquareForRowValues.get(i);
            long max2 = maxSquareForColumnValues.get(i);
            long max3 = maxSquareForDiagonalValues.get(i);
            long max4 = maxSquareForAntiDiagonalValues.get(i);
            long max = Math.max(Math.max(max1, max2), Math.max(max3, max4));
            result.add(max);
        }

        return result;
    }

    public List<Long> getConsecutiveMaxSquareValuesForRows() {
        List<Long> result = new ArrayList<>();

        long max = Long.MIN_VALUE;
        for(int rowIdx = 0; rowIdx <= N - 1; rowIdx++) {
            int colIdx = 0;
            //add bottom row to column
            long[] maxRowSubsequence = maxRowSubsequences[rowIdx];
            while (colIdx <= rowIdx) {
                long a = table[rowIdx][colIdx];
                long newMax = setAndGetNextMaxSubsequenceValue(maxRowSubsequence, a);
                max = Math.max(max, newMax);
                colIdx++;
            }
            //add right column
            colIdx = rowIdx;
            for(int addRowIdx = rowIdx - 1; addRowIdx >= 0; addRowIdx--) {
                long a = table[addRowIdx][colIdx];
                maxRowSubsequence = maxRowSubsequences[addRowIdx];
                long newMax = setAndGetNextMaxSubsequenceValue(maxRowSubsequence, a);
                max = Math.max(max, newMax);
            }
            result.add(max);
        }

        return result;
    }

    private List<Long> getConsecutiveMaxSquareValuesForColumns() {
        List<Long> result = new ArrayList<>();

        long max = Long.MIN_VALUE;
        for(int rowIdx = 0; rowIdx <= N - 1; rowIdx++) {
            int colIdx = 0;
            //add bottom row to column excluding last element
            while (colIdx < rowIdx) {
                long a = table[rowIdx][colIdx];
                long[] maxColumnSubsequence = maxColumnSubsequences[colIdx];
                long newMax = setAndGetNextMaxSubsequenceValue(maxColumnSubsequence, a);
                max = Math.max(max, newMax);
                colIdx++;
            }
            //add right column
            colIdx = rowIdx;
            long[] maxColumnSubsequence = maxColumnSubsequences[colIdx];
            for(int rowIdx2 = 0; rowIdx2 <= rowIdx; rowIdx2++) {
                long a = table[rowIdx2][colIdx];
                long newMax = setAndGetNextMaxSubsequenceValue(maxColumnSubsequence, a);
                max = Math.max(max, newMax);
            }
            result.add(max);
        }

        return result;
    }

    private List<Long> getConsecutiveMaxSquareValuesForDiagonals() {
        List<Long> result = new ArrayList<>();

        long max = Long.MIN_VALUE;
        for(int rowIdx = 0; rowIdx <= N - 1; rowIdx++) {
            int colIdx = 0;
            //add bottom row
            while (colIdx <= rowIdx) {
                int index = N - 1 - rowIdx + colIdx;
                long a = table[rowIdx][colIdx];
                long newMax = setAndGetNextDiagonalMaxSubarrayValue(index, a);
                max = Math.max(max, newMax);
                colIdx++;
            }
            //add right column
            colIdx = rowIdx;
            for(int addRowIdx = rowIdx - 1; addRowIdx >= 0; addRowIdx--) {
                int index = N - 1 + rowIdx - addRowIdx;
                long a = table[addRowIdx][colIdx];
                long newMax = setAndGetNextDiagonalMaxSubarrayValue(index, a);
                max = Math.max(max, newMax);
            }
            result.add(max);
        }

        return result;
    }

    private long setAndGetNextDiagonalMaxSubarrayValue(int index, long a) {
        long[] maxDiagonalSubsequence = maxDiagonalSubsequences[index];
        if(maxDiagonalSubsequence[3] == 0) {
            maxDiagonalSubsequence[0] = a;
            maxDiagonalSubsequence[1] = a;
            maxDiagonalSubsequence[2] = Math.min(0, a);
            maxDiagonalSubsequence[3] = 1;
            return a;
        }
        else {
            return setAndGetNextMaxSubsequenceValue(maxDiagonalSubsequence, a);
        }
    }

    private List<Long> getConsecutiveMaxSquareValuesForAntiDiagonals() {
        List<Long> result = new ArrayList<>();

        long max = Long.MIN_VALUE;
        for(int rowIdx = 0; rowIdx <= N - 1; rowIdx++) {
            int colIdx = 0;
            //add bottom row
            while (colIdx <= rowIdx) {
                AntiDiagonal antiDiagonal = prependToAntiDiagonal(rowIdx, colIdx);
                max = Math.max(max, antiDiagonal.max);
                colIdx++;
            }
            //add right column
            colIdx = rowIdx;
            for(int addRowIdx = rowIdx - 1; addRowIdx >= 0; addRowIdx--) {
                AntiDiagonal antiDiagonal = appendToAntiDiagonal(addRowIdx, colIdx);
                max = Math.max(max, antiDiagonal.max);
            }
            result.add(max);
        }

        return result;
    }

    private AntiDiagonal prependToAntiDiagonal(int rowIdx, int colIdx) {
        long a = this.table[rowIdx][colIdx];
        int index = rowIdx + colIdx;
        AntiDiagonal antiDiagonal = this.maxAntiDiagonalSubsequence[index];
        if(antiDiagonal == null) {
            int offset = Math.min(this.N - rowIdx - 1, colIdx);
            antiDiagonal = new AntiDiagonal(a, offset);
            this.maxAntiDiagonalSubsequence[index] = antiDiagonal;
        }
        else {
            prependToAntiDiagonal(antiDiagonal, a);
        }
        return antiDiagonal;
    }

    private static void prependToAntiDiagonal(AntiDiagonal antiDiagonal, long a) {
        long sum = antiDiagonal.sum + a;
        long maxSum = antiDiagonal.maxSum;
        long min = antiDiagonal.min;
        long max = antiDiagonal.max;
        long preMaxSum = antiDiagonal.preMaxSum;
        int i = antiDiagonal.i;
        int j = antiDiagonal.j;
        int offset = antiDiagonal.offset;
        int minPos = antiDiagonal.minPos;
        int maxPos = antiDiagonal.maxPos;

        if(preMaxSum == 0 && a > 0) {
            if(max > 0) {
                max = a + max;
                i = 0;
                j++;
            }
            else {
                max = a;
                i = 0;
                j = 0;
            }
        }
        else if(a > 0 && maxPos != j && maxSum > 0 && a + maxSum > a + preMaxSum + max && a + maxSum > max) {
            max = a + maxSum;
            preMaxSum = 0;
            i = 0;
            j = maxPos;
            j++;
        }
        else if(a > 0 && max > 0 && a + preMaxSum > 0) {
            if(a + preMaxSum + max > a) {
                max = a + preMaxSum + max;
                i = 0;
                j++;
            }
            else {
                max = a;
                i = 0;
                j = 0;
            }
            preMaxSum = 0;
        }
        else if(a > max) {
            max = a;
            i = 0;
            j = 0;
            preMaxSum = 0;
        }
        else {
            preMaxSum += a;
            i++;
            j++;
        }

        if(a > 0) {
            if(a + min > 0) {
                min = 0;
                minPos = -1;
            }
            else {
                min = a + min;
                minPos++;
            }
        }
        else {
            if(min == 0) {
                minPos = 0;
                min = a;
            }
            else {
                minPos++;
                min = a + min;
            }
        }

        if(maxSum > 0) {
            maxSum = maxSum + a;
            maxPos++;
        }
        else {
            maxSum = a;
            maxPos = 0;
        }

        antiDiagonal.max = max;
        antiDiagonal.min = min;
        antiDiagonal.preMaxSum = preMaxSum;
        antiDiagonal.sum = sum;
        antiDiagonal.maxSum = maxSum;
        antiDiagonal.i = i;
        antiDiagonal.j = j;
        antiDiagonal.minPos = minPos;
        antiDiagonal.maxPos = maxPos;
        antiDiagonal.offset = Math.max(0, offset - 1);
        antiDiagonal.length++;
    }

    private AntiDiagonal appendToAntiDiagonal(int rowIdx, int colIdx) {
        long a = this.table[rowIdx][colIdx];
        int index = rowIdx + colIdx;
        AntiDiagonal antiDiagonal = this.maxAntiDiagonalSubsequence[index];
        long[] antiDiagonalSum = antiDiagonalSums[index];
        appendToAntiDiagonal(antiDiagonal, a, antiDiagonalSum);
        return antiDiagonal;
    }

    private static void appendToAntiDiagonal(AntiDiagonal antiDiagonal, long a, long[] antiDiagonalSums) {
        long sum = antiDiagonal.sum + a;
        long maxSum = antiDiagonal.maxSum;
        long preMaxSum = antiDiagonal.preMaxSum;
        long min = antiDiagonal.min;
        long max = antiDiagonal.max;
        int i = antiDiagonal.i;
        int j = antiDiagonal.j;
        int minPos = antiDiagonal.minPos;
        int maxPos = antiDiagonal.maxPos;
        int offset = antiDiagonal.offset;

        if(sum > maxSum) {
            maxSum = sum;
            maxPos = antiDiagonal.length;
        }

        if(max < sum - min) {
            max = sum - min;
            j = antiDiagonal.length;
            i = antiDiagonal.minPos + 1;
            long offsetSum = offset > 0 ? antiDiagonalSums[offset - 1] : 0;
            preMaxSum = i > 0 ? antiDiagonalSums[offset + i - 1] - offsetSum: 0;
        }

        if(sum < min) {
            min = sum;
            minPos = antiDiagonal.length;
        }

        antiDiagonal.max = max;
        antiDiagonal.sum = sum;
        antiDiagonal.maxSum = maxSum;
        antiDiagonal.preMaxSum = preMaxSum;
        antiDiagonal.i = i;
        antiDiagonal.j = j;
        antiDiagonal.minPos = minPos;
        antiDiagonal.maxPos = maxPos;
        antiDiagonal.min = min;
        antiDiagonal.length++;
    }

    private static long setAndGetNextMaxSubsequenceValue(long[] lineStat, long a) {
        long max = lineStat[0];
        long s = lineStat[1];
        long min = lineStat[2];
        long length = lineStat[3];
        s += a;
        if(length == 0) {
            max = a;
            lineStat[0] = max;
            lineStat[1] = s;
            lineStat[2] = Math.min(0, a);
            lineStat[3] = 1;
        }
        else {
            max = Math.max(max, s - min);
            min = Math.min(min, s);
            lineStat[0] = max;
            lineStat[1] = s;
            lineStat[2] = min;
            lineStat[3] = length + 1;
        }
        return max;
    }

    private static long[][] getTable(int N, int l, int[] a, int[] f5, int m, int[] b, int[] g5) {
        long[][] table = new long[N][N];
        PseudoRandomGenerator pseudoRandomGenerator = new PseudoRandomGenerator(l, a, f5, m, b, g5);
        for(int k = 0; k < N * N; k++) {
            int row = k / N;
            int col = k % N;
            long value = pseudoRandomGenerator.apply(table, k, N);
            table[row][col] = value;
        }
        return table;
    }

    private static long[][] getTable(int N, TriFunction<long[][], Integer, Integer, Long> nextElementResolver) {
        long[][] table = new long[N][N];
        for(int k = 0; k < N * N; k++) {
            int row = k / N;
            int col = k % N;
            long value = nextElementResolver.apply(table, k + 1, N);
            table[row][col] = value;
        }
        return table;
    }

    private static class AntiDiagonal {
        long max;
        int i, j;
        long maxSum;
        long preMaxSum;
        long sum;
        long min;
        int minPos;
        int maxPos;
        int offset;
        int length;

        public AntiDiagonal(long a, int offset) {
            this.max = a;
            this.sum = a;
            this.min = Math.min(0, a);
            this.maxSum = a;
            this.preMaxSum = 0;
            this.i = this.j = 0;
            this.minPos = a < 0 ? 0 : -1;
            this.maxPos = 0;
            this.offset = offset;
            this.length = 1;
        }

        @Override
        public String toString() {
            return "AntiDiagonal{" +
                    "max=" + max +
                    ", i=" + i +
                    ", j=" + j +
                    ", maxSum=" + maxSum +
                    ", preMaxSum=" + preMaxSum +
                    ", sum=" + sum +
                    ", min=" + min +
                    ", minPos=" + minPos +
                    ", maxPos=" + maxPos +
                    ", offset=" + offset +
                    ", length=" + length +
                    '}';
        }
    }

}
