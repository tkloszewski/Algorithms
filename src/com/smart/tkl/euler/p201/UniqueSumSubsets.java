package com.smart.tkl.euler.p201;

public class UniqueSumSubsets {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();

        int[] tab = new int[100];
        for(int i = 1; i <= 100; i++) {
            tab[i - 1] = i * i;
        }

        long sum = sum(tab, 50);
        long time2 = System.currentTimeMillis();

        System.out.println("Sum2: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private static long sum(int[] set, int k) {
        long result = 0;

        int minSum = 0, maxSum = 0;
        for(int i = 0; i < k; i++) {
            minSum += set[i];
        }
        for(int i = set.length - k ; i < set.length; i++) {
            maxSum += set[i];
        }

        long[][] ways = new long[maxSum + 1][k + 1];
        ways[0][0] = 1;

        for(int i = 0; i < set.length; i++) {
            int currentSize = i + 1;
            int value = set[i];

            int[] currentMinSums = new int[k + 1];
            int[] currentMaxSums = new int[k + 1];

            int maxSize =  Math.min(k, currentSize);

            for(int setSize = 1; setSize <= maxSize; setSize++) {
                int min = 0, max = 0;
                for(int j = 0; j < setSize; j++) {
                    min += set[j];
                }
                for(int j = currentSize - setSize ; j < currentSize; j++) {
                    max += set[j];
                }
                currentMinSums[setSize] = min;
                currentMaxSums[setSize] = max;
            }

            long[][] used = new long[currentMaxSums[maxSize] + 1][k + 1];

            for(int setSize = 1; setSize <= Math.min(k, currentSize); setSize++) {
                int minSumCurrent = Math.max(value, currentMinSums[setSize]);
                int maxSumCurrent = currentMaxSums[setSize];

                for(int sum = minSumCurrent; sum <= maxSumCurrent; sum++) {
                    if(ways[sum - value][setSize - 1] > used[sum - value][setSize - 1]) {
                        long toAdd = ways[sum - value][setSize - 1] - used[sum - value][setSize - 1];
                        ways[sum][setSize] += toAdd;
                        used[sum][setSize] += toAdd;
                    }
                }
            }
        }

        for(int sum = minSum; sum <= maxSum; sum++) {
            if(ways[sum][k] == 1) {
                result += sum;
            }
        }

        return result;
    }
}