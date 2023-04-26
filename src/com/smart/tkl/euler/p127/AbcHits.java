package com.smart.tkl.euler.p127;

import java.util.*;

public class AbcHits {

    private final int limit;

    private Map<Long, Long> gcdCache = new HashMap<>();

    public AbcHits(int limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        AbcHits abcHits = new AbcHits(120000);
        long sum = abcHits.solve();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum=: " + sum);
        System.out.println("Sieve in ms: " + (time2 - time1));
    }

    public long solve() {
        long sum = 0;
        Radical[] radicals = sieve();
        Radical[] sortedRadicals = Arrays.copyOfRange(radicals, 1, radicals.length);
        Arrays.sort(sortedRadicals);
        for(int c = 3; c <= this.limit; c++) {
            long radc = radicals[c].product;
            long chalf = c / 2;
            for(Radical a : sortedRadicals) {
                if(a.product * radc > chalf) {
                   break;
                }
                if(a.number >= c / 2) {
                   continue;
                }
                int b = c - a.number;

                long product = (long)radicals[b].product * (long)a.product * (long)radicals[c].product;
                if(product < c) {
                   if(a.isRelativePrime(radicals[b])) {
                       sum += c;
                   }
                }
            }
        }
        return sum;
    }

    private Radical[] sieve() {
        Radical[] radicals = new Radical[limit + 1];
        for(int i = 0; i <= limit; i++) {
            radicals[i] = new Radical(i);
        }
        for(int n = 2; n <= limit; n++) {
            if (radicals[n].product == 1) {
                for(int p = n; p <= limit; p += n) {
                    radicals[p].product *= n;
                    radicals[p].addFactor(n);
                }
            }
        }
        return radicals;
    }

    private boolean isRelativePrime(Radical a, Radical b) {
       // return MathUtils.GCD(a.product, b.product) == 1;

       return a.isRelativePrime(b);
    }

    private static class Radical implements Comparable<Radical>{
        int number;
        int product;
        Set<Integer> factors = new LinkedHashSet<>();

        public Radical(int n) {
            this.number = n;
            this.product = 1;
        }

        void addFactor(int factor) {
            this.factors.add(factor);
        }

        @Override
        public int compareTo(Radical other) {
            int compareResult = Integer.compare(this.product, other.product);
            if(compareResult == 0) {
                compareResult = Integer.compare(this.number, other.number);
            }
            return compareResult;
        }

        boolean isRelativePrime(Radical other) {
            if(this.factors.size() < other.factors.size()) {
               for(int factor : this.factors) {
                   if(other.factors.contains(factor)) {
                      return false;
                   }
               }
            }
            else {
                for(int factor : other.factors) {
                    if(this.factors.contains(factor)) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "{" +
                    "n=" + number +
                    ", product=" + product +
                    '}';
        }
    }

}
