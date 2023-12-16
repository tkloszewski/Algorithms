package com.smart.tkl.euler.p190;

public class MaximumWeightedProduct {

    private final int maxM;

    public MaximumWeightedProduct(int maxM) {
        this.maxM = maxM;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int maxM = 15;
        MaximumWeightedProduct maximumWeightedProduct = new MaximumWeightedProduct(maxM);
        long sum = maximumWeightedProduct.calcSum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long calcSum() {
        long sum = 0;
        for(int m = 2; m <= maxM; m++) {
            sum += getMaximum(m);
        }
        return sum;
    }

    private static long getMaximum(int m) {
        double result = 1;
        double x = 2.0 / (1 + m);

        for(int i = 1; i <= m; i++) {
           double xi = Math.pow(i * x, i);
           result *= xi;
        }

        return (long) result;
    }


}
