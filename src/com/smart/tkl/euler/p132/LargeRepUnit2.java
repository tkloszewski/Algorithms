package com.smart.tkl.euler.p132;

import com.smart.tkl.lib.primes.PrimesSieve;
import com.smart.tkl.lib.utils.Divisors;
import com.smart.tkl.lib.utils.RepUnitUtils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LargeRepUnit2 {

    private static final int LIMIT = 10000000;

    private final int minB;
    private final PrimesSieve primesSieve;
    private final List<Long> primes;
    private final List<RepUnitPrimeFactor> repUnitPrimeFactors;
    private final List<RepUnitPrimeFactor> decimalOrderRepUnitPrimeFactors = new ArrayList<>(100);
    private final long[] sums;
    private final int[] radicals;

    private int maxOrderPow2 = 0;
    private long maxOrderPrime = 0;

    public LargeRepUnit2(int minB, int maxK) {
        this.minB = minB;
        this.primesSieve = new PrimesSieve(LIMIT);
        this.primes = primesSieve.getPrimes();
        this.repUnitPrimeFactors = generateRepUnitFactors(primes, maxK);
        this.sums = toSums(repUnitPrimeFactors, maxK);
        this.radicals = toRadicals(LIMIT);
        setMultiplicativeOrdersAndPowers();
    }

    public static void main(String[] args) {
         int minB = 9;
         int maxK = 45;
         LargeRepUnit2 largeRepUnit2 = new LargeRepUnit2(minB, maxK);
         System.out.println(largeRepUnit2.getSumOfPrimes(2500, 513, maxK));
         System.out.println(largeRepUnit2.getSumOfPrimes2(2500, 513, maxK));

         System.out.println(largeRepUnit2.getSumOfPrimes(190, 9, maxK));
         System.out.println(largeRepUnit2.getSumOfPrimes2(190, 9, maxK));
    }

    private void setMultiplicativeOrdersAndPowers() {
        for(RepUnitPrimeFactor repUnitPrimeFactor : repUnitPrimeFactors) {
            long prime = repUnitPrimeFactor.prime;
            long phi = repUnitPrimeFactor.phi;
            if (!repUnitPrimeFactor.decimalOrder) {
                List<Long> divisors = Divisors.listProperDivisors(phi);
                for(int i = 1; i < divisors.size() - 1; i++) {
                    long div = divisors.get(i);
                    if(RepUnitUtils.tenPow(div, prime) == 1) {
                        repUnitPrimeFactor.order = div;
                        break;
                    }
                }
            }
            if(repUnitPrimeFactor.order == 0) {
               repUnitPrimeFactor.order = phi;
            }
            long n = repUnitPrimeFactor.order;
            int pow2 = 0, pow3 = 0;
            while (n % 2 == 0) {
                 pow2++;
                 n = n / 2;
            }
            while (n % 3 == 0) {
                pow3++;
                n = n / 3;
            }
            repUnitPrimeFactor.pow2 = pow2;
            repUnitPrimeFactor.pow3 = pow3;

            if(primesSieve.isPrime((int)repUnitPrimeFactor.order)) {
               maxOrderPrime = Math.max(maxOrderPrime, repUnitPrimeFactor.order);
            }
        }
    }

    public long getSumOfPrimes(int a, int b, int k) {
        long sum = 0;
        int count = 0;
        if(a % 3 == 0) {
           sum += 3;
           count++;
        }

        if(count == k) {
           return sum;
        }

        BigInteger pow = BigInteger.valueOf(a).pow(b);


        for(long prime : primes) {
            if(prime < 7) {
               continue;
            }
            long phi = prime - 1;
            long r = pow.mod(BigInteger.valueOf(phi)).longValue();
            if(test(r, prime)) {
               sum += prime;
               if(++count == k) {
                  break;
               }
            }
        }

        return sum;
    }

    public long getSumOfPrimes2(int a, int b, int k) {
        long sum = 0;
        int count = 0;
        if(a % 3 == 0) {
            sum += 3;
            count++;
        }

        if(count == k) {
            return sum;
        }

        int[] tab = nonDecimalPartAndPow23(a);
        int s = tab[0];
        long pow2 = (long) tab[1] * b;
        long pow3 = (long) tab[2] * b;
        if(s == 1 || (s > maxOrderPrime && primesSieve.isPrime(s))) {
           if(pow2 >= maxOrderPow2) {
               return sum + sums[k];
           }
           else {
               for(RepUnitPrimeFactor repUnitFactor : decimalOrderRepUnitPrimeFactors) {
                   if(repUnitFactor.sureCandidate || pow2 >= repUnitFactor.pow2) {
                       sum += repUnitFactor.prime;
                       count++;
                       if(count == k) {
                           return sum;
                       }
                   }
               }
           }
        }
        else {
           int radical = 10 * this.radicals[s];
           int i = 1;
           for(RepUnitPrimeFactor repUnitFactor : repUnitPrimeFactors) {
               if(isDivisibleBy(repUnitFactor, a, radical, pow2, pow3)) {
                   sum += repUnitFactor.prime;
                   count++;
                   if(count == k) {
                       //System.out.println("Found at position: " + i);
                       return sum;
                   }
               }
               i++;
           }
           return sum;
        }
        return sum;
    }

    private boolean isDivisibleBy(RepUnitPrimeFactor repUnitFactor, int a, int radical, long pow2, long pow3) {
        if(repUnitFactor.sureCandidate) {
           return true;
        }
        if(radical % this.radicals[(int)repUnitFactor.order] != 0) {
            return false;
        }
        if(a % repUnitFactor.order == 0) {
           return true;
        }
        //check powers of 2 and 3
        return repUnitFactor.pow2 <= pow2 && repUnitFactor.pow3 <= pow3;

    }

    private List<RepUnitPrimeFactor> generateRepUnitFactors(List<Long> primes, int maxK) {
        List<RepUnitPrimeFactor> result = new ArrayList<>(primes.size());
        int count = 0;
        for(long prime : primes) {
            if(prime > 5) {
                RepUnitPrimeFactor repUnitPrimeFactor = generateRepUnitFactor(prime);
                result.add(repUnitPrimeFactor);
                if(repUnitPrimeFactor.decimalOrder) {
                   decimalOrderRepUnitPrimeFactors.add(repUnitPrimeFactor);
                }
                if(repUnitPrimeFactor.sureCandidate) {
                    count++;
                    if(count == maxK) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    private RepUnitPrimeFactor generateRepUnitFactor(long prime) {
        long phi = prime - 1;
        long nonDecimalPart = nonDecimalPart(phi);
        long decimalPart = phi / nonDecimalPart;
        long multiplicativeOrder = 0;
        boolean sureCandidate = false;
        if(decimalPart == phi || RepUnitUtils.tenPow(decimalPart, prime) == 1) {
            List<Long> divisors = Divisors.listProperDivisors(decimalPart);
            for (long div : divisors) {
                long modTen = RepUnitUtils.tenPow(div, prime);
                if(modTen == 1) {
                    long n = div;
                    int k = 0;
                    while (n % 2 == 0) {
                        n = n / 2;
                        k++;
                    }
                    if(k <= this.minB) {
                       sureCandidate = true;
                    }
                    maxOrderPow2 = Math.max(maxOrderPow2, k);
                    multiplicativeOrder = div;
                    break;
                }
            }
        }

        RepUnitPrimeFactor repUnitPrimeFactor = new RepUnitPrimeFactor(prime, phi, decimalPart, nonDecimalPart, multiplicativeOrder > 0);
        repUnitPrimeFactor.order = multiplicativeOrder;
        repUnitPrimeFactor.sureCandidate = sureCandidate;
        return repUnitPrimeFactor;
    }

    private static long[] toSums(List<RepUnitPrimeFactor> repUnitPrimeFactors, int maxK) {
        long[] sums = new long[maxK + 1];
        long sum = 0;
        int k = 1;
        for(RepUnitPrimeFactor repUnitPrimeFactor : repUnitPrimeFactors) {
            if(repUnitPrimeFactor.decimalOrder) {
               sum += repUnitPrimeFactor.prime;
               sums[k++] = sum;
               if(k > maxK) {
                  break;
               }
            }
        }
        return sums;
    }

    private static int[] toRadicals(int limit) {
        int[] radicals = new int[limit + 1];
        Arrays.fill(radicals, 1);
        for(int n = 2; n <= limit; n++) {
            if (radicals[n] == 1) {
                for(int p = n; p <= limit; p += n) {
                    radicals[p] *= n;
                }
            }
        }
        return radicals;
    }

    private static int[] nonDecimalPartAndPow23(long n) {
        int pow2 = 0, pow3 = 0 ;
        while (n % 2 == 0) {
            n = n / 2;
            pow2++;
        }
        while (n % 5 == 0) {
            n = n / 5;
        }
        int nonDecimalPart = (int)n;
        while (n % 3 == 0) {
            n = n / 3;
            pow3++;
        }
        return new int[]{nonDecimalPart, pow2, pow3};
    }

    private static long nonDecimalPart(long n) {
        while (n % 2 == 0) {
            n = n / 2;
        }
        while (n % 5 == 0) {
            n = n / 5;
        }
        return n;
    }

    static boolean test(long r, long mod) {
        return BigInteger.TEN.modPow(BigInteger.valueOf(r), BigInteger.valueOf(mod)).longValue() == 1;
    }

    private static class RepUnitPrimeFactor {
        long prime;
        long phi;
        long decimalPart;
        long nonDecimalPart;
        boolean decimalOrder;
        boolean sureCandidate;
        long order = 0;
        int pow2, pow3;

        public RepUnitPrimeFactor(long prime, long phi, long decimalPart, long nonDecimalPart, boolean decimalOrder) {
            this.prime = prime;
            this.phi = phi;
            this.decimalPart = decimalPart;
            this.nonDecimalPart = nonDecimalPart;
            this.decimalOrder = decimalOrder;
        }

        @Override
        public String toString() {
            return "RepUnitPrimeFactor{" +
                    "prime=" + prime +
                    ", phi=" + phi +
                    ", decimalPart=" + decimalPart +
                    ", nonDecimalPart=" + nonDecimalPart +
                    ", decimalOrder=" + decimalOrder +
                    ", sureCandidate=" + sureCandidate +
                    ", order=" + order +
                    ", pow2=" + pow2 +
                    ", pow3=" + pow3 +
                    '}';
        }
    }
}
