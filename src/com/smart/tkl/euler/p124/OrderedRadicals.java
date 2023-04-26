package com.smart.tkl.euler.p124;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OrderedRadicals {

    private final int limit;
    private final List<PrimeFactorsProduct> products;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        OrderedRadicals orderedRadicals = new OrderedRadicals(100000);
        long pos = orderedRadicals.findElementForPosition(10000);
        long time2 = System.currentTimeMillis();
        System.out.println("Position for 10000: " + pos);
        System.out.println("Solution took in ms: " + (time2 - time1));

        orderedRadicals = new OrderedRadicals(10);
        PrimeFactorsProduct[] products = orderedRadicals.sieve();
        System.out.println(Arrays.toString(products));
    }

    public OrderedRadicals(int limit) {
        this.limit = limit;
        this.products = generateProducts();
    }

    public long findElementForPosition(int k) {
        PrimeFactorsProduct product = products.get(k);
        return product.n;
    }

    private List<PrimeFactorsProduct> generateProducts() {
        List<PrimeFactorsProduct> products = Arrays.asList(sieve());
        Collections.sort(products);
        return products;
    }

    private PrimeFactorsProduct[] sieve() {
        PrimeFactorsProduct[] products = new PrimeFactorsProduct[limit + 1];
        for(int i = 0; i <= limit; i++) {
            products[i] = new PrimeFactorsProduct(i, 1);
        }
        for(int n = 2; n <= limit; n++) {
            if(products[n].product != 1) {
               continue;
            }
            for(int p = n; p <= limit; p += n) {
                products[p].product *= n;
            }
        }
        return products;
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
