package com.smart.tkl.euler.p166;

import java.util.ArrayList;
import java.util.List;

public class CrissCross {

    private final int maxDigit;

    public CrissCross(int maxDigit) {
        this.maxDigit = maxDigit;
    }

    public CrissCross() {
        this(9);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        CrissCross crissCross = new CrissCross();
        int count = crissCross.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public int count() {
        int result = 0;

        List<List<int[]>> allCombinations = new ArrayList<>();
        for(int sum = 0; sum <= 4 * this.maxDigit; sum++) {
            List<int[]> combinations = new CombinationsFinder(sum).findCombinations();
            allCombinations.add(combinations);
        }

        for(List<int[]> sumResults : allCombinations) {
            for(int[] sum1 : sumResults) {
                for(int[] sum2: sumResults) {
                    int a = sum1[0];
                    int b = sum1[1];
                    int c = sum1[2];
                    int d = sum1[3];
                    int e = sum2[0];
                    int f = sum2[1];
                    int g = sum2[2];
                    int h = sum2[3];

                    for(int j = 0; j <= this.maxDigit; j++) {
                        int k = a + b + c + d - f - g - j;
                        if(k < 0) {
                           break;
                        }
                        if(k > this.maxDigit) {
                           continue;
                        }

                        int p = b + c + d - f - k;
                        if(p > this.maxDigit) {
                           break;
                        }
                        if(p < 0) {
                           continue;
                        }

                        int o = a + b + d - g - k;
                        if(o > this.maxDigit) {
                            break;
                        }
                        if(o < 0) {
                            continue;
                        }

                        int n = a + c + d - f - j;
                        if(n < 0) {
                            break;
                        }
                        if(n > this.maxDigit) {
                            continue;
                        }

                        int m = a + b + c - g - j;
                        if(m < 0) {
                            break;
                        }
                        if(m > this.maxDigit) {
                            continue;
                        }

                        int l = a - d + f - h + k;
                        if(isNotValidDigit(l)) {
                           continue;
                        }

                        int i = -a + d - e + g + j;
                        if(isNotValidDigit(i)) {
                           continue;
                        }

                        result++;
                    }
                }
            }
        }
        return result;
    }

    private boolean isNotValidDigit(int digit) {
        return digit < 0 || digit > this.maxDigit;
    }
}
