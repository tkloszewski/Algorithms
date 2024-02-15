package com.smart.tkl.euler.p80;

import com.smart.tkl.lib.utils.MathUtils;
import com.smart.tkl.lib.utils.PrimeFactor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class SquareRootDigitalExpansion2 {

    private final BigDecimal epsilon;
    private final int limit;
    private final int numOfDigits;
    private final MathContext mathContext;

    public SquareRootDigitalExpansion2(int precision, int limit, int numOfDigits) {
        this.epsilon = BigDecimal.valueOf(10).pow(-precision - 1, new MathContext(precision , RoundingMode.HALF_UP));
        this.limit = limit;
        this.numOfDigits = numOfDigits;
        this.mathContext = new MathContext(numOfDigits + 10, RoundingMode.FLOOR);
    }

    public static void main(String[] args) {
        int numOfDigits = 1000;
        int limit = 1000;
        SquareRootDigitalExpansion2 digitalExpansion = new SquareRootDigitalExpansion2(numOfDigits, limit, numOfDigits);
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

        BigDecimal[] squareRoots = new BigDecimal[limit + 1];
        for(int i = 1; i <= limit; i++) {
            if(!squares[i]) {
                List<PrimeFactor> primeFactors = MathUtils.listPrimeFactors(i);
                BigDecimal approx;
                if(primeFactors.size() == 1 && primeFactors.get(0).getPow() == 1) {
                    approx = sqrt(i);
                }
                else {
                    approx = BigDecimal.ONE;
                    for(PrimeFactor primeFactor : primeFactors) {
                        int pow = primeFactor.getPow();
                        if(pow / 2 > 0) {
                           approx = approx.multiply(BigDecimal.valueOf(primeFactor.getFactor()).pow(pow / 2), mathContext);
                        }
                        if(pow % 2 == 1) {
                           approx = approx.multiply(squareRoots[(int)primeFactor.getFactor()], mathContext);
                        }
                    }
                }

                squareRoots[i] = approx;

                int sum = sumOfDigits(approx);
                result += sum;
            }
        }


        return result;
    }

    public int sumOfDigits(BigDecimal bigDecimal) {
        String[] digitParts = bigDecimal.toString().substring(0, this.numOfDigits + 1).split("\\.");
        int result = 0;
        for(char firstPartChar : digitParts[0].toCharArray()) {
            result += firstPartChar - '0';
        }

        for(int i = 0; i < digitParts[1].length(); i++) {
            result += digitParts[1].charAt(i) - '0';
        }
        return result;
    }

    private BigDecimal sqrt(int n) {
        BigDecimal initial = BigDecimal.valueOf(n);
        BigDecimal a = BigDecimal.ONE;
        BigDecimal b = BigDecimal.valueOf(n);

        while (a.subtract(b).abs().compareTo(epsilon) >= 0) {
            a = a.add(b).divide(BigDecimal.valueOf(2), mathContext);
            b = initial.divide(a, mathContext);
        }

        return a.add(b).divide(BigDecimal.valueOf(2), mathContext);
    }
}
