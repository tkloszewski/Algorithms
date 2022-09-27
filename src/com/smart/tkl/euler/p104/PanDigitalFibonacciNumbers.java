package com.smart.tkl.euler.p104;

import java.math.BigDecimal;

public class PanDigitalFibonacciNumbers {

    private final int digitsCount;

    public static void main(String[] args) {
        PanDigitalFibonacciNumbers panDigitalFibonacciNumbers = new PanDigitalFibonacciNumbers(9);
        long time1 = System.currentTimeMillis();
        int k = panDigitalFibonacciNumbers.find();
        long time2 = System.currentTimeMillis();
        System.out.println("Found pan digital Fibonacci number k = " + k);
        System.out.println("Solution took ms: " + (time2 - time1));
    }

    public PanDigitalFibonacciNumbers(int digitsCount) {
        this.digitsCount = digitsCount;
    }

    public int find() {
        BigDecimal c1 = BigDecimal.valueOf(0.34948500216800940239313055263775);
        BigDecimal phi = BigDecimal.valueOf(0.20898764024997873376927208923756);

        long a = 1;
        long b = 1;
        long cutNum = BigDecimal.TEN.pow(digitsCount).longValue();

        int k = 3;

        while (true) {
            long sumModulo = (a + b) % cutNum;
            if(isPanDigital(sumModulo)) {
                BigDecimal logFib = BigDecimal.valueOf(k).multiply(phi).subtract(c1);
                BigDecimal fractionalPart = logFib.remainder(BigDecimal.ONE);
                double firstTenDigits = Math.pow(10, fractionalPart.doubleValue());
                long firstTenDigitsLong = (long)(firstTenDigits * cutNum / 10);
                if(isPanDigital(firstTenDigitsLong)) {
                   return k;
                }
            }
            a = b;
            b = sumModulo;
            k++;
        }
    }

    private boolean isPanDigital(long value) {
        int[] freq = new int[10];
        int numOfDigits = 0;
        while (value != 0) {
            int digit = (int)value % 10;
            if(++freq[digit] > 1) {
                return false;
            }
            numOfDigits++;
            value = value / 10;
        }
        return freq[0] == 0 && numOfDigits >= digitsCount;
    }
}
