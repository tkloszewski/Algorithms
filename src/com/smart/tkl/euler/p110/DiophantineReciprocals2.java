package com.smart.tkl.euler.p110;

import com.smart.tkl.utils.MathUtils;
import com.smart.tkl.utils.PrimeFactor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DiophantineReciprocals2 {

    private final long expProductLimit;
    private final List<Long> primes;

    public static void main(String[] args) {
        DiophantineReciprocals2 diophantineReciprocals = new DiophantineReciprocals2(4000000);
        System.out.println("Diophantine reciprocals primes: " + diophantineReciprocals.primes);
        Solution foundSolution = diophantineReciprocals.findMinSolution();
        System.out.println("Min solution: " + foundSolution);
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
        BigDecimal minValue = calcPrimesValue();
        System.out.println("Min value: " + minValue);
        Solution minSolution = initialSolution(minValue);
        while (true) {
            setPowersOfTwo(powers);
            if(powers[0] < powers[1]) {
                //we exhausted possible solution. Check next powers.
               index++;
            }
            else {
               BigDecimal newValue = calcValue(powers, minValue);
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

    private BigDecimal calcValue(long[] powers, BigDecimal minResult) {
        BigDecimal value = BigDecimal.ONE;
        for(int i = 0; i < powers.length; i++) {
            if(powers[i] == 0) {
                break;
            }
            BigDecimal exp = new BigDecimal(primes.get(i)).pow((int)powers[i]);
            value = value.multiply(exp);
            if(value.compareTo(minResult) > 0) {
                return minResult.add(BigDecimal.ONE);
            }
        }
        return value;
    }

    private BigDecimal calcPrimesValue() {
        BigDecimal value = BigDecimal.ONE;
        for(long prime : primes) {
            value = value.multiply(BigDecimal.valueOf(prime));
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

    private Solution initialSolution(BigDecimal minValue) {
        List<PrimeFactor> primeFactors = new ArrayList<>();
        for(long prime : primes) {
            primeFactors.add(new PrimeFactor((int)prime, 1));
        }
        return new Solution(primeFactors, minValue);
    }


    private static class Solution {
        List<PrimeFactor> primeFactors;
        BigDecimal value;

        public Solution(List<PrimeFactor> primeFactors, BigDecimal value) {
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
