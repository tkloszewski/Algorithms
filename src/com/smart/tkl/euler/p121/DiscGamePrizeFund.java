package com.smart.tkl.euler.p121;

import com.smart.tkl.lib.combinatorics.CombinatoricsUtils;
import com.smart.tkl.lib.utils.MathUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DiscGamePrizeFund {

    private final int turns;
    private final BigInteger[][] numeratorMemo;

    public DiscGamePrizeFund(int turns) {
        this.turns = turns;
        this.numeratorMemo = new BigInteger[turns + 1][turns + 1];
    }

    public static void main(String[] args) {
        int maxTurns = 15;
        long time1 = System.currentTimeMillis();
        DiscGamePrizeFund discGamePrizeFund = new DiscGamePrizeFund(maxTurns);
        BigInteger maxPrize = discGamePrizeFund.resolveMaxPrize();
        long time2 = System.currentTimeMillis();
        System.out.println("Max prize: " + maxPrize);
        System.out.println("Solution found in ms: " + (time2 - time1));
    }

    public BigInteger resolveMaxPrize() {
        return resolveMaxPrize(this.turns);
    }

    public BigInteger resolveMaxPrize(int turns) {
        BigInteger numerator = calcNumerator(turns);
        BigInteger denominator = calcDenominator(turns);
        return denominator.divide(numerator);
    }

    private BigInteger calcDenominator(int turns) {
        BigInteger denominator = BigInteger.ONE;
        for(int i = 2; i <= turns + 1; i++) {
            denominator = denominator.multiply(BigInteger.valueOf(i));
        }
        return denominator;
    }

    private BigInteger calcNumerator(int turns) {
        BigInteger numerator = BigInteger.ONE;
        int maxRedDisksChosen = turns % 2 == 0 ? turns / 2 - 1 : turns / 2;
        for(int redDisks = 1; redDisks <= maxRedDisksChosen; redDisks++) {
            BigInteger combinationNumerator = calcNumerator(turns, redDisks);
            numerator = numerator.add(combinationNumerator);
        }
        return numerator;
    }

    private BigInteger calcNumerator(int n, int k) {
        BigInteger result = numeratorMemo[n][k];
        if(result != null) {
           return result;
        }

        if(n == k) {
           result = BigInteger.ONE;
           for(int i = 2; i <= n; i++) {
               result = result.multiply(BigInteger.valueOf(i));
           }
        }
        else if(k == 0) {
           result = BigInteger.ONE;
        }
        else if(k == 1) {
           BigInteger N = BigInteger.valueOf(n);
           result = N.multiply(BigInteger.ONE.add(N)).divide(BigInteger.TWO);
        }
        else {
          BigInteger r1 = calcNumerator(n - 1, k- 1);
          BigInteger r2 = calcNumerator(n - 1, k);
          result = BigInteger.valueOf(n).multiply(r1).add(r2);
        }

        numeratorMemo[n][k] = result;
        return result;
    }
}
