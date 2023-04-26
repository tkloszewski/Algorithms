package com.smart.tkl.euler.p121;

import com.smart.tkl.lib.combinatorics.CombinatoricsUtils;
import com.smart.tkl.lib.utils.MathUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Set;

public class DiscGamePrizeFund {

    private final int turns;

    public DiscGamePrizeFund(int turns) {
        this.turns = turns;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        DiscGamePrizeFund discGamePrizeFund = new DiscGamePrizeFund(15);
        int maxPrize = discGamePrizeFund.resolveMaxPrize();
        long time2 = System.currentTimeMillis();
        System.out.println("Max prize: " + maxPrize);
        System.out.println("Solution found in ms: " + (time2 - time1));
    }

    public int resolveMaxPrize() {
        long winingProbabilityDenominator = MathUtils.factorial(turns + 1);
        long winingProbabilityNumerator = 1;

        Set<Integer> allTurns = new HashSet<>();
        for(int i = 1; i <= turns; i++) {
            allTurns.add(i);
        }

        int maxRedDisksChosen = turns % 2 == 0 ? turns / 2 - 1 : turns / 2;
        for(int redDiskCount = 1; redDiskCount <= maxRedDisksChosen; redDiskCount++) {
            Set<int[]> winingCombinations = CombinatoricsUtils.combinations(allTurns, redDiskCount);
            for(int[] winingCombination : winingCombinations) {
                int numeratorPart = 1;
                for(int winingPosition : winingCombination) {
                    numeratorPart *= winingPosition;
                }
                winingProbabilityNumerator += numeratorPart;
            }
        }

        return calculateMaxPrize(winingProbabilityDenominator, winingProbabilityNumerator);
    }

    private int calculateMaxPrize(long winingProbabilityDenominator, long winingProbabilityNumerator) {
        long losingProbabilityNumerator = winingProbabilityDenominator - winingProbabilityNumerator;
        return (int)Math.ceil(BigDecimal.valueOf(losingProbabilityNumerator)
                .divide(BigDecimal.valueOf(winingProbabilityNumerator), MathContext.DECIMAL128).doubleValue());
    }

}
