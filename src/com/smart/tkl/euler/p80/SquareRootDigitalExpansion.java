package com.smart.tkl.euler.p80;

import com.smart.tkl.utils.BigDecimalFraction;
import com.smart.tkl.utils.PeriodicFraction;
import com.smart.tkl.utils.SquareRootPeriodicFractionGenerator;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class SquareRootDigitalExpansion {

    private final int precision;
    private final BigDecimal epsilon;
    private final int limit;
    private final int numOfDigits;

    public SquareRootDigitalExpansion(int precision, int limit, int numOfDigits) {
        this.precision = precision;
        this.epsilon = BigDecimal.valueOf(10).pow(-precision, new MathContext(precision, RoundingMode.HALF_UP));;
        this.limit = limit;
        this.numOfDigits = numOfDigits;
    }

    public static void main(String[] args) {
        SquareRootDigitalExpansion digitalExpansion = new SquareRootDigitalExpansion(100, 100, 100);
        long time1 = System.currentTimeMillis();
        int sum = digitalExpansion.sumOfDigits();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum of digits: " + sum + " in ms: " +  (time2 - time1));
    }


    public int sumOfDigits() {
        boolean[] squares = new boolean[limit + 1];
        for(int i = 1; i * i  <= limit; i++) {
            squares[i * i] = true;
        }

        int result = 0;

        for(int i = 1; i <= limit; i++) {
            if(!squares[i]) {
                SquareRootPeriodicFractionGenerator generator = new SquareRootPeriodicFractionGenerator(i);
                PeriodicFraction periodicFraction = generator.generate();
                BigDecimal approx = getSquareRootApproximation(periodicFraction);
                int sum = sumOfDigits(approx);
                result += sum;
            }
        }

        return result;
    }

    public int sumOfDigits(BigDecimal bigDecimal) {
        String[] digitParts = bigDecimal.toString().substring(0, this.numOfDigits + 1).split("\\.");
        int result = digitParts[0].charAt(0) - '0';

        for(int i = 0; i < digitParts[1].length(); i++) {
            result += digitParts[1].charAt(i) - '0';
        }
        return result;
    }

    private BigDecimal getSquareRootApproximation(PeriodicFraction periodicFraction) {
        BigDecimal[] previousP = new BigDecimal[]{BigDecimal.ONE, BigDecimal.valueOf(periodicFraction.getBase())};
        BigDecimal[] previousQ = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE};
        BigDecimal p = previousP[1];
        BigDecimal q = previousQ[1];

        BigDecimal previousValue = BigDecimal.ZERO;
        BigDecimal value = new BigDecimalFraction(p, q).toValue(this.precision + 2);
        BigDecimal diff = value.subtract(previousValue).abs();

        int i = 0;
        List<Integer> seq = periodicFraction.getSequence();

        while (diff.compareTo(epsilon) > 0) {
            p = BigDecimal.valueOf(seq.get(i)).multiply(previousP[1]).add(previousP[0]);
            q = BigDecimal.valueOf(seq.get(i)).multiply(previousQ[1]).add(previousQ[0]);

            previousP[0] = previousP[1];
            previousP[1] = p;
            previousQ[0] = previousQ[1];
            previousQ[1] = q;

            previousValue = value;
            value = new BigDecimalFraction(p, q).toValue(this.precision + 2);
            diff = value.subtract(previousValue).abs();

            i = (i + 1) % seq.size();
        }

        return value;
    }



}
