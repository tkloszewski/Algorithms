package com.smart.tkl.euler.p136;

public class SingletonDifferences2 {

    private static final long THRESHOLD = 6000000;
    private final long[] consecutiveSingleCounts;

    public SingletonDifferences2(long minL, long maxR) {
        if(maxR <= THRESHOLD) {
           consecutiveSingleCounts = initConsecutiveSingleCounts((int)minL, (int)maxR);
        }
        else {
           consecutiveSingleCounts = null;
        }
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SingletonDifferences2 singletonDifferences = new SingletonDifferences2(1, 6000000);
        long count = singletonDifferences.countSinglesInRange(1, 99);
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long countSinglesInRange(long L, long R) {
        if(consecutiveSingleCounts != null) {
           int left = (int)L;
           int right = (int)R;
           if(consecutiveSingleCounts[left] == 0) {
              return consecutiveSingleCounts[right];
           }
           else {
               if(consecutiveSingleCounts[left] == consecutiveSingleCounts[left - 1] + 1) {
                   return consecutiveSingleCounts[right] - consecutiveSingleCounts[left] + 1;
               }
               else {
                   return consecutiveSingleCounts[right] - consecutiveSingleCounts[left];
               }
           }
        }
        else {
            return countManualInRanges(L, R);
        }
    }

    private long[] initConsecutiveSingleCounts(int minL, int maxR) {
        long[] result = new long[maxR + 1];
        long[] tab = new long[maxR + 1];

        int maxQ = (int)Math.sqrt(3 * maxR);
        for(int q = 1; q < maxQ; q++) {
            int minP1 = (int)Math.sqrt(minL / 3.0);
            int minP2 = minL / q;
            int minP = Math.max(minP1, minP2);
            for(int p = minP; p * q <= maxR; p++) {
                if(p * q < minL) {
                    continue;
                }
                if((p + q) % 4 == 0 && (3 * p - q) > 0 && (3 * p - q) % 4 == 0) {
                    int n = p * q;
                    tab[n]++;
                }
            }
        }

        long count = 0;
        for(int i = 0; i < tab.length; i++) {
            if(tab[i] == 1) {
                count++;
            }
            result[i] = count;
        }

        return result;
    }

    private long countManualInRanges(long L, long R) {
        long count = 0;
        int[] tab = new int[(int)(R - L + 1)];
        long maxQ = (int)Math.sqrt(3 * R);
        for(long q = 1; q < maxQ; q++) {
            long minP1 = (int)Math.sqrt(L / 3.0);
            long minP2 = L / q;
            long minP = Math.max(minP1, minP2);
            for(long p = minP; p * q <= R; p++) {
                long n = p * q;
                if(n < L) {
                    continue;
                }
                if((p + q) % 4 == 0 && (3 * p - q) > 0 && (3 * p - q) % 4 == 0) {
                    int idx = (int)(n - L);
                    if(tab[idx] == 0) {
                        count++;
                    }
                    else if(tab[idx] == 1) {
                        count--;
                    }
                    tab[idx]++;
                }
            }
        }
        return count;
    }
}
