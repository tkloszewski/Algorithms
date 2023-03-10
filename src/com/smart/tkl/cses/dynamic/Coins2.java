package com.smart.tkl.cses.dynamic;

import com.smart.tkl.cses.FastReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Coins2 {

    private final int n;
    private final int x;
    private final int[] coins;

    public Coins2(int n, int x, int[] coins) {
        this.n = n;
        this.x = x;
        this.coins = coins;
    }

    public static void main(String[] args) throws Exception {
        FastReader reader = new FastReader();
        int n = reader.nextInt();
        int x = reader.nextInt();

        int[] coins = new int[n];
        for(int i = 0; i < n; i++) {
            coins[i] = reader.nextInt();
        }

        Coins2 coins2 = new Coins2(n, x, coins);
        long count = coins2.count();

        System.out.println(count);
    }


    public long count() {
        long mod = 1000000000 + 7;
        long[] ways = new long[this.x + 1];
        ways[0] = 1;

        for(int j = 0; j < coins.length; j++) {
            int coin = coins[j];
            for(int i = coin; i <= x; i++) {
                ways[i] += ways[i - coin];
                ways[i] = ways[i] % mod;
            }
        }

        return ways[this.x];
    }


}
