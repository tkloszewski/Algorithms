package com.smart.tkl.euler.p106;

import com.smart.tkl.lib.utils.ModuloUtils;
import java.util.Arrays;

public class SubsetMetaTesting2 {

    public static void main(String[] args) {
        long mod = (long)Math.pow(10, 9) + 7;
        long[] partitions = countPartitions(12, mod);
        System.out.println(Arrays.toString(partitions));
    }

    /*(n-4)*(n+2)*a(n) = (3*n^2 - 7*n - 5)*a(n-1) + (n-3)*(n-1)*a(n-2) - 3*(n-2)*(n-1)*a(n-3)
    * */
    public static long[] countPartitions(int n, long mod) {
        long[] partitions = new long[n + 1];
        if(n >= 4) {
           partitions[4] = 1;
        }
        for(int i = 5; i <= n; i++) {
            long term1 = ((long) (i - 4) * (i + 2)) % mod;
            long inv = ModuloUtils.modInv(term1, mod);

            long c1 = (3L * i * i - 7L * i - 5) % mod;
            long c2 = ((long) (i - 3) * (i - 1)) % mod;
            long c3 = (3L * (i - 2) * (i - 1)) % mod;

            long value = ((c1 * partitions[i -1]) % mod + (c2 * partitions[i - 2]) % mod
                    - (c3 * partitions[i - 3]) % mod) % mod;

            value = (value * inv) % mod;

            if(value < 0) {
               value = mod + value;
            }

            partitions[i] = value;
        }
        return partitions;
    }
}
