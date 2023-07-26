package com.smart.tkl.euler.p64;

import com.smart.tkl.lib.utils.PeriodicFraction;
import com.smart.tkl.lib.utils.SquareRootPeriodicFractionGenerator;

public class OddPeriodSquareRoots {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int count = oddPeriodSquareRoots(10000);
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count + " Time in ms: " + (time2 - time1));
    }

    private static int oddPeriodSquareRoots(int upperBound) {
        int result = 0;
        for(int n = 2; n <= upperBound; n++) {
            PeriodicFraction periodicFraction = new SquareRootPeriodicFractionGenerator(n).generate();
            int periodSize = periodicFraction.getPeriodSize();
            if(periodSize % 2 == 1) {
                result++;
            }
        }
        return result;
    }
}
