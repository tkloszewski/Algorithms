package com.smart.tkl.euler.p100.hk;

import com.smart.tkl.lib.primes.PrimesRangeSieve;
import com.smart.tkl.lib.utils.Divisors;
import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.XYPair;
import com.smart.tkl.lib.utils.diophantine.PellsEquationSolver;
import com.smart.tkl.lib.utils.diophantine.ReducedHyperbolicIterator;
import com.smart.tkl.lib.utils.diophantine.ReducedHyperbolicSolver;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class ArrangedProbability2 {

    private static final String INPUT_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p100\\hk\\inputs.txt";
    private static final BigInteger ULTIMATE_THRESHOLD = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TWO).add(BigInteger.ONE);
    private static final BigInteger PELLS_CONVERGENT_THRESHOLD = ULTIMATE_THRESHOLD.multiply(BigInteger.TEN);

    private final List<ArrangedProbabilityInput> inputList;
    private final ReducedHyperbolicSolver solver;

    public ArrangedProbability2() {
        this(List.of());
    }

    public ArrangedProbability2(List<ArrangedProbabilityInput> input) {
        this.inputList = input;
        this.solver = new ReducedHyperbolicSolver(PELLS_CONVERGENT_THRESHOLD);
    }

    public static void main(String[] args) {
        test1();
        tests2();
        tests3();
        tests4();
       // tests5();
    }

    private static void test1() {
        long time1 = System.currentTimeMillis();

        ArrangedProbability2 arrangedProbability2 = new ArrangedProbability2();
        Optional<XYPair> solution1 = arrangedProbability2.findSolutionForNonSquare(3, 8, 1000);
        Optional<XYPair> solution2 = arrangedProbability2.findSolutionForNonSquare(1, 2, 100);
        Optional<XYPair> solution3 = arrangedProbability2.findSolutionForNonSquare(1, 2, 5);
        Optional<XYPair> solution4 = arrangedProbability2.findSolutionForNonSquare(1, 2, 2);
        Optional<XYPair> solution5 = arrangedProbability2.findSolutionForNonSquare(1, 2, 1000000000000L);
        Optional<XYPair> solution6 = arrangedProbability2.findSolutionForNonSquare(1, 1000, 1000);
        Optional<XYPair> solution7 = arrangedProbability2.findSolutionForNonSquare(9999, 10000, 2000);
        Optional<XYPair> solution8 = arrangedProbability2.findSolutionForNonSquare(1, 11, 2000);
        Optional<XYPair> solution9 = arrangedProbability2.findSolutionForNonSquare(127, 128, 200);
        Optional<XYPair> solution10 = arrangedProbability2.findSolutionForNonSquare(1, 10000000, 1);
        Optional<XYPair> solution11 = arrangedProbability2.findSolutionForNonSquare(97, 100, 100L);
        Optional<XYPair> solution12 = arrangedProbability2.findSolutionForNonSquare(97, 1000, 100L);
        Optional<XYPair> solution13 = arrangedProbability2.findSolutionForNonSquare(97, 10000, 100L);
        Optional<XYPair> solution14 = arrangedProbability2.findSolutionForNonSquare(97, 100000, 100L);
        Optional<XYPair> solution15 = arrangedProbability2.findSolutionForNonSquare(97, 1000000, 100L);
        Optional<XYPair> solution16 = arrangedProbability2.findSolutionForNonSquare(97, 10000000, 100L);
        Optional<XYPair> solution17 = arrangedProbability2.findSolutionForNonSquare(97, 101, 100L);

        long time2 = System.currentTimeMillis();

        System.out.println("Solution for 3 8 1000: " + solution1);
        System.out.println("Solution for 1 2 100: " + solution2);
        System.out.println("Solution for 1 2 5: " + solution3);
        System.out.println("Solution for 1 2 2: " + solution4);
        System.out.println("Solution for 1 2 10^12: " + solution5);
        System.out.println("Solution for 1 1000 1000: " + solution6);
        System.out.println("Solution for 9999 10000 2000: " + solution7);
        System.out.println("Solution for 1 11 2000: " + solution8);
        System.out.println("Solution for 127 128 200: " + solution9);
        System.out.println("Solution for 1 1000000 2000: " + solution10);
        System.out.println("Solution for 97 100 10^15: " + solution11);
        System.out.println("Solution for 97 1000 10^15: " + solution12);
        System.out.println("Solution for 97 10000 10^15: " + solution13);
        System.out.println("Solution for 97 100000 10^15: " + solution14);
        System.out.println("Solution for 97 1000000 10^15: " + solution15);
        System.out.println("Solution for 97 10000000 10^15: " + solution16);
        System.out.println("Solution for 97 101 10^15: " + solution17);

        System.out.println("Time in ms: " + (time2 - time1));
    }

    private static void tests2() {
        int low = 1000, high = 10000;
        PrimesRangeSieve rangeSieve = new PrimesRangeSieve(low, high);
        long limit = 1000;
        List<ArrangedProbabilityInput> inputs = new ArrayList<>();
        for(int i = low; i <= high; i++) {
            if(rangeSieve.isPrime(i)) {
               inputs.add(new ArrangedProbabilityInput(i, 2L * i + 1, limit));
            }
        }

        System.out.println("Input size: " + inputs.size());
        writeToFile(inputs);

        long time1 = System.currentTimeMillis();
        ArrangedProbability2 arrangedProbability = new ArrangedProbability2(inputs);
        List<ArrangedProbabilityOutput> outputs = arrangedProbability.findSolutions();
        long time2 = System.currentTimeMillis();

        for(int i = 0; i < inputs.size(); i++) {
            ArrangedProbabilityInput input = inputs.get(i);
            ArrangedProbabilityOutput output = outputs.get(i);
            System.out.println(input + " => " + output);
        }

        System.out.println("Time in ms: " + (time2 - time1));
    }

    private static void tests3() {
        List<ArrangedProbabilityInput> inputs = new ArrayList<>();
        long offset = 5;
        for(long i = 1; i < 10000; i++) {
            long P = i * i;
            long Q = (i + offset) * (i + offset);
            inputs.add(new ArrangedProbabilityInput(P, Q, 1));
        }

        long time1 = System.currentTimeMillis();
        ArrangedProbability2 arrangedProbability = new ArrangedProbability2(inputs);
        List<ArrangedProbabilityOutput> outputs = arrangedProbability.findSolutions();
        long time2 = System.currentTimeMillis();

        for(int i = 0; i < inputs.size(); i++) {
            ArrangedProbabilityInput input = inputs.get(i);
            ArrangedProbabilityOutput output = outputs.get(i);
            System.out.println(input + " => " + output);
        }

        System.out.println("Time in ms: " + (time2 - time1));
    }

    private static void tests4() {
        long limit = 1000;
        for(long i = 1; i < limit; i++) {
            for(long j = i + 1; j <= limit; j++) {
                long P = i * i;
                long Q = j * j;
                long gcd = MathUtils.GCD(P, Q);
                P = P / gcd;
                Q = Q / gcd;
                Optional<XYPair> solution = findSolutionForSquare(P, Q, 2);
                if(solution.isPresent()) {
                   System.out.println(P + "/" + Q + " => " + solution.get());
                }
            }
        }
    }

    private static void tests5() {
        List<ArrangedProbabilityInput> inputs = new ArrayList<>();
        List<ArrangedProbabilityOutput> outputs = new ArrayList<>();
        Random random = new Random();
        int drawLimit = 10000000;
        int k = 0;
        while (outputs.size() != 1000) {
           long i = random.nextInt(drawLimit);
           long j = random.nextInt(drawLimit);
           long Q = Math.max(i, j);
           long P = Math.min(i, j);
           if(P == Q) {
              Q++;
           }
           /*ArrangedProbabilityInput input = new ArrangedProbabilityInput(P, Q, 1000);
           inputs.add(input);*/
           ArrangedProbabilityInput input = new ArrangedProbabilityInput(P, Q, 1000);
           ArrangedProbability2 arrangedProbability = new ArrangedProbability2(List.of(input));
           ArrangedProbabilityOutput solution = arrangedProbability.findSolutions().get(0);
           if ((solution.solution != null)) {
               inputs.add(input);
               outputs.add(solution);
               if (++k % 25 == 0) {
                   System.out.println("Solutions size: " + outputs.size());
               }
           }
        }

        writeToFile(inputs);

        /*ArrangedProbability2 arrangedProbability = new ArrangedProbability2(inputs);
        List<ArrangedProbabilityOutput> outputs = arrangedProbability.findSolutions();*/

        for(int i = 0; i < inputs.size(); i++) {
            ArrangedProbabilityInput input = inputs.get(i);
            ArrangedProbabilityOutput output = outputs.get(i);
            System.out.println(input + " => " + output);
        }

    }

    private static void writeToFile(List<ArrangedProbabilityInput> inputs) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(INPUT_FILE_PATH))) {
            for(ArrangedProbabilityInput input : inputs) {
                writer.write(input.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e);
        }
    }

    public List<ArrangedProbabilityOutput> findSolutions() {
        List<ArrangedProbabilityOutput> result = new ArrayList<>(inputList.size());
        for(ArrangedProbabilityInput input : inputList) {
            long P = input.P;
            long Q = input.Q;
            long limit = input.limit;

            long g = MathUtils.GCD(P, Q);
            P = P / g;
            Q = Q / g;

            XYPair solution = findSolution(P, Q, limit);
            result.add(new ArrangedProbabilityOutput(solution));
        }
        return result;
    }

    private XYPair findSolution(long P, long Q, long limit) {
        long D = P * Q;
        if(isPerfectSquare(D)) {
           return findSolutionForSquare(P, Q, limit).orElse(null);
        }
        else {
           return findSolutionForNonSquare(P, Q, limit).orElse(null);
        }
    }

    private Optional<XYPair> findSolutionForNonSquare(long P, long Q, long limit) {
        //ReducedHyperbolicSolver solver = new ReducedHyperbolicSolver();
        List<XYPair> basicSolutions = solver.getSolutions(Q, 0, -P, P - Q);
        Set<XYPair> uniqueSolutions = new LinkedHashSet<>();
        for(XYPair basicSolution : basicSolutions) {
            BigInteger x = basicSolution.getX().abs();
            BigInteger y = basicSolution.getY().abs();
            //even basic solutions will never produce correct solutions
            if(x.mod(BigInteger.TWO).longValue() != 0 || y.mod(BigInteger.TWO).longValue() != 0) {
                uniqueSolutions.add(new XYPair(x, y));
            }
        }

        uniqueSolutions.add(new XYPair(BigInteger.ONE, BigInteger.ONE));

        XYPair foundPair = new XYPair(BigInteger.valueOf(Long.MAX_VALUE), BigInteger.valueOf(Long.MAX_VALUE));
        BigInteger threshold = BigInteger.valueOf(limit);
        boolean foundAny = false;

        //Find among basic solutions
        for(XYPair uniqueSolution : uniqueSolutions) {
            BigInteger X = uniqueSolution.getX();
            BigInteger Y = uniqueSolution.getY();
            if(solutionSatisfies(X, Y)) {
                BigInteger y = adjustValue(Y);
                if(y.compareTo(threshold) > 0 && y.compareTo(foundPair.getY()) < 0) {
                  //  System.out.println("Found among basic solutions: " + uniqueSolution);
                    BigInteger x = adjustValue(X);
                    foundPair = new XYPair(x, y);
                    foundAny = true;
                }
            }
        }

        if(foundAny) {
           return Optional.of(foundPair);
        }

        long D = 4 * P * Q;

        XYPair leastSolution = PellsEquationSolver.leastPositivePellsFourSolution(D, PELLS_CONVERGENT_THRESHOLD).orElse(null);
        if(leastSolution == null) {
           return Optional.empty();
        }

        BigInteger u0 = leastSolution.getX();
        BigInteger v0 = leastSolution.getY();

        List<ReducedHyperbolicIterator> reducedHyperbolicIterators = new ArrayList<>();
        for(XYPair uniqueSolution : uniqueSolutions) {
            BigInteger x0 = uniqueSolution.getX();
            BigInteger y0 = uniqueSolution.getY();

            ReducedHyperbolicIterator positiveIterator = ReducedHyperbolicIterator.ofPositive(x0, y0, u0, v0, Q, -P);
            ReducedHyperbolicIterator negativeIterator = ReducedHyperbolicIterator.ofNegative(x0, y0, u0, v0, Q, -P);

            reducedHyperbolicIterators.add(positiveIterator);
            reducedHyperbolicIterators.add(negativeIterator);
        }

        for(ReducedHyperbolicIterator iterator : reducedHyperbolicIterators) {
            boolean found = false;
            while (!found) {
                XYPair solution = iterator.next();
                BigInteger X = solution.getX().abs();
                BigInteger Y = solution.getY().abs();
                if(solutionSatisfies(X, Y)) {
                    BigInteger y = adjustValue(Y);
                    if(y.compareTo(threshold) > 0) {
                        if(y.compareTo(foundPair.getY()) < 0) {
                            BigInteger x = adjustValue(X);
                            foundPair = new XYPair(x, y);
                            foundAny = true;
                        }
                        found = true;
                    }
                }
                else if(Y.compareTo(ULTIMATE_THRESHOLD) > 0) {
                    break;
                }
            }
        }

        return foundAny ? Optional.of(foundPair) : Optional.empty();
    }

    private static BigInteger adjustValue(BigInteger value) {
        return value.add(BigInteger.ONE).divide(BigInteger.TWO);
    }

    private static boolean solutionSatisfies(BigInteger X, BigInteger Y) {
        return X.mod(BigInteger.TWO).longValue() == 1 && Y.mod(BigInteger.TWO).longValue() == 1
                && Y.compareTo(X) > 0;
    }

    private static Optional<XYPair> findSolutionForSquare(long P, long Q, long limit) {
        long F = Q - P;
        List<Long> divisors = Divisors.listProperDivisors(F);

        long sqrtQ = (long)Math.sqrt(Q);
        long sqrtP = (long)Math.sqrt(P);

        long a = 2 * sqrtQ;
        long b = 2 * sqrtP;
        long c1 = sqrtQ + sqrtP;
        long c2 = sqrtQ - sqrtP;

        long x = Long.MAX_VALUE, y = Long.MAX_VALUE;
        boolean found = false;

        for(long div : divisors) {
            long div2 = F / div;
            long d1 = div + c1 + div2 + c2;
            long d2 = div + c1 - div2 - c2;
            if(d2 > 0 && d1 % (2 * a) == 0 && d2 % (2 * b) == 0) {
                long newX = d1 / (2 * a);
                long newY = d2 / (2 * b);
                if(newY > newX && newY > limit && newY < y) {
                    x = newX;
                    y = newY;
                    found = true;
                }
            }
        }
        return found ? Optional.of(new XYPair(BigInteger.valueOf(x), BigInteger.valueOf(y))) : Optional.empty();
    }

    private static boolean isPerfectSquare(long d) {
        double exactSqrt = Math.sqrt(d);
        return exactSqrt == (long)exactSqrt;
    }

    private static boolean satisfiesEquation(BigInteger x, BigInteger y, long P, long Q) {
        BigInteger PBI = BigInteger.valueOf(P);
        BigInteger QBI = BigInteger.valueOf(Q);
        BigInteger value1 = x.multiply(x.subtract(BigInteger.ONE)).multiply(QBI);
        BigInteger value2 = y.multiply(y.subtract(BigInteger.ONE)).multiply(PBI);
        System.out.println(value1);
        System.out.println(value2);
        return value1.equals(value2);
    }
}
