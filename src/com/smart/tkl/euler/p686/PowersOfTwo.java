package com.smart.tkl.euler.p686;

public class PowersOfTwo {

    public static void main(String[] args) {
        long pow = getPower(45);
        System.out.println("Power: " + pow);
        pow = getPower(678910);
        System.out.println("Power: " + pow);
    }

    public static long getPower(int n) {
        long pow = 1;
        long occurrence = 0;

        double log2 = Math.log10(2);
        /*0.0899051114393979318044397532233*/
        double lowerBound = 0.089905111;
        /*0.0934216851622350700941818895674*/
        double upperBound = 0.093421685;

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
