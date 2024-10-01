package com.smart.tkl.euler.p149;

import java.util.Random;

public class NextElementResolver implements TriFunction<long[][], Integer, Integer, Long> {

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++) {
            long l = new Random().nextLong() % 10;
            System.out.println(l);
        }
    }

    @Override
    public Long apply(long[][] tab, Integer k, Integer limit) {
        if(k <= 55) {
            return (100003 - 200003L * k + 300007L * k * k * k) % 1000000 - 500000;
        }
        int m2 = (k - 25) / limit;
        int m3 = (k - 56) / limit;
        return (tab[m2][(k - 25) % limit] + tab[m3][(k - 56) % limit] + 1000000) % 1000000 - 500000;
    }
}
