package com.smart.tkl.euler.p129;

import com.smart.tkl.utils.MathUtils;

public class RepUnitDivisibility {

    private final int limit;
    private final int startNumber;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        RepUnitDivisibility repUnitDivisibility = new RepUnitDivisibility(1000000, 1000001);
        long number = repUnitDivisibility.solve();
        long time2 = System.currentTimeMillis();
        System.out.println("Number: " + number);
        System.out.println("Solution took in ms: " + (time2 - time1));
    }

    public RepUnitDivisibility(int limit, int startNumber) {
        this.limit = limit;
        this.startNumber = startNumber;
    }

    public long solve() {
        long number = this.startNumber, k = 1;
        while (k <= this.limit) {
            k = 1;
            long n = 1;
            while (n != 0) {
                n = (MathUtils.moduloMultiply(n, 10, number) + 1) % number;
                k++;
            }
            if(k <= this.limit) {
                number += 2;
                if(number % 5 == 0) {
                    number += 2;
                }
            }
        }
        return number;
    }
}
