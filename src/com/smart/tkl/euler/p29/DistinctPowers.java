package com.smart.tkl.euler.p29;

public class DistinctPowers {

    private final int limit;

    public DistinctPowers(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        DistinctPowers distinctPowers = new DistinctPowers(100000);
        long count = distinctPowers.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Distinct powers: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        long repeated = countRepeated();
        return (long) (limit - 1) * (limit - 1) - repeated;
    }

    private long countRepeated() {
        long totalRepeated = 0;
        boolean[] used = new boolean[limit + 1];

        for(int n = 2; n <= limit; n++) {
            long repeated = 0;
            if(used[n]) {
                continue;
            }
            int maxExp = maxExp(n, limit);
            if(maxExp == 1) {
               break;
            }
            boolean[] sieve = new boolean[maxExp * limit + 1];
            for(int exp = 1; exp <= maxExp; exp++) {
                for(int p = 2 * exp; p <= exp * limit; p += exp) {
                    if(sieve[p]) {
                        repeated++;
                    }
                    else {
                        sieve[p] = true;
                    }
                }
                int value = (int)Math.pow(n, exp);
                used[value] = true;
            }
            totalRepeated += repeated;
        }
        return totalRepeated;
    }

    private static int maxExp(long base, long limit) {
        int exp = 1;
        long pow = base;
        while (pow * base <= limit) {
            exp++;
            pow *= base;
        }
        return exp;

    }
}
