package com.smart.tkl.euler.p686;

import com.smart.tkl.utils.MathUtils;
import java.util.List;

public class PowersOfTwo {

    public static void main(String[] args) {
        long pow = getPower(12,1);
        System.out.println("Power: " + pow);

        pow = getPower(12,2);
        System.out.println("Power: " + pow);

        pow = getPower(123,45);
        System.out.println("Power: " + pow);

        long time1 = System.currentTimeMillis();
        pow = getPower(123,678910);
        long time2 = System.currentTimeMillis();
        System.out.println("Power: " + pow + " found in ms: " + (time2 - time1));
    }

    public static long getPower(int leadingNumber, int n) {
        long pow = 1;
        long occurrence = 0;

        double log2 = Math.log10(2);

        List<Integer> leadingDigits = MathUtils.getDigits(leadingNumber);
        int pow10 = (int)Math.pow(10, leadingDigits.size() - 1);
        double lowerBound = Math.log10((double) leadingNumber / pow10);

        leadingDigits = MathUtils.getDigits(leadingNumber + 1);
        pow10 = (int)Math.pow(10, leadingDigits.size() - 1);
        double upperBound = Math.log10((double) (leadingNumber + 1) / pow10);

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
