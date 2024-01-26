package com.smart.tkl.euler.p33;

import com.smart.tkl.lib.utils.Fraction;
import com.smart.tkl.lib.utils.MathUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DigitCancellingFractions {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long[] sums = getSumOfNumeratorsAndDenominators(4, 1);
        long time2 = System.currentTimeMillis();
        System.out.println(Arrays.toString(sums));
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long[] getSumOfNumeratorsAndDenominators(int digitsCount, int cancellingDigitsCount) {
        int left = digitsCount - cancellingDigitsCount;
        int maxDenominator = (int)Math.pow(10, left) - 1;
        int min = (int)Math.pow(10, digitsCount - 1) + 1;
        int max = (int)Math.pow(10, digitsCount) - 1;

        long numeratorSum = 0, denominatorSum = 0;

        for(int denominator = 2; denominator <= maxDenominator; denominator++) {
            List<List<Integer>> result = getNumerators(denominator, cancellingDigitsCount, min, max);
            List<Integer> numerators = result.get(0);
            List<Integer> denominators = result.get(1);

            if (!numerators.isEmpty()) {
                long sum1 = numerators.stream().reduce(0, Integer::sum);
                numeratorSum += sum1;
                long sum2 = denominators.stream().reduce(0, Integer::sum);
                denominatorSum += sum2;
            }
        }

        return new long[]{numeratorSum, denominatorSum};
    }

    private static List<List<Integer>> getNumerators(int denominator, int cancellingDigitsCount, int min, int max) {
        List<Integer> numerators = new ArrayList<>();
        List<Integer> denominators = new ArrayList<>();

        int kMin = (int) Math.ceil( (double) min / denominator);
        int kMax = max / denominator;

        for(int numerator = denominator - 1; numerator >= 1; numerator--) {
            if(kMax * numerator < min) {
                break;
            }

            if(MathUtils.GCD(numerator, denominator) == 1) {
                for(int k = kMax; k >= kMin; k--) {
                    if(k * numerator < min) {
                        break;
                    }

                    int targetNumerator = k * numerator;
                    int targetDenominator = k * denominator;

                    DigitStats digitStats1 = getDigits(targetNumerator);
                    DigitStats digitStats2 = getDigits(targetDenominator);

                    int commonDigits = commonDigits(digitStats1.freq, digitStats2.freq);

                    if(commonDigits < cancellingDigitsCount) {
                        continue;
                    }

                    Fraction fraction = new Fraction(numerator, denominator, false);
                    if(isCancellingFraction(fraction, digitStats1, digitStats2, cancellingDigitsCount)) {
                        numerators.add(targetNumerator);
                        denominators.add(targetDenominator);
                    }
                }
            }
        }

        return List.of(numerators, denominators);
    }

    private static int commonDigits(int[] digitsFreq1, int[] digitFreq2) {
        int common = 0;
        for(int digit = 0; digit <= 9; digit++) {
            if(digitsFreq1[digit] > 0 && digitFreq2[digit] > 0) {
                common += Math.max(digitsFreq1[digit], digitFreq2[2]);
            }
        }
        return common;
    }

    private static boolean isCancellingFraction(Fraction fraction, DigitStats numeratorStats, DigitStats denominatorStats, int cancellingLeft) {
        int[] numeratorFreq = numeratorStats.freq;
        int[] denominatorFreq = denominatorStats.freq;
        List<Integer> numeratorDigits = numeratorStats.digits;
        List<Integer> denominatorDigits = denominatorStats.digits;

        for(int i = 0; i < numeratorDigits.size(); i++) {
            int digit1 = numeratorDigits.get(i);
            if(digit1 == 0) {
               continue;
            }

            for(int k = 0; k < denominatorFreq[digit1]; k++) {
                for (int j = 0; j < denominatorDigits.size(); j++) {
                    int digit2 = denominatorDigits.get(j);
                    if (digit1 == digit2) {

                        numeratorFreq[digit1]--;
                        numeratorDigits.remove(i);
                        denominatorFreq[digit2]--;
                        denominatorDigits.remove(j);

                        if (cancellingLeft == 1) {
                            int newNumerator = toValue(numeratorDigits);
                            int newDenominator = toValue(denominatorDigits);
                            if (newDenominator != 0) {
                                Fraction cancelledFraction = new Fraction(newNumerator, newDenominator);
                                if(cancelledFraction.equals(fraction)) {
                                    return true;
                                }
                            }
                        }
                        else {
                            boolean cancellingFraction = isCancellingFraction(fraction,
                                    new DigitStats(numeratorFreq, numeratorDigits),
                                    new DigitStats(denominatorFreq, denominatorDigits),
                                    cancellingLeft - 1);

                            if(cancellingFraction) {
                                return true;
                            }
                        }

                        numeratorFreq[digit1]++;
                        numeratorDigits.add(i, digit1);
                        denominatorFreq[digit2]++;
                        denominatorDigits.add(j, digit2);
                    }
                }
            }
        }

        return false;
    }

    private static int toValue(List<Integer> digits) {
        int result = 0;
        for(int i = 0, pow = 1; i < digits.size(); i++, pow *= 10) {
            result += digits.get(i) * pow;
        }
        return result;
    }

    private static DigitStats getDigits(int n) {
        int[] freq = new int[10];
        List<Integer> digits = new ArrayList<>();
        while (n > 0) {
            int digit = n % 10;
            freq[digit]++;
            digits.add(digit);
            n = n / 10;
        }
        return new DigitStats(freq, digits);
    }

    private static class DigitStats {
        int[] freq;
        List<Integer> digits;

        public DigitStats(int[] freq, List<Integer> digits) {
            this.freq = freq;
            this.digits = digits;
        }
    }
}
