package com.smart.tkl.euler.p131;

import com.smart.tkl.utils.MathUtils;

public class CubePartnership {

    private final long primeLimit;

    public CubePartnership(long primeLimit) {
        this.primeLimit = primeLimit;
    }

    public static void main(String[] args) {
        int count = new CubePartnership(1000000).countPrimes();
        System.out.println("Count: " + count);
    }

    public int countPrimes() {
        int count = 0;
        int i = 1, p = 3;
        while (p < primeLimit) {
            if(MathUtils.isPrime(p)) {
               count++;
            }
            i++;
            p = 3 * i * i + 3 * i + 1;
        }
        return count;
    }
}
