package com.smart.tkl.euler.p39;

import java.util.Arrays;

public class IntegerRightAngledTriangles {

    public static void main(String[] args) {
        int[] perimeters = getPerimeters(100);
        System.out.println(Arrays.toString(perimeters));
    }

    public static int[] getPerimeters(int limit) {
        int[] perimeters = new int[limit + 1];
        int[] freq = new int[limit + 1];

        double delta = 4 + 8 * limit;
        int mMax = (int)((-2 + Math.sqrt(delta)) / 4);

        for(int m = 2; m <= mMax; m++) {
            for(int n = 1; n < m; n++) {
                if((m + n) % 2 == 1 && GCD(m, n) == 1) {
                   int a = m * m - n * n;
                   int b = 2 * m * n;
                   int c = m * m + n * n;
                   int p = a + b + c;
                   int kMax = limit / p;
                   for(int k = 1; k <= kMax; k++) {
                       freq[k * p]++;
                   }
                }
            }
        }

        //System.out.println(Arrays.toString(freq));

        int maxSolutions = 0, minPerimeter = 12;
        for(int p = minPerimeter; p <= limit; p++) {
            if(freq[p] > maxSolutions) {
                minPerimeter = p;
                maxSolutions = freq[p];
            }
            perimeters[p] = minPerimeter;
        }

        return perimeters;
    }

    private static int GCD(int a, int b) {
        if(b == 0) {
           return a;
        }
        return GCD(b, a % b);
    }
}
