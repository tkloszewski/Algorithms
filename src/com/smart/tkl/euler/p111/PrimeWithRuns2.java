package com.smart.tkl.euler.p111;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.utils.MathUtils;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PrimeWithRuns2 {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        List<Long> times = new ArrayList<>();
        for(int digitLength = 4; digitLength <= 18; digitLength++) {
            for (int repeatedDigit = 0; repeatedDigit <= 9; repeatedDigit++) {
                long t1 = System.currentTimeMillis();
                List<Long> primes = findSmallPrimes(digitLength, repeatedDigit);
                List<Long> primesBig = findLargePrimes(digitLength, repeatedDigit).stream().map(BigInteger::longValue).collect(Collectors.toList());
                long t2 = System.currentTimeMillis();
                times.add(t2 - t1);
                if(!primes.equals(primesBig)) {
                    System.out.println(primes);
                    System.out.println(primesBig);
                    System.out.println("-----------------------");
                }
            }
        }

        for(int digitLength = 19; digitLength <= 40; digitLength++) {
            for (int repeatedDigit = 0; repeatedDigit <= 9; repeatedDigit++) {
                List<BigInteger> primes = findLargePrimes(digitLength, repeatedDigit);
                System.out.println(primes);
            }
        }


        System.out.println("Times: " + times);
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));


        List<Long> primes = findSmallPrimes(4, 3);
        System.out.println(primes);

        List<String> foundPrimes = findLargePrimesAsString(4, 3);
        System.out.println(foundPrimes);
    }

    public static List<String> findLargePrimesAsString(int digitsLength, int repeatedDigit) {
        List<BigInteger> primes = findLargePrimes(digitsLength, repeatedDigit);
        return primes.stream().map(BigInteger::toString).collect(Collectors.toList());
    }

    private static List<BigInteger> findLargePrimes(int digitsLength, int repeatedDigit) {
        if(repeatedDigit == 1 && (digitsLength == 19 || digitsLength == 23)) {
           String stringNumber = "1".repeat(digitsLength);
           BigInteger repUnit = new BigInteger(stringNumber);
           if(repUnit.isProbablePrime(16)) {
               return List.of(repUnit);
           }
           else {
               return List.of();
           }
        }
        else {
            int startPositionLength = repeatedDigit == 0 ? 2 : 1;
            for(int positionLength = startPositionLength; ;positionLength++) {
                List<BigInteger> primes = findLargePrimes(digitsLength, repeatedDigit, positionLength);
                if(!primes.isEmpty()) {
                   Collections.sort(primes);
                   return primes;
                }
            }
        }
    }

    private static List<BigInteger> findLargePrimes(int digitsLength, int repeatedDigit, int positionLength) {
        List<BigInteger> primes = new ArrayList<>();
        fillPrimes(digitsLength, repeatedDigit, 0, new int[positionLength], new String[positionLength], primes);
        return primes;
    }

    private static void fillPrimes(int digitsLength, int repeatedDigit, int posCount, int[] positions, String[] repeatingParts, List<BigInteger> foundPrimes) {
        int prevPos = posCount > 0 ? positions[posCount - 1] : -1;
        if(posCount == positions.length - 1) {
           int startPos = prevPos + 1;
           if(repeatedDigit % 2 == 0 || repeatedDigit == 5) {
              startPos = digitsLength - 1;
           }
           for(int pos = startPos; pos <= digitsLength - 1; pos++) {
               String repeatingPart = pos == prevPos + 1 ? "" : String.valueOf(repeatedDigit).repeat(pos - prevPos - 1);
               repeatingParts[posCount] = repeatingPart;
               positions[posCount] = pos;
               fillPrimes(digitsLength, repeatedDigit, positions, repeatingParts, new int[positions.length], 0, foundPrimes);
           }
        }
        else {
           int startPos = posCount == 0 ? 0 : prevPos + 1;
           int positionsLeft = positions.length - posCount - 1;
           int endPos = posCount == 0 && repeatedDigit == 0 ? 0 : digitsLength - positionsLeft - 1;
           for(int pos = startPos; pos <= endPos; pos++) {
               String repeatingPart = pos == prevPos + 1 ? "" : String.valueOf(repeatedDigit).repeat(pos - prevPos - 1);
               repeatingParts[posCount] = repeatingPart;
               positions[posCount] = pos;
               fillPrimes(digitsLength, repeatedDigit, posCount + 1, positions, repeatingParts, foundPrimes);
           }
        }
    }

    private static void fillPrimes(int digitsLength, int repeatingDigit, int[] positions, String[] repeatingParts, int[] digits, int digitPos,
                                   List<BigInteger> primes) {
        if(digitPos == digits.length - 1) {
            StringBuilder numberPart1 = new StringBuilder();
            for(int i = 0; i < digits.length - 1; i++) {
                numberPart1.append(repeatingParts[i]);
                numberPart1.append(digits[i]);
            }

            int lastPos = positions[positions.length - 1];
            int startEndDigit = lastPos == digitsLength - 1 ? 1 : lastPos == 0 ? 1 : 0;
            int step = lastPos == digitsLength - 1 ? 2 : 1;

            for(int endDigit = startEndDigit; endDigit <= 9; endDigit += step){
                if(endDigit == repeatingDigit) {
                   continue;
                }
                if(endDigit == 5 && lastPos == digitsLength - 1) {
                   continue;
                }

                StringBuilder numberPart2 = new StringBuilder();

                digits[digitPos] = endDigit;
                numberPart2.append(repeatingParts[digitPos]);
                numberPart2.append(endDigit);

                if(lastPos < digitsLength - 1) {
                    String lastRepeatingPart = String.valueOf(repeatingDigit).repeat(digitsLength - 1 - lastPos);
                    numberPart2.append(lastRepeatingPart);
                }

                String numberString = numberPart1 + numberPart2.toString();
                BigInteger number = new BigInteger(numberString);
                if(number.isProbablePrime(16)) {
                    primes.add(number);
                }
            }
        }
        else {
            int pos = positions[digitPos];
            int startDigit = pos == 0 ? 1 : 0;
            for(int digit = startDigit; digit <= 9; digit++) {
                if(digit == repeatingDigit) {
                   continue;
                }
                digits[digitPos] = digit;
                fillPrimes(digitsLength, repeatingDigit, positions, repeatingParts, digits, digitPos + 1, primes);
            }
        }
    }

    private static List<Long> findSmallPrimes(int digitsLength, int repeatedDigit) {
        List<Long> primes = getSmallPrimesForSingleSpace(digitsLength, repeatedDigit);
        if(primes.isEmpty()) {
            primes = getSmallPrimesForDoubleSpace(digitsLength, repeatedDigit);
        }
        return primes;
    }

    private static List<Long> getSmallPrimesForSingleSpace(int digitsLength, int repeatedDigit) {
        List<Long> result = new ArrayList<>();
        if(repeatedDigit == 0) {
            return result;
        }
        if(repeatedDigit % 2 == 0 || repeatedDigit == 5) {
            long sameDigitPart = sameDigitsToValue(repeatedDigit, digitsLength - 1) * 10;
            for(int digit = 1; digit <= 9; digit += 2) {
                if(digit == 5) {
                    continue;
                }
                long number = sameDigitPart + digit;
                if(Primes.isPrime(number)) {
                    result.add(number);
                }
            }
        }
        else {
            for(int pos = 0; pos < digitsLength; pos++) {
                long multiplier = (long) Math.pow(10, digitsLength - pos);
                long sameDigitPart1 = 0;
                if(pos > 0) {
                    sameDigitPart1 = sameDigitsToValue(repeatedDigit, pos) * multiplier;
                }
                int startEndDigit = pos == digitsLength - 1 ? 1 : 0;
                int step = pos == digitsLength - 1 ? 2 : 1;
                for(int endDigit = startEndDigit; endDigit <= 9; endDigit += step) {
                    long sameDigitPart2 = sameDigitsToValue(repeatedDigit, digitsLength - pos - 1);
                    long number = sameDigitPart1 + endDigit * (multiplier / 10) + sameDigitPart2;
                    if(Primes.isPrime(number)) {
                        result.add(number);
                    }
                }
            }
        }
        Collections.sort(result);
        return result;
    }

    private static List<Long> getSmallPrimesForDoubleSpace(int digitsLength, int repeatedDigit) {
        List<Long> result = new ArrayList<>();
        if(repeatedDigit % 2 == 0 || repeatedDigit == 5) {
            if(repeatedDigit == 0) {
                for(int startDigit = 1; startDigit <= 9; startDigit++) {
                    long base = ((long) Math.pow(10, digitsLength - 1)) * startDigit;
                    for(int endDigit = 1; endDigit <= 9; endDigit += 2) {
                        long number = base + endDigit;
                        if(Primes.isPrime(number)) {
                            result.add(number);
                        }
                    }
                }
            }
            else {
                for(int pos1 = 0; pos1 <= digitsLength - 2; pos1++) {
                    for(int startDigit = pos1 == 0 ? 1 : 0; startDigit <= 9; startDigit++) {
                        if(startDigit == repeatedDigit) {
                            continue;
                        }
                        long value;
                        if(pos1 > 0) {
                            long multiplier = (long) Math.pow(10, digitsLength - pos1);
                            long sameDigitPart1 = sameDigitsToValue(repeatedDigit, pos1) * multiplier;
                            long sameDigitPart2 = sameDigitsToValue(repeatedDigit, digitsLength - pos1 - 2) * 10;
                            value = sameDigitPart1 + startDigit * (multiplier / 10) + sameDigitPart2;
                        }
                        else {
                            long multiplier = (long) Math.pow(10, digitsLength - 1);
                            value = startDigit * multiplier + sameDigitsToValue(repeatedDigit, digitsLength - 2) * 10;
                        }
                        for(int endDigit = 1; endDigit <= 9; endDigit += 2) {
                            if(endDigit == repeatedDigit) {
                                continue;
                            }
                            long number = value + endDigit;
                            if(Primes.isPrime(number)) {
                                result.add(number);
                            }
                        }
                    }
                }
            }
        }
        else {
            for(int pos1 = 0; pos1 <= digitsLength - 2; pos1++) {
                long multiplier1 = (long)Math.pow(10, digitsLength - pos1);
                for(int pos2 = pos1 + 1; pos2 <= digitsLength - 1; pos2++) {
                    long multiplier2 = (long)Math.pow(10, digitsLength - pos2);
                    for(int startDigit = pos1 == 0 ? 1 : 0; startDigit <= 9; startDigit++) {
                        if (startDigit == repeatedDigit) {
                            continue;
                        }

                        long sameDigitPart1 = 0;
                        if(pos1 > 0) {
                            sameDigitPart1 = sameDigitsToValue(repeatedDigit, pos1) * multiplier1;
                        }
                        long sameDigitPart2 = 0;
                        if(pos2 - pos1 > 1) {
                            sameDigitPart2 = sameDigitsToValue(repeatedDigit, pos2 - pos1 - 1) * multiplier2;
                        }

                        long value = sameDigitPart1 + startDigit * (multiplier1 / 10) + sameDigitPart2;

                        int startEndDigit = pos2 == digitsLength - 1 ? 1 : 0;
                        int step = pos2 == digitsLength - 1 ? 2 : 1;
                        for(int endDigit = startEndDigit; endDigit <= 9; endDigit += step) {
                            if(endDigit == repeatedDigit) {
                                continue;
                            }
                            long sameDigitPart3 = 0;
                            if(pos2 < digitsLength - 1) {
                                sameDigitPart3 = sameDigitsToValue(repeatedDigit, digitsLength - pos2 - 1);
                            }
                            long number = value + endDigit * (multiplier2 / 10) + sameDigitPart3;
                            if(MathUtils.isPrime(number)) {
                                result.add(number);
                            }
                        }
                    }
                }
            }
        }
        Collections.sort(result);
        return result;
    }

    private static long sameDigitsToValue(int digit, int length) {
        long base = (long) Math.pow(10, length);
        return ((base - 1) / 9) * digit;
    }
}
