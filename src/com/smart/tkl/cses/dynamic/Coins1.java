package com.smart.tkl.cses.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Coins1 {

    private final int n;
    private final int x;
    private final List<Integer> coins;
    private final long[] ways;

    public Coins1(int n, int x, List<Integer> coins) {
        this.n = n;
        this.x = x;
        this.coins = coins;
        this.ways = new long[x + 1];
        for(int i = 0; i <= x; i++) {
            this.ways[i] = -1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int x = sc.nextInt();
        List<Integer> coins = new ArrayList<>(n);
        for(int i = 0; i < n; i++) {
           coins.add(sc.nextInt());
        }
        long time1 = System.currentTimeMillis();
        Coins1 coins1 = new Coins1(n, x, coins);
        long count = coins1.count();
        long time2 = System.currentTimeMillis();
        System.out.println(count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        if(coins.size() == 1) {
           return this.x % coins.get(0) == 0 ? 1 : 0;
        }
        long mod = 1000000000 + 7;
        return countWays(this.x, mod);
    }

    private long countWays(int sum, long mod) {
        long result = 0;
        if(sum >= 0 && this.ways[sum] >= 0) {
           return ways[sum];
        }
        if(sum == 0) {
           result = 1;
        }
        else if (sum > 0){
           for(int coin : coins) {
               result += countWays(sum - coin, mod);
               result = result % mod;
           }
        }

        if (sum >= 0) {
           this.ways[sum] = result;
        }

        return result;
    }
}
