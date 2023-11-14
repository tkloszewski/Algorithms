package com.smart.tkl.euler.p178;

import java.util.Arrays;

public class PandigitalStepNumbers {

    private final int maxLength;

    public PandigitalStepNumbers(int maxLength) {
        this.maxLength = maxLength;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PandigitalStepNumbers pandigitalStepNumbers = new PandigitalStepNumbers(40);
        long[] pandigital = pandigitalStepNumbers.count();
        System.out.println(Arrays.toString(pandigital));
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long[] count() {
        long[] result = new long[maxLength + 1];
        long[] pandigital = countAllPandigital();
        long count = 0;
        for(int length = 10; length <= maxLength; length++) {
            count += pandigital[length];
            result[length] = count;
        }
        return result;
    }

    public long[] countAllPandigital() {
        long[] result = new long[maxLength + 1];
        for(int start = 1; start <= 9; start++) {
            long[] pandigital = countPandigital(start);
            for(int length = 10; length <= maxLength; length++) {
                if(pandigital[length] > 0) {
                    result[length] += pandigital[length];
                }
            }
        }
        return result;
    }

    private long[] countPandigital(int startDigit) {
        long[] pandigital = new long[maxLength + 1];
        long[][][] stepNumbers = new long[maxLength + 1][11][4];

        int startFlag = startDigit == 9 ? 2 : 0;
        stepNumbers[1][startDigit][startFlag] = 1;

        for (int length = 1; length < maxLength; length++) {
            int newLength = length + 1;
            for (int digit = 0; digit <= 9; digit++) {
                for (int flag = startFlag; flag <= 3; flag++) {
                    int newFlag = flag;
                    long count = stepNumbers[length][digit][flag];
                    if (count > 0) {
                        if (digit == 0) {
                            stepNumbers[newLength][1][flag] += count;
                            stepNumbers[newLength][10][newFlag] += count;
                        } else if (digit == 9) {
                            stepNumbers[newLength][8][flag] += count;
                            stepNumbers[newLength][10][newFlag] += count;
                        } else {
                            if (digit + 1 == 9) {
                                newFlag = newFlag | 2;
                            }
                            stepNumbers[newLength][digit + 1][newFlag] += count;
                            stepNumbers[newLength][10][newFlag] += count;

                            newFlag = flag;
                            if (digit - 1 == 0) {
                                newFlag = newFlag | 1;
                            }
                            stepNumbers[newLength][digit - 1][newFlag] += count;
                            stepNumbers[newLength][10][newFlag] += count;
                        }
                    }
                }
            }
        }

        for (int length = 1; length <= maxLength; length++) {
            pandigital[length] = stepNumbers[length][10][3];
        }

        return pandigital;
    }
}
