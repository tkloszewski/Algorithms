package com.smart.tkl.euler.p179;

import com.smart.tkl.utils.PrimeFactor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ConsecutivePositiveDivisors {

    private final int limit;

    public ConsecutivePositiveDivisors(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ConsecutivePositiveDivisors consecutiveDivisors = new ConsecutivePositiveDivisors(10000000);
        int count = consecutiveDivisors.solve();
        long time2 = System.currentTimeMillis();

        System.out.println("Result: " + count);
        System.out.println("Solution found in: " + (time2 - time1));
    }

    private int solve() {
        //take into account 2 and 3 as the only consecutive prime numbers
        int result = 1;
        Set<Integer> foundValues = new TreeSet<>();
        ProductEntry[] products = new ProductEntry[this.limit + 1];
        for(int i = 2; i <= this.limit; i++) {
            products[i] = new ProductEntry(i);
        }
        for(int n = 2; n <= this.limit; n++) {
            if(products[n].product > 1) {
                int count1 = products[n].countDivisors();
                if(n > 2) {
                    int count2 = products[n - 1].countDivisors();
                    if(count1 == count2) {
                        result++;
                    }
                }
                continue;
            }
            for(int p = n; p <= this.limit; p += n) {
                products[p].addPrimeFactor(n);
            }
        }
        return result;
    }

    private static class ProductEntry {
        int number;
        List<PrimeFactor> primeFactors = new ArrayList<>(9);
        int product = 1;
        int divisorsCount = 0;

        ProductEntry(int number) {
            this.number = number;
        }

        void addPrimeFactor(int factor) {
            primeFactors.add(new PrimeFactor(factor, 1));
            product *= factor;
        }

        int countDivisors() {
            if(divisorsCount != 0) {
                return divisorsCount;
            }
            int n = number / product;
            if (n > 1) {
                for(PrimeFactor primeFactor : primeFactors) {
                    int factor = primeFactor.getFactor();
                    while (n % factor == 0) {
                        primeFactor.incrPower();
                        n = n / factor;
                    }
                    if(n == 1) {
                        break;
                    }
                }
            }
            int result = 1;
            for(PrimeFactor primeFactor : primeFactors) {
                result *= (primeFactor.getPow() + 1);
            }
            divisorsCount = result;
            return result;
        }

        @Override
        public String toString() {
            return "{" +
                    "number=" + number +
                    ", primeFactors=" + primeFactors +
                    ", product=" + product +
                    ", divisorsCount=" + divisorsCount +
                    '}';
        }
    }

}
