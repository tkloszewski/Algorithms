package com.smart.tkl.lib.dynamic.example;

public class Happiness {

    public static void main(String[] args) {
        int[] t1 = {1, 2, 3};
        int[] t2 = {3000, 2000, 1000, 3, 10};
        int[] t3 = {100, 1000, 100, 1000, 1};
        int[] t4 = {1, 1, 1, 1, 1};
        int[] t5 = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(maxHappiness(t1));
        System.out.println(maxHappiness(t2));
        System.out.println(maxHappiness(t3));
        System.out.println(maxHappiness(t4));
        System.out.println(maxHappiness(t5));
        System.out.println(maxNotAdjacentSum(new int[]{5, 5, 10, 100, 10, 5}));
        System.out.println(maxNotAdjacentSum(new int[]{3, 2, 7, 10}));
        System.out.println(maxNotAdjacentSum(new int[]{3, 2, 5, 10, 7}));
    }

    public static int maxNotAdjacentSum(int[] t) {
        int[] sum = new int[t.length];
        if(t.length >= 1) {
           sum[0] = t[0];
        }
        if(t.length >= 2) {
            sum[1] = Math.max(t[0], t[1]);
        }
        for(int i = 2; i < t.length; i++) {
            sum[i] = Math.max(sum[i - 1], sum[i - 2] + t[i]);
        }
        return sum[t.length - 1];
    }

    public static int maxHappiness(int[] t) {
        if(t.length <= 2) {
            int res = 0;
            for(int value : t) {
                res += value;
            }
            return res;
        }
        int[] sum = new int[t.length];
        sum[0] = t[0];
        sum[1] = t[0] + t[1];
        sum[2] = Math.max(Math.max(t[0] + t[1], t[0] + t[2]), t[1] + t[2]);

        for(int i = 3; i < t.length; i++) {
            int a = sum[i - 2] + t[i];
            int b = sum[i - 3] + t[i - 1] + t[i];
            int c = sum[i - 1];
            sum[i] = Math.max(Math.max(a, b), c);
        }
        return sum[t.length - 1];
    }

}
