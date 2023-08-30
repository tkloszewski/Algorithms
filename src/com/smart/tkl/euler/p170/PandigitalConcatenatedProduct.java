package com.smart.tkl.euler.p170;

import com.smart.tkl.lib.utils.MathUtils;

public class PandigitalConcatenatedProduct {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PandigitalProduct product = new PandigitalConcatenatedProduct().findMaxPandigitalProduct();
        long time2 = System.currentTimeMillis();
        System.out.println("Found concatenated pandigital: " + product);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public PandigitalProduct findMaxPandigitalProduct() {
        long pow = (long)Math.pow(10, 9);
        return findMaxPandigital(0, pow, new int[10], 0);
    }

    private PandigitalProduct findMaxPandigital(long value, long pow, int[] used, int pos) {
        int minDigit = pos > 0 ? 0 : 1;
        if (pos < 9) {
            for(int digit = 9; digit >= minDigit; digit--) {
                if(used[digit] == 1) {
                    continue;
                }
                used[digit] = 1;
                PandigitalProduct result = findMaxPandigital(value + pow * digit, pow / 10, used, pos + 1);
                if(result != null) {
                    return result;
                }
                used[digit] = 0;
            }
        }
        else {
            for(int digit = 9; digit >= minDigit; digit--) {
                if (used[digit] == 1) {
                    continue;
                }
                long pandigitalValue = digit + value;
                PandigitalProduct pandigitalProduct = getPandigitalProduct(pandigitalValue);
                if(pandigitalProduct != null) {
                    return pandigitalProduct;
                }
            }
        }
        return null;
    }

    private static class PandigitalProduct {
        long factor, leftInput, rightInput, pandigital;

        public PandigitalProduct(long factor, long leftInput, long rightInput, long pandigital) {
            this.factor = factor;
            this.leftInput = leftInput;
            this.rightInput = rightInput;
            this.pandigital = pandigital;
        }

        @Override
        public String toString() {
            return "PandigitalProduct{" +
                    "factor=" + factor +
                    ", leftInput=" + leftInput +
                    ", rightInput=" + rightInput +
                    ", pandigital=" + pandigital +
                    '}';
        }
    }

    private static PandigitalProduct getPandigitalProduct(long pandigital) {
        long pow = 10;
        for(int splitSize = 1; splitSize <= 9; splitSize++) {
            long right = pandigital % pow;
            long left = pandigital / pow;
            long gcd = MathUtils.GCD(left, right);
            for(long factor = 2; factor <= gcd; factor++) {
                int[] used = new int[10];
                if(right % factor == 0 && left % factor == 0 && isPandigital(factor, used)) {
                    long leftDiv = left / factor;
                    long rightDiv = right / factor;
                    boolean inputsArePandigital = areInputsPandigital(factor, leftDiv, rightDiv, used);
                    if(inputsArePandigital) {
                        return new PandigitalProduct(factor, leftDiv, rightDiv, pandigital);
                    }
                }
            }
            pow *= 10;
        }
        return null;
    }

    private static boolean areInputsPandigital(long factor, long leftDiv, long rightDiv, int[] used) {
        return hasConcatenation10Digits(factor, leftDiv, rightDiv) &&
                isPandigital(leftDiv, used) && isPandigital(rightDiv, used);
    }

    private static boolean isPandigital(long number, int[] used) {
        while (number > 0) {
            int digit = (int)(number % 10);
            if(used[digit] > 0) {
                return false;
            }
            used[digit]++;
            number = number / 10;
        }
        return true;
    }

    private static boolean hasConcatenation10Digits(long factor, long left, long right) {
        return String.valueOf(factor).length() +
                String.valueOf(left).length() +
                String.valueOf(right).length() == 10;
    }

}

