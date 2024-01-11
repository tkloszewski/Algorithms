package com.smart.tkl.euler.p199;

import java.text.DecimalFormat;

public class IterativeCirclePacking {

    private final int iterations;

    public IterativeCirclePacking(int iterations) {
        this.iterations = iterations;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        IterativeCirclePacking iterativeCirclePacking = new IterativeCirclePacking(10);
        double gapFraction = iterativeCirclePacking.calcGapFraction();
        long time2 = System.currentTimeMillis();
        System.out.println("Fraction: " + new DecimalFormat("#.########").format(gapFraction));
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public double calcGapFraction() {
        double totalFraction = 0.0;

        double k0 = -1.0;  // outer circle curvature
        double k1 = 1 + 2 * Math.sqrt(3) / 3;
        double area1 = toArea(k1);

        totalFraction += 3 * area1;

        double innerCirclesArea = calcArea(k1, k1, k1, iterations);
        double outerCircleArea = calcArea(k1, k1, k0, iterations);

        totalFraction += innerCirclesArea;
        totalFraction += 3 * outerCircleArea;

        return 1 - totalFraction;
    }

    private double calcArea(double k1, double k2, double k3, int iterationsLeft) {
        double k4 = calcCurvature(k1, k2, k3);
        double result = toArea(k4);

        if(iterationsLeft > 1) {
           result += calcArea(k1, k2, k4, iterationsLeft - 1);
           result += calcArea(k1, k4, k3, iterationsLeft  -1);
           result += calcArea(k2, k4, k3, iterationsLeft - 1);
        }

        return result;
    }

    private static double calcCurvature(double k1, double k2, double k3) {
        return k1 + k2 + k3 + 2 * Math.sqrt(k1 * k2 + k1 * k3 + k2 * k3);
    }

    private static double toArea(double k) {
        return (1.0 / k) * (1.0 / k);
    }

}
