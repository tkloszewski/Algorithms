package com.smart.tkl.euler.p112;

public class BouncyNumberSolver {

    private final int percentageLimit;

    public BouncyNumberSolver(int percentageLimit) {
        this.percentageLimit = percentageLimit;
    }

    public static void main(String[] args) {
        BouncyNumberSolver bouncyNumberSolver = new BouncyNumberSolver(500);
        long lastNumber = bouncyNumberSolver.findLastNumber();
        System.out.println("Last number for 50% limit: " + lastNumber);

        bouncyNumberSolver = new BouncyNumberSolver(900);
        lastNumber = bouncyNumberSolver.findLastNumber();
        System.out.println("Last number for 90% limit: " + lastNumber);

        bouncyNumberSolver = new BouncyNumberSolver(990);
        long time1 = System.currentTimeMillis();
        lastNumber = bouncyNumberSolver.findLastNumber();
        long time2 = System.currentTimeMillis();
        System.out.println("Last number for 99% limit: " + lastNumber);
        System.out.println("Found solution in ms: " + (time2 - time1));

        int nonBouncyCount = 0;
        for(int i = 1; i < 1000; i++) {
            if (!bouncyNumberSolver.isBouncyNumber(i)) {
               nonBouncyCount++;
            }
        }
        System.out.println("Non bouncy count: " + nonBouncyCount);

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
            percentage = (1000 * bouncyCount) / (bouncyCount + nonBouncyCount);
        }
        System.out.println("Bouncy count: " + bouncyCount);
        System.out.println("Non bounyc count: " + nonBouncyCount);
        return number;
    }

    private boolean isBouncyNumber(long n) {
        if(n < 100) {
           return false;
        }
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
