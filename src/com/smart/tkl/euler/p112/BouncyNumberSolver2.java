package com.smart.tkl.euler.p112;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BouncyNumberSolver2 {

    private final int digitLimit;
    private final long[][] increaseTab;
    private final long[][] decreaseTab;
    private final long[][] nonBouncyTab;

    public BouncyNumberSolver2(int digitLimit) {
        this.digitLimit = digitLimit;
        this.increaseTab = new long[digitLimit + 1][11];
        this.decreaseTab = new long[digitLimit + 1][11];
        this.nonBouncyTab = new long[digitLimit + 1][11];

        for(int i = 0; i <= 9 ; i++) {
            increaseTab[1][i] = 1;
            decreaseTab[1][i] = 1;
            nonBouncyTab[1][i] = 1;
        }

        fillValues();
    }

    public static void main(String[] args) {
        Random random = new Random();
        long time1 = System.currentTimeMillis();
        BouncyNumberSolver2 bouncyNumberSolver = new BouncyNumberSolver2(30);
        for(int i = 0; i < 10000; i++) {
            long n = random.nextLong();
            n = Math.min(n, Long.MAX_VALUE - 10000);
            long m = n + (random.nextLong() % 10000);
            if(m == n) {
               m++;
            }
            bouncyNumberSolver.find(n, m);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));

        BigInteger foundNumber = bouncyNumberSolver.find(999999999999999999L, 1000000000000000000L);
        System.out.println("Found number: " + foundNumber);

        BigInteger bouncy = bouncyNumberSolver.countBouncy(foundNumber);
        System.out.println("Ratio: " + new BigDecimal(bouncy).divide(new BigDecimal(foundNumber), MathContext.DECIMAL128));
    }

    public BigInteger find(long n, long m) {
        return find(BigInteger.valueOf(n), BigInteger.valueOf(m));
    }

    public BigInteger find(BigInteger n, BigInteger m) {
        BigInteger b = BigInteger.ONE;
        BigInteger v = BigInteger.valueOf(101);
        BigInteger denominator = m.subtract(n);
        BigInteger target = n.multiply(v);

        while (b.multiply(m).compareTo(target) < 0) {
            BigInteger k = target.subtract(b.multiply(m)).divide(denominator);
            if(k.signum() == 0) {
               k = k.add(BigInteger.ONE);
            }
            v = v.add(k);
            b = countBouncy(v);
            target = n.multiply(v);
        }

        return v;
    }

    public BigInteger countBouncy(BigInteger n) {
        long nonBouncy = count(n);
        return n.subtract(BigInteger.valueOf(nonBouncy));
    }

    public long countBouncy(long n) {
        long nonBouncyCount = count(n);
        return n - nonBouncyCount;
    }

    public long count(long n) {
        if(n < 100) {
           return n;
        }

        List<Integer> digitList = new ArrayList<>();
        long value = n;
        int digitCount = 0;
        while (value > 0) {
            int digit = (int)(value % 10);
            digitList.add(digit);
            value = value / 10;
            digitCount++;
        }

        int[] digits = new int[digitCount];
        for(int i = 0; i < digitCount; i++) {
            digits[i] = digitList.get(digitCount - 1 - i);
        }

        return count(digits);
    }

    public long count(BigInteger n) {
        if(n.compareTo(BigInteger.valueOf(100)) < 0) {
           return n.longValue();
        }

        String s = n.toString();
        int[] digits = new int[s.length()];
        for(int i = 0; i < digits.length; i++) {
            digits[i] = s.charAt(i) - '0';
        }

        return count(digits);
    }

    private long count(int[] digits) {
        long result = 0;

        int digitCount = digits.length;
        int prevDigit = -1, lastSameDigitIdx = -1;
        boolean incr = false, decr = false, currIncr = false, currDecr, bouncy = false, sameDigit = false;

        for(int i = 0; i < digits.length; i++) {
            int digit = digits[i];

            if(i - 1 > lastSameDigitIdx) {
                sameDigit = false;
            }

            if(prevDigit != -1) {
                currIncr = digit > prevDigit;
                currDecr = digit < prevDigit;
                if(currIncr) {
                    if(decr) {
                        bouncy = true;
                    }
                    incr = true;
                }
                else if(currDecr) {
                    if(incr) {
                        bouncy = true;
                    }
                    decr = true;
                }
                else {
                    if(!incr && !decr) {
                        if(lastSameDigitIdx == -1) {
                            sameDigit = true;
                        }
                        lastSameDigitIdx = i;
                    }
                }
            }
            else {
                //first digit
                result += nonBouncyTab[digitCount - 1][10];
                for(int startDigit = 1; startDigit < digit; startDigit++) {
                    result += nonBouncyTab[digitCount][startDigit];
                }
            }

            if(!bouncy) {
                int lastDigit = i == digits.length - 1 ? digit : digit - 1;
                if(i == 1 || sameDigit) {
                    if (incr) {
                        for (int decrDigit = 0; decrDigit <= Math.min(prevDigit, digit); decrDigit++) {
                            result += decreaseTab[digitCount - i][decrDigit];
                        }
                        long incrSum = 0;
                        for (int incrDigit = prevDigit; incrDigit <= lastDigit; incrDigit++) {
                            incrSum += increaseTab[digitCount - i][incrDigit];
                        }
                        if(incrSum > 0) {
                            result += (incrSum - 1);
                        }
                    } else {
                        for (int decrDigit = 0; decrDigit <= lastDigit; decrDigit++) {
                            result += decreaseTab[digitCount - i][decrDigit];
                        }
                    }
                }
                else if(i != 0){
                    if(incr) {
                        for (int incrDigit = prevDigit; incrDigit <= lastDigit; incrDigit++) {
                            result += increaseTab[digitCount - i][incrDigit];
                        }
                    }
                    else {
                        for (int decrDigit = 0; decrDigit <= lastDigit; decrDigit++) {
                            result += decreaseTab[digitCount - i][decrDigit];
                        }
                    }
                }
            }
            else {
                if(currIncr) {
                    result += decreaseTab[digitCount - i + 1][prevDigit];
                }
                break;
            }
            prevDigit = digit;
        }

        return result;
    }

    private void fillValues() {
        long cumulativeSum = 9;
        for(int digitsCount = 2; digitsCount <= digitLimit; digitsCount++) {
            decreaseTab[digitsCount][0] = 1;
            for(int digit = 1; digit <= 9; digit++) {
                long incrSum = 0;
                for(int i = digit; i <= 9; i++) {
                    incrSum = incrSum + increaseTab[digitsCount - 1][i];
                }
                increaseTab[digitsCount][digit] = incrSum;

                long decrSum = 1; //Include values like 300000000
                for(int j = 1; j <= digit; j++) {
                    decrSum = decrSum + decreaseTab[digitsCount - 1][j];
                }
                decreaseTab[digitsCount][digit] = decrSum;

                long digitSum = incrSum + decrSum;
                nonBouncyTab[digitsCount][digit] = digitSum - 1;

                cumulativeSum = cumulativeSum + digitSum;
            }
            cumulativeSum = cumulativeSum - 9;
            nonBouncyTab[digitsCount][10] = cumulativeSum;
        }
    }

}
