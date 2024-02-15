package com.smart.tkl.euler.p78;

import java.util.Arrays;

public class CoinPartitions2 {

    private final int modValue;
    private final int limit;
    private final long[] partitionMemo;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int mod = (int)Math.pow(10, 9) + 7;
        int limit = 60000;
        CoinPartitions2 coinPartitions2 = new CoinPartitions2(mod, limit);
        long[] partitions = coinPartitions2.allPartitions();
        long time2 = System.currentTimeMillis();
        System.out.println(Arrays.toString(partitions));
        System.out.println("##############################################");
        System.out.println(Arrays.toString(countPartitions(limit, mod)));
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public CoinPartitions2(int modValue, int limit) {
        this.modValue = modValue;
        this.limit = limit;
        partitionMemo = new long[limit + 1];
        partitionMemo[0] = 1;
    }

    public static long[] countPartitions(int limit, long mod) {
        long[] partitions = new long[ limit + 1];
        partitions[0] = 1;

        for(int number = 1; number <= limit; number++) {
            for(int j = number; j <= limit; j++) {
                partitions[j] = (partitions[j] + partitions[j - number]) % mod;
            }
        }

        return partitions;
    }

    public long[] allPartitions() {
        long[] partitions = new long[limit + 1];
        for(int i = 1; i <= limit; i++) {
            partitions[i] = modPartition(i);
        }
        return partitions;
    }

    private long modPartition(int n) {
        if(partitionMemo[n] != 0) {
           return partitionMemo[n];
        }

        if(n == 0) {
           return 1;
        }

        long result = 0;

        long sign = 1;
        long index = n;
        for(long k = 1; index > 0; k++) {
            long n1 = n - toPentagonal(k);
            long n2 = n - toPentagonal(-k);

            long value1 = n1 >= 0 ? modPartition((int)n1) : 0;
            long value2 = n2 >= 0 ? modPartition((int)n2) : 0;

            long sum = (value1 + value2) % modValue;
            result = (result + sign * (sum)) % modValue;
            if(result < 0) {
               result = modValue + result;
            }

            sign *= -1;
            index = n2;
        }

        partitionMemo[(int)n] = result;

        return result;
    }

    private long toPentagonal(long k) {
        long pentagonal = (k * (3 * k - 1)) / 2;
        return pentagonal % modValue;
    }

}
