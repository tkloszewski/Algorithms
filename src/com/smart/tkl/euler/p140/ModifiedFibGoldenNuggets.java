package com.smart.tkl.euler.p140;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

public class ModifiedFibGoldenNuggets {

    /*Ag(x) = (x + 3x^2) / (1 - x - x^2)
     Delta = 5n^2 + 14n + 1
    * */
    public static void main(String[] args) {
        long[][] baseSolutions = {
                {0, 1},
                {0, -1},
                {2, 7},
                {2, -7},
                {-3, -2},
                {-3, 2},
                {-4, 5},
                {-4, -5}
        };

        Set<BigInteger> solutions = new TreeSet<>();

        for(long[] baseSolution : baseSolutions) {
            BigInteger x = BigInteger.valueOf(baseSolution[0]);
            BigInteger y = BigInteger.valueOf(baseSolution[1]);
            for(int i = 0; i < 30; i++) {
                if(x.compareTo(BigInteger.ZERO) > 0) {
                   solutions.add(x);
                }
                BigInteger prevX = x;
                x = BigInteger.valueOf(-9)
                        .multiply(prevX)
                        .add(BigInteger.valueOf(-4).multiply(y))
                        .subtract(BigInteger.valueOf(14));

                y = BigInteger.valueOf(-20)
                        .multiply(prevX)
                        .add(BigInteger.valueOf(-9).multiply(y))
                        .subtract(BigInteger.valueOf(28));
            }
        }

        System.out.println("Solutions: " + solutions);

        BigInteger sum = BigInteger.ZERO;
        int i = 0;
        for(BigInteger solution : solutions) {
            sum = sum.add(solution);
            if(++i >= 30) {
               break;
            }
        }

        System.out.println("Sum equals: " + sum);
    }


}
