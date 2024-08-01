package com.smart.tkl.euler.p138;

import java.util.ArrayList;
import java.util.List;

public class IsoscelesTriangle {

    private final long triangleCount;

    public IsoscelesTriangle(long triangleCount) {
        this.triangleCount = triangleCount;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        IsoscelesTriangle isoscelesTriangle = new IsoscelesTriangle(12);
        long sum = isoscelesTriangle.calcSumOfLegs();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum:= " + sum);
        System.out.println("Solution found in ms: " + (time2 - time1));
    }

    /*5L^2 -1 = y^2*/
    public long calcSumOfLegs() {
        long sum = 0;
        long l = 17, y = 38;
        long currentTrianglesCount = 0;

        List<Long> values = new ArrayList<>();

        while (currentTrianglesCount < this.triangleCount) {
            if(y % 5 == 2 || y % 5 == 3) {
                sum += l;
                currentTrianglesCount++;
               values.add(l);
            }
            long prevL = l;
            l = 9 * l + 4 * y;
            y = 20 * prevL + 9 * y;
        }

        System.out.println(values);

        return sum;
    }
}
