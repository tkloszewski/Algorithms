package com.smart.tkl;

import java.util.ArrayList;
import java.util.List;

public class PrimeGenerator {

    public static void main(String[] args) {
        List<Integer> primes = new PrimeGenerator().generatePrimesUpTo(100000);
        System.out.println(primes);
    }

    public List<Integer> generatePrimesUpTo(int n) {
        List<Integer> primes = new ArrayList<>();
        for(int i = n; i >= 1 ;i--) {
            if(isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    private boolean isPrime(int n) {
        for(int i = 2; i <= n/2; i++) {
            if(n % i == 0) {
               return false;
            }
        }
        return true;
    }
}
