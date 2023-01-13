package com.smart.tkl.euler.p686;

public class PowersOfTwo {

    public static void main(String[] args) {
        long pow = getPower(45);
        System.out.println("Power: " + pow);

        /*193092067*/

        pow = getPower(678910);
        System.out.println("Power: " + pow);
    }

    public static long getPower(int n) {
        long pow = 1;
        long occurrence = 0;

        double log2 = Math.log10(2);
        double lowerBound = 0.0899051114393;
        double upperBound = 0.0934216851622;

        while (true) {
            double value = (double) pow * log2;
            double fraction = value - (long)value;
            if(fraction > lowerBound && fraction < upperBound) {
                occurrence++;
                if(occurrence == n) {
                    break;
                }
            }
            pow++;
        }

        return pow;
    }



}
