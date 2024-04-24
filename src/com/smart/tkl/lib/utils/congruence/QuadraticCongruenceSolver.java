package com.smart.tkl.lib.utils.congruence;

import com.smart.tkl.lib.primes.PrimesSieve;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class QuadraticCongruenceSolver {

    private static final long LIMIT_SQRT = (long)Math.sqrt(Long.MAX_VALUE);
    private static final List<Long> FIRST_PRIMES = List.of(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L, 79L);

    public static void main(String[] args) {
        System.out.println(solve(49, 1176));
    }

    /*
     * Solves quadratic congruence x^2 = a (mod m)
     * where a and m are composite numbers
     * */
    public static List<Long> solve(long a, long m) {
        if(m == 1) {
            return List.of(0L);
        }

        a = a % m;

        List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(m);
        List<PartialSolution> partialSolutions = new ArrayList<>(primeFactors.size());
        for(PrimeFactor primeFactor : primeFactors) {
            List<Long> partialSolution = solve(a, primeFactor.getFactor(), primeFactor.getPow());
            if(partialSolution.isEmpty()) {
               return List.of();
            }
            long mod = (long)Math.pow(primeFactor.getFactor(), primeFactor.getPow());
            partialSolutions.add(new PartialSolution(partialSolution, mod));
        }

        if(partialSolutions.size() == 1) {
           return partialSolutions.get(0).solutions;
        }

        List<Long> result = new ArrayList<>();
        List<List<LinearCongruence>> allCongruences = getCongruences(partialSolutions);

        for(List<LinearCongruence> congruenceSet : allCongruences) {
            long solution = LinearCongruenceSolver.solveCongruences(congruenceSet);
            result.add(solution);
        }

        Collections.sort(result);
        return result;
    }

    /*
    * Solves quadratic congruence x^2 = a (mod p^k) where p is any prime
    * */
    private static List<Long> solve(long a, long p, int k) {
        long mod = (long) Math.pow(p, k);
        a = a % mod;
        if(a % mod == 0) {
            int pow = k / 2;
            long multiple = (long) Math.pow(p, pow);
            if(k % 2 == 1) {
                multiple *= p;
            }
            List<Long> result = new ArrayList<>((int)multiple);
            int i = 0;
            while (i * multiple < mod) {
                result.add(i * multiple);
                i++;
            }
            return result;
        }
        else if(a % p == 0){
            if(p == 2) {
                return solveForPowOfTwo(a, k);
            }
            else {
                long s = a;
                int r = 0;
                while (s % p == 0) {
                    s = s / p;
                    r++;
                }
                if(r % 2 == 1) {
                    return List.of();
                }
                int m = r / 2;
                long pm = (long)Math.pow(p, m);

                return solveForSquareRootPrime(a, p, pm, mod, k);
            }
        }
        else {
            return p == 2 ? solveForPowOfTwo(a, k) : solveForOddPrime(a, p, k);
        }
    }

    /*
     * Solves pm^2 * x = a (mod pk) where p is odd prime
     * */
    private static List<Long> solveForSquareRootPrime(long a, long p, long pm, long pk, int k) {
        //solves for t
        long pm2 = pm * pm;
        List<Long> linearSolutions = LinearCongruenceSolver.solve(pm2, a, pk);
        List<Long> squareRoots = new ArrayList<>();
        for(long x : linearSolutions) {
            List<Long> roots = solveForOddPrime(x, p, k);
            squareRoots.addAll(roots);
        }
        Set<Long> uniqueSolutions = new TreeSet<>();
        for(long root : squareRoots) {
            long solution = (pm * root) % pk;
            uniqueSolutions.add(solution);
            uniqueSolutions.add(pk - solution);
        }
        return new ArrayList<>(uniqueSolutions);
    }

    /*
     * Solves x^2 = a (mod 2^k)
     * pow = 2^k
     * */
    private static List<Long> solveForPowOfTwo(long a, int k) {
        if(a % 2 == 1) {
            if(k < 3) {
                if(k == 1) {
                    return List.of(a);
                }
                else {
                    return a % 4 == 1 ? List.of(1L, 3L) : List.of();
                }
            }
            else {
                if (a % 8 != 1) {
                    return List.of();
                }

                long x1 = 1, x2 = 5;
                long pow = 8;
                for(int i = 4; i <= k; i++) {
                    long mod = pow * 2;
                    if(moduloSquare(x1, mod) != a % mod) {
                        long x = x2;
                        x1 = x;
                        x2 = (x + pow) % mod;
                    }
                    else {
                        x2 = (x1 + pow) % mod;
                    }
                    pow = mod;
                }
                long x3 = (pow - x1 + pow / 2) % pow;
                long x4 = pow - x1;
                return List.of(x1, x2, x3, x4);
            }
        }
        else {
            long s = a;
            int r = 0;
            while (s % 2 == 0) {
                s = s / 2;
                r++;
            }

            if(r % 2 == 1) {
                return List.of();
            }

            long mod = (long)Math.pow(2, k);
            long b = (long)Math.pow(2, (double) r / 2);

            Set<Long> solutions1 = new HashSet<>();
            solutions1.add(b);
            solutions1.add(mod - b);
            solutions1.add((b + mod / 2) % mod);
            solutions1.add((mod - b + mod / 2) % mod);

            List<Long> solutions2 = solveForPowOfTwo(s, k - r);

            return combineSolutions(solutions1, solutions2, mod);
        }
    }

    /*
     *  Solves x^2 == a(mod p^k) where p is odd prime and a > 0
     * */
    private static List<Long> solveForOddPrime(long a, long p, int k) {
        List<Long> solutions = solveForOddPrime(a, p);
        if(solutions.isEmpty() || solutions.size() == 1) {
            return solutions;
        }
        else if(solutions.size() == 2) {
            long x1 = solutions.get(0);
            long x2 = solutions.get(1);
            long primePow = p;
            for (int i = 2; i <= k; i++) {
                long nextPrimePow = primePow * p;
                long k1 = solveNextPrimePower(x1, a, primePow, nextPrimePow);
                x1 = primePow * k1 + x1;
                x2 = nextPrimePow - x1;
                primePow *= p;
            }
            return List.of(Math.min(x1, x2), Math.max(x1, x2));
        }

        return List.of();
    }

    /*
     * Solves 2 * x * p^n = (a - x^2) % mod (p^(n+1))
     *        f1 * p^n = f2 mod (p^(n+1))
     * */
    private static long solveNextPrimePower(long x, long a, long primePow, long nextPrimePow) {
        long f1 = moduloMultiply(2 * x, primePow, nextPrimePow);
        long f2 = (nextPrimePow - moduloSquare(x, nextPrimePow) + a) % nextPrimePow;
        Optional<Long> result = LinearCongruenceSolver.leastSolution(f1, f2, nextPrimePow);
        return result.orElseThrow();
    }

    /*
    Solves x^2 == a(mod p) where p is prime
    *
    */
    private static List<Long> solveForOddPrime(long a, long p) {
        List<Long> result = new ArrayList<>(2);
        if(a == 0) {
            result.add(0L);
        }
        else if(p == 2) {
            long x = a % 2 == 1 ? 1 : 0;
            result.add(x);
        }
        else {
            if(!isQuadraticResidue(a, p)) {
                return result;
            }

            if (p % 4 == 3) {
                long n = (p - 3) / 4;
                long x1 = MathUtils.moduloPower(a, n + 1, p);
                long x2 = p - x1;
                result.add(x1);
                result.add(x2);
            }
            else {
                //p = 1 (mod 4)
                if(p % 8 == 5) {
                    long b = MathUtils.moduloPower(a, (p - 1) / 4, p);
                    if(b == 1) {
                        long x = MathUtils.moduloPower(a, (p + 3) / 8, p);
                        result.add(x);
                        result.add(p - x);
                    }
                    else {
                        long f1 = 2 * a;
                        long f2 = MathUtils.moduloPower(4 * a, (p - 5) / 8, p);
                        long x = MathUtils.moduloMultiply(f1, f2, p);
                        result.add(x);
                        result.add(p - x);
                    }
                }
                else {
                    //p = 1 (mod 8)
                    long x = findSquareRoot(a, p);
                    result.add(x);
                    result.add(p - x);
                }
            }
        }
        if(result.size() == 2 && result.get(0) > result.get(1)) {
            long temp = result.get(0);
            result.set(0, result.get(1));
            result.set(1, temp);
        }
        return result;
    }

    /*
    Finds the x^2 = a (mod p) using Tonelli-Shanks algorithm
    */
    private static long findSquareRoot(long a, long p) {
        //p - 1 = 2^r * s
        long s = p - 1;
        int r = 0;
        while (s % 2 == 0) {
            s = s / 2;
            r++;
        }
        long b = MathUtils.moduloPower(a, s, p);
        long x = MathUtils.moduloPower(a,(s + 1) / 2, p);
        if(b == 1) {
            return x;
        }
        long n = findFirstQuadraticNonResidue(p);
        long z = MathUtils.moduloPower(n, s, p);

        while (b != 1) {
            //find order_p(b) = 2^m
            int m = 0;
            long t = b;
            while (t != 1) {
                t = moduloSquare(t, p);
                m++;
            }

            //z1 = z ^ 2^(r - m) mod p
            //z2 = z ^ 2^(r - m - 1) mod p
            long z1, z2 = z;
            int pow = r - m - 1;
            for(int i = 0; i < pow; i++) {
                z2 = moduloSquare(z2, p);
            }
            z1 = moduloSquare(z2, p);
            b = MathUtils.moduloMultiply(b, z1, p);
            x = MathUtils.moduloMultiply(x, z2, p);
        }

        return x;
    }

    private static long findFirstQuadraticNonResidue(long p) {
        List<Long> firstPrimes = List.of(2L, 3L, 5L, 7L);
        for(long prime : FIRST_PRIMES) {
            if(!isQuadraticResidue(prime, p)) {
                return prime;
            }
        }

        for(long n = 83, step = 4; n < p; n += step) {
            if(MathUtils.isPrime(n)) {
                if(!isQuadraticResidue(n, p)) {
                    return n;
                }
            }
            step = step == 4 ? 2 : 4;
        }
        //mathematically not possible to happen
        throw new RuntimeException("No non quadratic residue found for prime: " + p);
    }

    //Euler's Criterion
    private static boolean isQuadraticResidue(long a, long p) {
        long pow = (p - 1) / 2;
        long sign = MathUtils.moduloPower(a, pow, p);
        return sign == 1;
    }

    //x^2 (mod m)
    private static long moduloSquare(long x, long m) {
        if(x <= LIMIT_SQRT) {
            return (x * x) % m;
        }
        return BigInteger.valueOf(x)
                .modPow(BigInteger.TWO, BigInteger.valueOf(m))
                .longValue();
    }

    private static long moduloMultiply(long a, long b, long m) {
        return ((a % m) * (b % m)) % m;
    }

    private static List<Long> combineSolutions(Collection<Long> solutions1, Collection<Long> solutions2, long mod) {
        if(solutions1.isEmpty() || solutions2.isEmpty()) {
            return List.of();
        }
        Set<Long> uniqueSolutions = new TreeSet<>();
        for(long x1 : solutions1) {
            for(long x2 : solutions2) {
                uniqueSolutions.add((x1 * x2) % mod);
            }
        }
        return new ArrayList<>(uniqueSolutions);
    }

    private static List<List<LinearCongruence>> getCongruences(List<PartialSolution> partialSolutions) {
        List<List<LinearCongruence>> allCongruences = new ArrayList<>();
        fillCongruences(allCongruences, partialSolutions, new ArrayList<>(), 0);
        return allCongruences;
    }

    private static void fillCongruences(List<List<LinearCongruence>> allCongruences, List<PartialSolution> partialSolutions,
                                        List<LinearCongruence> accumulatedCongruences, int pos) {
        PartialSolution partialSolution = partialSolutions.get(pos);
        if(pos == partialSolutions.size() - 1) {
           for(long x : partialSolution.solutions) {
               accumulatedCongruences.add(new LinearCongruence(x, partialSolution.mod));
               allCongruences.add(new ArrayList<>(accumulatedCongruences));
               accumulatedCongruences.remove(accumulatedCongruences.size() - 1);
           }
        }
        else {
            for(long x : partialSolution.solutions) {
                accumulatedCongruences.add(new LinearCongruence(x, partialSolution.mod));
                int removeIndex = accumulatedCongruences.size() - 1;
                fillCongruences(allCongruences, partialSolutions, accumulatedCongruences, pos + 1);
                accumulatedCongruences.remove(removeIndex);
            }
        }
    }

    private static class PartialSolution {
        List<Long> solutions;
        long mod;

        public PartialSolution(List<Long> solutions, long mod) {
            this.solutions = solutions;
            this.mod = mod;
        }
    }
}
