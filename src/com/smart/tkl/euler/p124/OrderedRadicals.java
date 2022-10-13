package com.smart.tkl.euler.p124;

import com.smart.tkl.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderedRadicals {

    private final int limit;
    private final List<PrimeFactorsProduct> products;

    public static void main(String[] args) {
        OrderedRadicals orderedRadicals = new OrderedRadicals(100000);
        long pos = orderedRadicals.findElementForPosition(10000);
        System.out.println("Position for 10000: " + pos);
    }

    public OrderedRadicals(int limit) {
        this.limit = limit;
        this.products = generateProducts();
    }

    public long findElementForPosition(int k) {
        PrimeFactorsProduct product = products.get(k - 1);
        return product.n;
    }

    private List<PrimeFactorsProduct> generateProducts() {
        List<PrimeFactorsProduct> products = new ArrayList<>();

        List<Long> primes = MathUtils.generatePrimesUpTo(limit);
        for(int i = 1; i <= limit; i++) {
            products.add(rad(i, primes));
        }

        Collections.sort(products);

        return products;
    }

    private PrimeFactorsProduct rad(long n, List<Long> primes) {
        List<Long> primeFactors = MathUtils.listPrimeFactorsForPrimes(n, primes);
        return new PrimeFactorsProduct(n, toProduct(primeFactors));
    }

    private static Long toProduct(List<Long> list) {
        return list.stream().reduce(1L, (a, b) -> a * b);
    }

    private static class PrimeFactorsProduct implements Comparable<PrimeFactorsProduct>{
        Long n;
        Long product;

        public PrimeFactorsProduct(long n, long product) {
            this.n = n;
            this.product = product;
        }

        @Override
        public int compareTo(PrimeFactorsProduct other) {
            int compareResult = this.product.compareTo(other.product);
            if(compareResult == 0) {
               compareResult = this.n.compareTo(other.n);
            }
            return compareResult;
        }

        @Override
        public String toString() {
            return "PrimeFactorsProduct{" +
                    "n=" + n +
                    ", product=" + product +
                    '}';
        }
    }
}
