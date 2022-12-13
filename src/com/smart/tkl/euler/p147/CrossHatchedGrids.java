package com.smart.tkl.euler.p147;

import java.util.ArrayList;
import java.util.List;

public class CrossHatchedGrids {

    private final int maxWidth;
    private final int maxHeight;

    private final long[][] diagonalCache;

    public CrossHatchedGrids(int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        int max = Math.max(this.maxWidth, this.maxHeight);
        this.diagonalCache = new long[max + 1][max + 1];
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long count = new CrossHatchedGrids(47, 43).countAll();
        long time2 = System.currentTimeMillis();
        System.out.println("Count regular and diagonal: " + count);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public long countAll() {
        long result = 0;
        for(int m = 1; m <= maxWidth; m++) {
            for(int n = 1; n <= maxHeight; n++) {
                long regularAndDiagonalCount = countRegularAndDiagonal(m, n);
                result += regularAndDiagonalCount;
            }
        }
        return result;
    }

    private long countRegularAndDiagonal(int m, int n) {
        return countRegular(m, n) + countDiagonal(m, n);
    }

    private long countDiagonal(int m, int n) {
        if(diagonalCache[m][n] != 0) {
            return diagonalCache[m][n];
        }
        long result = doCountDiagonal(m, n);
        diagonalCache[m][n] = result;
        diagonalCache[n][m] = result;
        return result;
    }

    private static long doCountDiagonal(int m, int n) {
        if(m == 1 || n == 1) {
            return Math.max(m, n) - 1;
        }

        int maxBothLength = Math.min(m, n);
        int maxLength = m == n ? 2 * m - 2 : 2 * maxBothLength - 1;
        int oneThickStripes = m + n - 2;
        int maxPartLength = Math.abs(m - n);

        List<Integer> oneThickStripeLengths = getOneThickStripeLengths(oneThickStripes, maxLength);

        long count = 0;
        long oneThickRectanglesCount = countOneThickRectangles(oneThickStripeLengths);
        count += oneThickRectanglesCount;

        for(int stripeWidth = 2; stripeWidth <= maxBothLength; stripeWidth++) {
            long rectInThickerThanOneStripesCount = countForStripeWidth(stripeWidth, oneThickStripeLengths, maxLength, maxPartLength);
            count += rectInThickerThanOneStripesCount;
        }

        return count;
    }

    private static long countRegular(int m, int n) {
        return  (m * n * ((long) m * n  + m + n + 1)) / 4;
    }

    private static long countForStripeWidth(int stripeWidth, List<Integer> singleStripeLengths, int maxLength, int maxPartLength) {
        long result = 0;

        int firstPartLength = (singleStripeLengths.size() - maxPartLength) / 2;
        int lastIndex = getLastWindowIndex(firstPartLength, maxPartLength, stripeWidth) - stripeWidth + 1;

        for(int i = Math.max(stripeWidth / 2 - 1, 0); i <= lastIndex; i++) {
            int endIdx = i + stripeWidth - 1;
            int firstLength = singleStripeLengths.get(i);
            int lastLength = singleStripeLengths.get(endIdx);
            int minLength = Math.min(firstLength, lastLength);

            if(minLength < stripeWidth) {
                continue;
            }

            boolean countOnce = i == singleStripeLengths.size() - endIdx - 1 ||
                    (i >= firstPartLength && endIdx < firstPartLength + maxPartLength);

            int multiplier = countOnce ? 1 : 2;
            int maxStripeLength = getMaxStripeLength(firstLength, minLength, maxLength, i, endIdx, firstPartLength, maxPartLength);
            long count = multiplier * countInStripe(stripeWidth, maxStripeLength);

            result += count;
        }

        return result;
    }

    private static long countOneThickRectangles(List<Integer> singleStripeLengths) {
        long result = 0;
        for(int stripeLength : singleStripeLengths) {
            result += countInStripe(1, stripeLength);
        }
        return result;
    }


    private static long countInStripe(int stripeWidth, int stripeLength) {
        if(stripeWidth == stripeLength) {
            return 1;
        }
        long n = stripeLength - stripeWidth;
        long squaresCount = n + 1;
        long nonSquaresCount = n * (n + 1);
        return squaresCount + nonSquaresCount;
    }

    private static int getMaxStripeLength(int firstStripeLength, int minStripeLength, int maxStripeLength, int stripeStartIdx, int stripeEndIdx, int maxPartStartIdx,
                                          int maxPartSize) {
        if(maxPartSize == 0) {
           return minStripeLength;
        }

        int negativeOffset = (maxStripeLength - firstStripeLength) / 2;
        int maxPartStartCheckIdx = Math.max(maxPartStartIdx, stripeStartIdx);
        int positiveOffset = stripeEndIdx - maxPartStartCheckIdx;

        int offset = positiveOffset - negativeOffset;
        if(offset <= 0) {
           return minStripeLength;
        }
        return minStripeLength - offset;
    }

    private static int getLastWindowIndex(int firstPartLength, int maxPartLength, int stripeWidth) {
        int endMaxPartIdx = firstPartLength + maxPartLength - 1;
        if(stripeWidth <= maxPartLength) {
            return endMaxPartIdx;
        }
        return endMaxPartIdx + (stripeWidth - maxPartLength) / 2;
    }
    private static List<Integer> getOneThickStripeLengths(int size, int maxLength) {
        List<Integer> lengths = new ArrayList<>();
        int length = 0;
        while (true) {
            length += 2;
            int newLength = Math.min(length, maxLength);
            if(newLength == maxLength) {
                int numOfMaxLength = size - 2 * lengths.size();
                for(int i = 0; i < numOfMaxLength; i++) {
                    lengths.add(maxLength);
                }
                break;
            }
            lengths.add(newLength);
        }

        length = length - 2;

        while (length >= 2) {
            lengths.add(length);
            length -= 2;
        }

        return lengths;
    }

}
