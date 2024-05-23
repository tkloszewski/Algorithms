package com.smart.tkl.euler.p110;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DiophantineReciprocals2 {

    private final long expProductLimit;
    private final List<Long> primes;

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        DiophantineReciprocals2 diophantineReciprocals = new DiophantineReciprocals2(1000);
        Solution foundSolution = diophantineReciprocals.findMinSolution();
        long t2 = System.currentTimeMillis();
        System.out.println("Diophantine reciprocals primes: " + diophantineReciprocals.primes);
        System.out.println("Min solution: " + foundSolution);
        System.out.println("Time in ms: " + (t2 - t1));
    }

    public DiophantineReciprocals2(long solutionThreshold) {
        this.expProductLimit = 2 * solutionThreshold + 1;
        this.primes = MathUtils.generatePrimeList(primesCount(expProductLimit));
    }

    private static int primesCount(long productLimit) {
        double log3 = Math.log10(productLimit) / Math.log10(3);
        return (log3 == (int)log3) ? (int)log3 : (int)log3 + 1;
    }

    public Solution findMinSolution() {
        long[] powers = new long[primes.size()];
        int index = 1;
        BigInteger minValue = calcPrimesValue();
        System.out.println("Min value: " + minValue);
        Solution minSolution = initialSolution(minValue);
        while (true) {
            setPowersOfTwo(powers);
            if(powers[0] < powers[1]) {
                //we exhausted possible solution. Check next powers.
               index++;
            }
            else {
               BigInteger newValue = calcValue(powers, minValue);
               if(newValue.compareTo(minValue) < 0) {
                   minValue = newValue;
                   minSolution = new Solution(toPrimeFactors(powers), minValue);
               }
               index = 1;
            }
            if(index >= powers.length) {
               break;
            }
            powers[index]++;

            setPowersLessThanToValue(index, powers[index], powers);
        }

        return minSolution;
    }

    private void setPowersLessThanToValue(int index, long value, long[] powers) {
        for(int i = 0; i < index; i++) {
            powers[i] = value;
        }
    }

    private void setPowersOfTwo(long[] powers) {
        long product = 1;
        for(int i = 1; i < powers.length; i++) {
            if(powers[i] == 0) {
               break;
            }
            product *= (2 * powers[i] + 1);
        }
        powers[0] = (expProductLimit / product - 1) / 2;
        while ((powers[0] * 2 + 1) * product < expProductLimit) {
            powers[0]++;
        }
    }

    private BigInteger calcValue(long[] powers, BigInteger minResult) {
        BigInteger value = BigInteger.ONE;
        for(int i = 0; i < powers.length; i++) {
            if(powers[i] == 0) {
                break;
            }
            BigInteger exp = BigInteger.valueOf(primes.get(i)).pow((int)powers[i]);
            value = value.multiply(exp);
            if(value.compareTo(minResult) > 0) {
                return minResult.add(BigInteger.ONE);
            }
        }
        return value;
    }

    private BigInteger calcPrimesValue() {
        BigInteger value = BigInteger.ONE;
        for(long prime : primes) {
            value = value.multiply(BigInteger.valueOf(prime));
        }
        return value;
    }

    private List<PrimeFactor> toPrimeFactors(long[] powers) {
        List<PrimeFactor> primeFactors = new ArrayList<>(powers.length);
        for(int i = 0; i <powers.length; i++) {
            if(powers[i] == 0) {
               break;
            }
            primeFactors.add(new PrimeFactor(primes.get(i).intValue(), (int)powers[i]));
        }
        return primeFactors;
    }

    private Solution initialSolution(BigInteger minValue) {
        List<PrimeFactor> primeFactors = new ArrayList<>();
        for(long prime : primes) {
            primeFactors.add(new PrimeFactor((int)prime, 1));
        }
        return new Solution(primeFactors, minValue);
    }


    private static class Solution {
        List<PrimeFactor> primeFactors;
        BigInteger value;

        public Solution(List<PrimeFactor> primeFactors, BigInteger value) {
            this.primeFactors = primeFactors;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Solution{" +
                    "primeFactors=" + primeFactors +
                    ", value=" + value +
                    '}';
        }
    }



}
