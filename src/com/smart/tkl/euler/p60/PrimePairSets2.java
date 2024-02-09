package com.smart.tkl.euler.p60;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.primes.PrimesSieve;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrimePairSets2 {

    private final PrimesSieve sieve;
    private final int limit;
    private final int setSize;
    private final List<Long> primeSums = new ArrayList<>();

    public PrimePairSets2(int limit, int setSize) {
        long time1 = System.currentTimeMillis();
        this.sieve = new PrimesSieve(getSieveLength(limit));
        this.limit = limit;
        this.setSize = setSize;
        long time2 = System.currentTimeMillis();
        System.out.println("Primes length: " + sieve.getLength());
        System.out.println("Primes sieve time: " + (time2 - time1));
    }

    private static int getSieveLength(int limit) {
        int pow = 2 * ((int)Math.log10(limit) + 1);
        return Math.min(100000000, (int)Math.pow(10, pow));
    }

    public static void main(String[] args) {
        int limit = 20000, setSize = 5;
        long time1 = System.currentTimeMillis();
        PrimePairSets2 primePairSets2 = new PrimePairSets2(limit, setSize);
        List<Long> primeSums = primePairSets2.generatePrimeSums();
        long time2 = System.currentTimeMillis();
        System.out.println(primeSums);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public List<Long> generatePrimeSums() {
        List<Long> primes = getPrimes();
        List<Long> primes1 = primes.stream().filter(p -> p % 3 == 1).collect(Collectors.toList());
        List<Long> primes2 = primes.stream().filter(p -> p % 3 == 2).collect(Collectors.toList());

        primes1.add(0, 3L);
        primes2.add(0, 3L);

        boolean[][] pairs1 = new boolean[primes1.size()][primes1.size()];
        boolean[][] pairs2 = new boolean[primes2.size()][primes2.size()];
        List<IndexedPrime>[] indexedPrimes1 = new ArrayList[primes1.size()];
        List<IndexedPrime>[] indexedPrimes2 = new ArrayList[primes2.size()];

        fillData(primes1, pairs1, indexedPrimes1);
        fillData(primes2, pairs2, indexedPrimes2);

        if(setSize == 3) {
            findTripleSets(primes1, indexedPrimes1, pairs1);
            findTripleSets(primes2, indexedPrimes2, pairs2);
        }
        else if(setSize == 4) {
            findQuadrupleSets(primes1, indexedPrimes1, pairs1);
            findQuadrupleSets(primes2, indexedPrimes2, pairs2);
        }
        else if(setSize == 5) {
            findQuintupleSets(primes1, indexedPrimes1, pairs1);
            findQuintupleSets(primes2, indexedPrimes2, pairs2);
        }

        Collections.sort(primeSums);

        return primeSums;
    }

    private void fillData(List<Long> primes, boolean[][] pairs, List<IndexedPrime>[] indexedPrimes) {
        for(int i = 0; i < primes.size(); i++) {
            long p1 = primes.get(i);
            List<IndexedPrime> indexedPrimesList = new ArrayList<>(100);
            for(int j = i + 1; j < primes.size(); j++) {
                long p2 = primes.get(j);
                if(isPrimePair(p1, p2)) {
                    pairs[i][j] = true;
                    indexedPrimesList.add(new IndexedPrime(j, p2));
                }
            }
            indexedPrimes[i] = indexedPrimesList;
        }
    }

    private void findQuintupleSets(List<Long> primes, List<IndexedPrime>[] indexedPrimes, boolean[][] pairs) {
        for(int i = 0; i < primes.size(); i++) {
            long prime1 = primes.get(i);
            for(IndexedPrime indexedPrime1 : indexedPrimes[i]) {
                long prime2 = indexedPrime1.prime;
                for(IndexedPrime indexedPrime2 : indexedPrimes[indexedPrime1.idx]) {
                    if(pairs[i][indexedPrime2.idx]) {
                        long prime3 = indexedPrime2.prime;
                        for(IndexedPrime indexedPrime3 : indexedPrimes[indexedPrime2.idx]) {
                            if(pairs[i][indexedPrime3.idx] && pairs[indexedPrime1.idx][indexedPrime3.idx]) {
                                long prime4 = indexedPrime3.prime;
                                for(IndexedPrime indexedPrime4 : indexedPrimes[indexedPrime3.idx]) {
                                    if(pairs[i][indexedPrime4.idx] && pairs[indexedPrime1.idx][indexedPrime4.idx]
                                      && pairs[indexedPrime2.idx][indexedPrime4.idx]) {
                                        long prime5 = indexedPrime4.prime;
                                        long sum = prime1 + prime2 + prime3 + prime4 + prime5;
                                        primeSums.add(sum);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void findQuadrupleSets(List<Long> primes, List<IndexedPrime>[] indexedPrimes, boolean[][] pairs) {
        for(int i = 0; i < primes.size(); i++) {
            long prime1 = primes.get(i);
            for(IndexedPrime indexedPrime1 : indexedPrimes[i]) {
                long prime2 = indexedPrime1.prime;
                for(IndexedPrime indexedPrime2 : indexedPrimes[indexedPrime1.idx]) {
                    if(pairs[i][indexedPrime2.idx] && pairs[indexedPrime1.idx][indexedPrime2.idx]) {
                        long prime3 = indexedPrime2.prime;
                        for(IndexedPrime indexedPrime3 : indexedPrimes[indexedPrime2.idx]) {
                            if(pairs[i][indexedPrime3.idx] && pairs[indexedPrime1.idx][indexedPrime3.idx]) {
                                long prime4 = indexedPrime3.prime;
                                long sum = prime1 + prime2 + prime3 + prime4;
                                primeSums.add(sum);
                            }
                        }
                    }
                }
            }
        }
    }

    private void findTripleSets(List<Long> primes, List<IndexedPrime>[] indexedPrimes, boolean[][] pairs) {
        for(int i = 0; i < primes.size(); i++) {
            long prime1 = primes.get(i);
            for(IndexedPrime indexedPrime1 : indexedPrimes[i]) {
                long prime2 = indexedPrime1.prime;
                for(IndexedPrime indexedPrime2 : indexedPrimes[indexedPrime1.idx]) {
                    if(pairs[i][indexedPrime2.idx] && pairs[indexedPrime1.idx][indexedPrime2.idx]) {
                        long prime3 = indexedPrime2.prime;
                        long sum = prime1 + prime2 + prime3;
                        primeSums.add(sum);
                    }
                }
            }
        }
    }

    private List<Long> getPrimes() {
        List<Long> primes = new ArrayList<>();
        primes.add(3L);
        for(int i = 7, step = 2; i < limit; i += step) {
            if(sieve.isPrime(i)) {
                primes.add((long)i);
            }
            step = step == 2 ? 4 : 2;
        }
        return primes;
    }

    private boolean isPrimePair(long prime1, long prime2) {
        return isPrime(concat(prime1, prime2)) && isPrime(concat(prime2, prime1));
    }

    private boolean isPrime(long number) {
        if(number <= sieve.getLength()) {
            return sieve.isPrime((int)number);
        }
        return Primes.isPrime(number);
    }

    private static long concat(long a, long b) {
        long shift = 1;
        long n = b;
        while (n > 0) {
            shift *= 10;
            n = n / 10;
        }
        return a * shift + b;
    }

    private static class IndexedPrime {
        int idx;
        long prime;

        public IndexedPrime(int idx, long prime) {
            this.idx = idx;
            this.prime = prime;
        }
    }
}
