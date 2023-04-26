package com.smart.tkl.euler.p187;

import com.smart.tkl.lib.primes.PrimesSieve;

import java.util.List;

public class SemiPrimes {

    private final int limit;

    public SemiPrimes(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SemiPrimes semiPrimes = new SemiPrimes(100000000);
       // int count = semiPrimes.count();
        int fastCount = semiPrimes.fastCount();
        long time2 = System.currentTimeMillis();
      //  System.out.println("Semi primes count: " + count);
        System.out.println("Semi primes fast count: " + fastCount);
        System.out.println("Solution took: " + (time2 - time1));
    }

    public int count() {
        int semiPrimesCount = 0;
        PrimeFactorProduct[] products = new PrimeFactorProduct[this.limit];
        for(int n = 2; n < limit; n++) {
            products[n] = new PrimeFactorProduct(n ,1, 0);
        }
        for(int n = 2; n < limit; n++) {
            if(products[n].productCount > 0) {
                if(products[n].isSemiPrime()){
                   semiPrimesCount++;
                }
                continue;
            }
            for(int p = n; p < limit; p += n) {
                products[p].addFactor(n);
            }
        }
        return semiPrimesCount;
    }

    public int fastCount() {
        int result = 0;
        List<Long> primes = PrimesSieve.generatePrimesUpTo(this.limit / 2);
        for(int i = 0; i < primes.size(); i++) {
            for(int j = i; j < primes.size(); j++) {
                long product = primes.get(i) * primes.get(j);
                if(product < limit) {
                   result++;
                }
                else {
                   break;
                }
            }
        }
        return result;
    }

    private static class PrimeFactorProduct {
        int number;
        int product;
        int productCount;

        PrimeFactorProduct(int number, int product, int productCount) {
            this.number = number;
            this.product = product;
            this.productCount = productCount;
        }

        void addFactor(int factor) {
            product *= factor;
            productCount++;
        }

        boolean isSemiPrime() {
            return (productCount == 2 && number / product == 1)
                    || (productCount == 1 && product * product == number);
        }

        @Override
        public String toString() {
            return "{" +
                    "number=" + number +
                    ", product=" + product +
                    ", productCount=" + productCount +
                    '}';
        }
    }
}
