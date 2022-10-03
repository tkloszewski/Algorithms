package com.smart.tkl.euler.p112;

public class BouncyNumberSolver {

    private final int percentageLimit;

    public BouncyNumberSolver(int percentageLimit) {
        this.percentageLimit = percentageLimit;
    }

    public static void main(String[] args) {
        BouncyNumberSolver bouncyNumberSolver = new BouncyNumberSolver(50);
        long lastNumber = bouncyNumberSolver.findLastNumber();
        System.out.println("Last number for 50% limit: " + lastNumber);

        bouncyNumberSolver = new BouncyNumberSolver(90);
        lastNumber = bouncyNumberSolver.findLastNumber();
        System.out.println("Last number for 90% limit: " + lastNumber);

        bouncyNumberSolver = new BouncyNumberSolver(99);
        lastNumber = bouncyNumberSolver.findLastNumber();
        System.out.println("Last number for 99% limit: " + lastNumber);
    }

    public long findLastNumber() {
        long bouncyCount = 0;
        long nonBouncyCount = 100;

        long percentage = 0;
        long number = 100;
        while (percentage < percentageLimit) {
            number++;
            if(isBouncyNumber(number)) {
               bouncyCount++;
            }
            else {
                nonBouncyCount++;
            }
            percentage = (100 * bouncyCount) / (bouncyCount + nonBouncyCount);
        }
        return number;
    }

    private boolean isBouncyNumber(long n) {
        String sNum = String.valueOf(n);
        Boolean isIncreasing = null;

        for(int i = 0, j = sNum.length() - 1; i < j; i++, j--) {
            if(sNum.charAt(i) < sNum.charAt(i + 1)) {
               if(isIncreasing == null) {
                  isIncreasing = true;
               }
               else if(!isIncreasing) {
                  return true;
               }
            }
            else if(sNum.charAt(i) > sNum.charAt(i + 1)) {
                if(isIncreasing == null) {
                    isIncreasing = false;
                }
                else if(isIncreasing) {
                    return true;
                }
            }
            if(sNum.charAt(j - 1) < sNum.charAt(j)) {
                if(isIncreasing == null) {
                    isIncreasing = true;
                }
                else if(!isIncreasing) {
                    return true;
                }
            }
            else if(sNum.charAt(j - 1) > sNum.charAt(j)) {
                if(isIncreasing == null) {
                    isIncreasing = false;
                }
                else if(isIncreasing) {
                    return true;
                }
            }
        }
        return false;
    }
}
