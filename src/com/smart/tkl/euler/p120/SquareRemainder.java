package com.smart.tkl.euler.p120;

public class SquareRemainder {

    private final int minA;
    private final int maxA;

    public SquareRemainder(int minA, int maxA) {
        this.minA = minA;
        this.maxA = maxA;
    }

    public static void main(String[] args) {
        SquareRemainder squareRemainder = new SquareRemainder(3, 1000);
        long time1 = System.currentTimeMillis();
        int sum = squareRemainder.sumOfMaxRemainders();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Solution took in ms: " + (time2 - time1));


        System.out.println(maxRemainder(6));
    }

    public int sumOfMaxRemainders() {
        int sum = 0;
        for(int a = minA; a <= maxA; a++) {
            int maxRemainder = maxRemainder(a);
            sum += maxRemainder;
        }
        return sum;
    }

    private static int maxRemainder(int a) {
        boolean cycleDetected = false;
        int value = 2 * a;
        int square = a * a;
        int firstRemainder = value % square;
        int maxRemainder = Math.max(firstRemainder, 2);
        while (!cycleDetected) {
            value += 4 * a;
            int remainder = value % square;
            if(remainder > maxRemainder) {
               maxRemainder = remainder;
            }
            cycleDetected = remainder == firstRemainder;
        }
        return maxRemainder;
    }
}
