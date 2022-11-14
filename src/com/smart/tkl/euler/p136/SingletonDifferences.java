package com.smart.tkl.euler.p136;

import com.smart.tkl.euler.p135.SameDifferences;

public class SingletonDifferences {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SameDifferences sameDifferences = new SameDifferences(50000000, 1);
        int count = sameDifferences.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Number of n values with single distinct solutions: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }
}
