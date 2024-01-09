package com.smart.tkl.euler.p178;

import java.math.BigInteger;
import java.util.Arrays;

public class PandigitalStepNumbers {

    private final int maxLength;
    private final boolean isPowerOfTen;
    private final String number;

    public PandigitalStepNumbers(int maxLength) {
        this.maxLength = maxLength;
        this.isPowerOfTen = true;
        this.number = null;
    }

    public PandigitalStepNumbers(String hugeNumber) {
        this.maxLength = hugeNumber.length();
        this.isPowerOfTen = false;
        this.number = hugeNumber;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        PandigitalStepNumbers pandigitalStepNumbers =
                new PandigitalStepNumbers("98765432132");
        BigInteger count = pandigitalStepNumbers.count();
        System.out.println(count);
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public BigInteger count() {
        BigInteger[][] digitPandigital = new BigInteger[10][maxLength + 1];
        for(int start = 1; start <= 9; start++) {
            digitPandigital[start] = countPandigital(start);
        }
        BigInteger[] allLengthsPandigital = countPandigitalForAllLengths(digitPandigital);
        BigInteger[] pandigital = countCumulatedPandigital(allLengthsPandigital);

        System.out.println(Arrays.toString(pandigital));

        if(isPowerOfTen) {
            return pandigital[this.maxLength];
        }

        if(number.length() < 10) {
            return BigInteger.ZERO;
        }

        BigInteger result = BigInteger.ZERO;

        int length = this.number.length();

        result = result.add(pandigital[length - 1]);

        System.out.println("Initial: " + result);

        int firstDigit = number.charAt(0) - '0';
        for(int digit = 1; digit < firstDigit; digit++) {
            result = result.add(digitPandigital[digit][length]);
        }

        System.out.println("After initial: " + result);

        for(int i = 0; i < number.length() - 10; i++) {
            int digit = number.charAt(i) - '0';
            int nextDigit = number.charAt(i + 1) - '0';
            if(digit == 0) {
                if(nextDigit > 1) {
                    result = result.add(digitPandigital[1][length - 1]);
                    break;
                }
                else if(nextDigit < 1) {
                    break;
                }
            }
            else if(digit == 1) {
                if(nextDigit > 2) {
                    result = result.add(digitPandigital[1][length]);
                    break;
                }
                else if(nextDigit == 2) {
                    result = result.add(digitPandigital[1][length - 2]);
                }
                else if(nextDigit == 1) {
                    result = result.add(digitPandigital[1][length - 2]);
                    break;
                }
            }
            else if(digit == 9) {
                if(nextDigit == 9) {
                    result = result.add(digitPandigital[8][length - 1]);
                    break;
                }
                else if(nextDigit < 8) {
                    break;
                }
            }
            else {
                if(nextDigit > digit + 1) {
                    result = result.add(digitPandigital[digit][length]);
                    break;
                }
                else if(nextDigit < digit - 1) {
                    break;
                }
                else if(nextDigit == digit) {
                    result = result.add(digitPandigital[digit - 1][length - 1]);
                    break;
                }
                else if(nextDigit == digit + 1) {
                    result = result.add(digitPandigital[digit - 1][length - 1]);
                }
            }
            length--;
        }


        return result;
    }

    public BigInteger[] countCumulatedPandigital(BigInteger[] pandigital) {
        BigInteger[] result = new BigInteger[maxLength + 1];
        for(int i = 0; i < 10; i++) {
            result[i] = BigInteger.ZERO;
        }
        BigInteger count = BigInteger.ZERO;
        for(int length = 10; length <= maxLength; length++) {
            count = count.add(pandigital[length]);
            result[length] = count;
        }
        return result;
    }

    public BigInteger[] countPandigitalForAllLengths(BigInteger[][] digitPandigital) {
        BigInteger[] result = new BigInteger[maxLength + 1];
        for(int start = 1; start <= 9; start++) {
            BigInteger[] pandigital = digitPandigital[start];
            for(int length = 10; length <= maxLength; length++) {
                if(pandigital[length].compareTo(BigInteger.ZERO) > 0) {
                    result[length] = add(result[length], pandigital[length]);
                }
            }
        }
        return result;
    }

    private BigInteger[] countPandigital(int startDigit) {
        BigInteger[] pandigital = new BigInteger[maxLength + 1];
        pandigital[0] = BigInteger.ZERO;
        BigInteger[][][] stepNumbers = new BigInteger[maxLength + 1][11][4];

        int startFlag = startDigit == 9 ? 2 : 0;
        stepNumbers[1][startDigit][startFlag] = BigInteger.ONE;

        for (int length = 1; length < maxLength; length++) {
            int newLength = length + 1;
            for (int digit = 0; digit <= 9; digit++) {
                for (int flag = startFlag; flag <= 3; flag++) {
                    int newFlag = flag;
                    BigInteger count = stepNumbers[length][digit][flag];
                    if (count != null && count.compareTo(BigInteger.ZERO) > 0) {
                        if (digit == 0) {
                            stepNumbers[newLength][1][flag] = add(stepNumbers[newLength][1][flag], count);
                            stepNumbers[newLength][10][newFlag] = add(stepNumbers[newLength][10][newFlag], count);
                        } else if (digit == 9) {
                            stepNumbers[newLength][8][flag] = add(stepNumbers[newLength][8][flag], count);
                            stepNumbers[newLength][10][newFlag] = add(stepNumbers[newLength][10][newFlag], count);
                        } else {
                            if (digit + 1 == 9) {
                                newFlag = newFlag | 2;
                            }
                            stepNumbers[newLength][digit + 1][newFlag] = add(stepNumbers[newLength][digit + 1][newFlag], count);
                            stepNumbers[newLength][10][newFlag] = add(stepNumbers[newLength][10][newFlag], count);

                            newFlag = flag;
                            if (digit - 1 == 0) {
                                newFlag = newFlag | 1;
                            }
                            stepNumbers[newLength][digit - 1][newFlag] = add(stepNumbers[newLength][digit - 1][newFlag], count);
                            stepNumbers[newLength][10][newFlag] = add(stepNumbers[newLength][10][newFlag], count);
                        }
                    }
                }
            }
        }

        for (int length = 1; length <= maxLength; length++) {
            pandigital[length] = stepNumbers[length][10][3];
            if(pandigital[length] == null) {
                pandigital[length] = BigInteger.ZERO;
            }
        }

        System.out.println("Pandigital for start digit: " + startDigit + "length: " + " =>  " + Arrays.toString(pandigital));

        return pandigital;
    }

    private static BigInteger add(BigInteger a, BigInteger b) {
        return a != null ? a.add(b) : b;
    }
}
