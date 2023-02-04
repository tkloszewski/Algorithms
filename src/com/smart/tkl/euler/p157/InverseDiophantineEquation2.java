package com.smart.tkl.euler.p157;

public class InverseDiophantineEquation2 {

    private final int limit;

    public InverseDiophantineEquation2(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        InverseDiophantineEquation2 diophantineEquation = new InverseDiophantineEquation2(9);
        int count = diophantineEquation.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public int count() {
        int result = 0;

        long[] powers2 = new long[limit + 1];
        long[] powers5 = new long[limit + 1];
        powers2[0] = 1;
        powers5[0] = 1;
        for(int i = 1; i <= limit; i++) {
            powers2[i] = powers2[i - 1] * 2;
            powers5[i] = powers5[i - 1] * 5;
        }

        for(int n = 1; n <= limit; n++) {
            long powTen = (long)Math.pow(10, n);
            for(int i = 0; i <= n; i++) {
                for(int j = 0; j <= n; j++) {
                    result += countSolutions(powers2[i], powers5[j], powTen);
                }
            }
            for(int i = 1; i <= n; i++) {
                for(int j = 1; j <= n; j++) {
                    result += countSolutions(1, powers2[i] * powers5[j], powTen);
                }
            }
        }

        return result;
    }

    private int countSolutions(long a0, long b0, long powTen) {
        return countDivisors(powTen * (a0 + b0) / (a0 * b0));
    }

    private int countDivisors(long n) {
        int result = 1;

        int step = n % 2 == 0 ? 1 : 2;
        int start = n % 2 == 0 ? 2 : 3;

        for(long i = start; i * i <= n; i += step) {
            int count = 1;
            while (n % i == 0) {
                count++;
                n = n / i;
            }
            result *= count;
        }
        if(n != 1) {
           result *= 2;
        }
        return result;
    }



}
