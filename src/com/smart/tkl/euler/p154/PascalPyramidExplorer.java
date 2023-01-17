package com.smart.tkl.euler.p154;

import com.smart.tkl.utils.MathUtils;
import com.smart.tkl.utils.PrimeFactor;
import java.util.List;
import java.util.stream.Collectors;

public class PascalPyramidExplorer {

    private final int level;
    private final long multiple;

    private int[] primeFactors;
    private int[][] factorialPrimeFactors;

    public PascalPyramidExplorer(int level, long multiple) {
        this.level = level;
        this.multiple = multiple;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int level = 200000;
        long multiple = 1000000000000L;
        PascalPyramidExplorer pascalPyramidExplorer = new PascalPyramidExplorer(level, multiple);
        long count = pascalPyramidExplorer.countFast();
        long time2 = System.currentTimeMillis();
        System.out.println("Count of multiples: " + count);
        System.out.println("Time consumed in ms: " + (time2 - time1));
    }

    public long countFast() {
        long count = 0;

        List<PrimeFactor> list = MathUtils.listPrimeFactors(multiple);
        List<Integer> factors = list.stream().map(PrimeFactor::getFactor).collect(Collectors.toList());

        this.primeFactors = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            this.primeFactors[i] = list.get(i).getPow();
        }

        this.factorialPrimeFactors = initFactorialPowers(factors);

        boolean oddNumOfElements = (this.level + 1) % 2 == 1;
        int[][] lastRowPowers = new int[this.level + 1][this.primeFactors.length];
        boolean[] lastRowMultiples = new boolean[this.level + 1];
        for(int i = 0; i <= this.level / 2; i++) {
            int[] factorPowers = getBinomialPowers(this.level, i);
            if(isMultiple(factorPowers)) {
               lastRowMultiples[i] = true;
               boolean isFirst = i == 0;
               boolean isMiddle = oddNumOfElements && i == this.level / 2;
               int inc = getInc(isFirst, isMiddle);
               count += inc;
            }
            lastRowPowers[i] = factorPowers;
        }

        for(int i = 1; i <= this.level / 3; i++) {
            int row = this.level - i;
            boolean oddRow = (row + 1) % 2 == 1;
            if(lastRowMultiples[i]) {
               int inc = getIncForRow(row, i, oddRow);
               count += inc;
            }
            else {
                for(int j = i; j <= row / 2; j++) {
                    boolean isMultiple = true;
                    for(int idx = 0; idx < primeFactors.length; idx++) {
                        int lastRowPower = lastRowPowers[i][idx];
                        int binomialPower = getBinomialPower(row, j, idx);
                        if(lastRowPower + binomialPower < primeFactors[idx]) {
                           isMultiple = false;
                           break;
                        }
                    }
                    if(isMultiple) {
                       boolean isFirst = j == i;
                       boolean isMiddle = oddRow && j == row / 2;
                       int inc = getInc(isFirst, isMiddle);
                       count += inc;
                    }
                }
            }
        }

        return count;
    }

    private int[][] initFactorialPowers(List<Integer> factors) {
        int[][] result = new int[this.level + 1][factors.size()];
        int[][] factorPowers = new int[this.level + 1][factors.size()];

        for(int n = 0; n <= level; n++) {
            int[] powers = new int[factors.size()];
            for(int i = 0; i < factors.size(); i++) {
                int primeFactor = factors.get(i);
                int freq = 0;
                long value = n;
                while (value > 0 && value % primeFactor == 0) {
                    freq++;
                    value = value / primeFactor;
                }
                powers[i] = freq;
            }
            factorPowers[n] = powers;
        }

        result[0] = factorPowers[0];
        int[] previousFactorialPowers = factorPowers[0];

        for(int n = 1; n <= level; n++) {
            int[] factorialPowers = new int[factors.size()];
            int[] currentPowers = factorPowers[n];
            for(int i = 0; i < factors.size(); i++) {
                factorialPowers[i] = previousFactorialPowers[i] + currentPowers[i];
            }
            result[n] = factorialPowers;
            previousFactorialPowers = factorialPowers;
        }

        return result;
    }

    private boolean isMultiple(int[] powers) {
        for(int i = 0; i < this.primeFactors.length; i++) {
            if(this.primeFactors[i] > powers[i]) {
               return false;
            }
        }
        return true;
    }

    private int[] getBinomialPowers(int n, int k) {
        int[] result = new int[primeFactors.length];
        for(int i = 0; i < primeFactors.length; i++) {
            result[i] = factorialPrimeFactors[n][i] -
                    (factorialPrimeFactors[k][i] + factorialPrimeFactors[n - k][i]);
        }
        return result;
    }

    private int getBinomialPower(int n, int k, int index) {
        return factorialPrimeFactors[n][index] -
                (factorialPrimeFactors[k][index] + factorialPrimeFactors[n - k][index]);
    }

    private int getInc(boolean isFirst, boolean isMiddle) {
        return (isFirst && isMiddle) ? 1 : (isFirst || isMiddle) ? 3 : 6;
    }

    private int getIncForRow(int row, int startIdx, boolean oddRow) {
        int endIdx = row / 2;
        if(startIdx == endIdx) {
           return oddRow ? 1 : 3;
        }
        int result = 0;
        int inBetweenNumbers = endIdx - startIdx - 1;
        result += inBetweenNumbers * 6;
        result += 3;
        result += (oddRow ? 3 : 6);
        return result;
    }
}
