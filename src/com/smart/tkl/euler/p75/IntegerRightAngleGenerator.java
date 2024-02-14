package com.smart.tkl.euler.p75;

import com.smart.tkl.lib.utils.MathUtils;

public class IntegerRightAngleGenerator {

    private final int limit;

    public IntegerRightAngleGenerator(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        IntegerRightAngleGenerator generator = new IntegerRightAngleGenerator(5000000);
        int[] freq = generator.calcValues();
        System.out.println(freq[12]);
        System.out.println(freq[50]);
        System.out.println(freq[5000000]);
    }

    public int[] calcValues() {
        int maxM = ((int)Math.sqrt(1 + 2 * limit) - 1) / 2;
        int[] perimeters = new int[limit + 1];
        for(int m = 2; m <= maxM; m++) {
            for(int n = 1; n < m; n++) {
                if((m + n) % 2 == 1 && MathUtils.GCD(m, n) == 1) {
                    int a = m * m - n * n;
                    int b = 2 * m * n;
                    int c = m * m + n * n;
                    int perimeter = a + b + c;
                    int maxK = limit / perimeter;
                    for(int k = 1; k <= maxK; k++) {
                        perimeters[k * perimeter]++;
                    }
                }
            }
        }

        int[] result = new int[limit + 1];
        int count = 0;
        for(int perimiter = 12; perimiter <= limit; perimiter++) {
            if(perimeters[perimiter] == 1) {
               count++;
            }
            result[perimiter] = count;
        }
        return result;
    }
}
