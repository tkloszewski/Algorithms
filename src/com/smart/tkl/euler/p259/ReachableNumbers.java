package com.smart.tkl.euler.p259;

import java.util.TreeSet;

public class ReachableNumbers {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long sum = new ReachableNumbers().sum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long sum() {
        long result = 0;
        String digits = "123456789";
        TreeSet<ReachableFraction> allFractions = findFractions(digits);

        for(ReachableFraction fraction : allFractions) {
            if(!fraction.isGreaterThanZero()) {
               break;
            }
            result += fraction.toIntegerValue();
        }

        return result;
    }

    private TreeSet<ReachableFraction> findFractions(String digits) {
        TreeSet<ReachableFraction> result = new TreeSet<>();
        result.add(new ReachableFraction(Integer.parseInt(digits)));

        for(int splitIndex = 1; splitIndex < digits.length(); splitIndex++) {
            String leftSubstring = digits.substring(0, splitIndex);
            String rightSubstring = digits.substring(splitIndex);
            TreeSet<ReachableFraction> leftBranch = findFractions(leftSubstring);
            TreeSet<ReachableFraction> rightBranch = findFractions(rightSubstring);

            for(ReachableFraction fraction1 : leftBranch) {
                for(ReachableFraction fraction2 : rightBranch) {
                    result.add(fraction1.add(fraction2));
                    result.add(fraction1.subtract(fraction2));
                    result.add(fraction1.multiply(fraction2));
                    if(fraction2.numerator != 0) {
                       result.add(fraction1.divide(fraction2));
                    }
                }
            }
        }

        return result;
    }
}
