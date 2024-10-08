package com.smart.tkl.euler.p145;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReversibleNumber2 {

    private final long[] DIGIT_COUNTS = initDigitsCounts();
    private final long[] ODD_SUMS = new long[]{0, 4, 4, 3, 3, 2, 2, 1, 1, 0};
    private final long[] ODD_SUMS_ZERO = new long[] {5, 5, 4, 4, 3, 3, 2, 2, 1, 1};
    private final long[] ODD_SUMS_CARRY_OVER = new long[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4};
    private final long[] EVEN_SUMS_ZERO = new long[] {5, 4, 4, 3, 3, 2, 2, 1, 1, 0};
    private final long[] ODD_SUMS_CUMULATIVE = new long[10];
    private final long[] ODD_SUMS_ZERO_CUMULATIVE = new long[10];
    private final long[] ODD_SUMS_CARRY_OVER_CUMULATIVE = new long[10];
    private final long[] EVEN_SUMS_ZERO_CUMULATIVE = new long[10];

    public ReversibleNumber2() {
        init();
    }

    public static void main(String[] args) {
        ReversibleNumber2 reversibleNumber = new ReversibleNumber2();
        System.out.println(reversibleNumber.count(33722496859L));
        System.out.println(reversibleNumber.count(754321372713713L));

        long time1 = System.currentTimeMillis();
        Random random = new Random();
        for(int i = 0; i < 100000; i++) {
            long n = Math.abs(random.nextLong());
            reversibleNumber.count(n);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private void init() {
        ODD_SUMS_CUMULATIVE[0] = ODD_SUMS[0];
        ODD_SUMS_ZERO_CUMULATIVE[0] = ODD_SUMS_ZERO[0];
        ODD_SUMS_CARRY_OVER_CUMULATIVE[0] = ODD_SUMS_CARRY_OVER[0];
        EVEN_SUMS_ZERO_CUMULATIVE[0] = EVEN_SUMS_ZERO[0];
        for(int i = 1; i <= 9; i++) {
            ODD_SUMS_CUMULATIVE[i] = ODD_SUMS_CUMULATIVE[i - 1] + ODD_SUMS[i];
            ODD_SUMS_ZERO_CUMULATIVE[i] = ODD_SUMS_ZERO_CUMULATIVE[i - 1] + ODD_SUMS_ZERO[i];
            ODD_SUMS_CARRY_OVER_CUMULATIVE[i] = ODD_SUMS_CARRY_OVER_CUMULATIVE[i - 1] + ODD_SUMS_CARRY_OVER[i];
            EVEN_SUMS_ZERO_CUMULATIVE[i] = EVEN_SUMS_ZERO_CUMULATIVE[i - 1] + EVEN_SUMS_ZERO[i];
        }
    }

    private static long[] initDigitsCounts() {
        long[] counts = new long[19];
        for(int k = 2; k <= 18; k++) {
            long decimalCount;
            if(k % 2 == 0) {
                int pow = k / 2;
                decimalCount = 20 * (long)Math.pow(30, pow - 1);
            }
            else {
                int r = k % 4;
                int pow = k / 4;
                decimalCount = r == 1 ? 0 : 100 * (long)Math.pow(500, pow);
            }
            counts[k] = counts[k - 1] + decimalCount;
        }
        return counts;
    }

    public long count(long number) {
        DigitsInfo digits = toDigitsInfo(number);
        int size = digits.size;
        if(size % 2 == 0) {
            return countForEvenDigitsSize(digits);
        }
        else {
            int r = size % 4;
            if(r == 1) {
               return DIGIT_COUNTS[size - 1];
            }
            else {
               return countForOdd3Mod4DigitsSize(digits);
            }
        }
    }

    private long countForEvenDigitsSize(DigitsInfo digits) {
        long count = 0;
        int size = digits.size;
        int pow = size / 2 - 1;
        long pow30 = (long)Math.pow(30, pow);

        count += DIGIT_COUNTS[size - 1];

        int firstDigit = digits.get(0);
        if(firstDigit > 1) {
            count += ODD_SUMS_CUMULATIVE[firstDigit - 1] * pow30;
        }

        pow30 = pow30 / 30;

        long[] prefixDigitProduct = new long[size / 2];
        prefixDigitProduct[0] = ODD_SUMS[firstDigit];

        int middle = size / 2;
        int reverseIndex = middle - 1;

        for(int i = 1; i < size; i++) {
            int digit = digits.get(i);
            int reversedDigit = digits.getReversed(i);

            if(i < size / 2) {
                if(digit != 0) {
                    count += prefixDigitProduct[i - 1] * ODD_SUMS_ZERO_CUMULATIVE[digit - 1] * pow30;
                }
                prefixDigitProduct[i] = prefixDigitProduct[i - 1] * ODD_SUMS_ZERO[digit];
                pow30 = pow30 / 30;
            }
            else {
                int lessCount = 0;
                int startDigit = i < size - 1 ? (reversedDigit % 2 == 0 ? 1 : 0) : (reversedDigit % 2 == 0 ? 1 : 2);
                for(int smaller = startDigit; smaller < digit && smaller + reversedDigit < 10; smaller += 2) {
                    lessCount++;
                }

                if(reverseIndex - 1 >= 0) {
                    count += lessCount * prefixDigitProduct[reverseIndex - 1];
                }
                else {
                    count += lessCount;
                }

                int sum = digit + reversedDigit;
                if(sum >= 10 || sum % 2 == 0) {
                    break;
                }

                reverseIndex--;
            }
        }

        return count;
    }

    private long countForOdd3Mod4DigitsSize(DigitsInfo digits) {
        long count = 0;
        int size = digits.size;
        int pow = (size - 3) / 4;
        long pow500 = (long)Math.pow(500, pow);

        count += DIGIT_COUNTS[size - 1];

        int firstDigit = digits.get(0);
        if(firstDigit == 1) {
            return count;
        }

        count += ODD_SUMS_CARRY_OVER_CUMULATIVE[firstDigit - 1] * pow500 * 5;

        long[] prefixDigitProduct = new long[size / 2];
        prefixDigitProduct[0] = ODD_SUMS_CARRY_OVER[firstDigit];

        int middle = size / 2;
        int reverseIndex = middle - 1;

        for(int i = 1; i < size; i++) {
            int digit = digits.get(i);
            int reversedDigit = digits.getReversed(i);
            boolean evenIndex = i % 2 == 0;

            if(i < size / 2) {
                if(!evenIndex) {
                    pow500 = pow500 / 500;
                }

                if(digit > 0) {
                    long cumulativeSum = evenIndex ? ODD_SUMS_CARRY_OVER_CUMULATIVE[digit - 1] : EVEN_SUMS_ZERO_CUMULATIVE[digit - 1];
                    long multiplier = cumulativeSum * 5 * pow500;
                    if(!evenIndex) {
                       multiplier = multiplier * 20;
                    }
                    count += prefixDigitProduct[i - 1] * multiplier;
                }
                long multiplier = evenIndex ? ODD_SUMS_CARRY_OVER[digit] : EVEN_SUMS_ZERO[digit];
                if(multiplier == 0) {
                    break;
                }
                prefixDigitProduct[i] = prefixDigitProduct[i - 1] * multiplier;
            }
            else if(i == size / 2) {
                if(digit > 4) {
                    count += 5 * prefixDigitProduct[i - 1];
                    break;
                }
                else {
                    count += digit * prefixDigitProduct[i - 1];
                }
            }
            else {
                int lessCount = 0;
                if(evenIndex) {
                    if(ODD_SUMS_CARRY_OVER[digit] == 0) {
                        break;
                    }

                    int startDigit = i < size - 1 ? (reversedDigit % 2 == 0 ? 1 : 0) : (reversedDigit % 2 == 0 ? 1 : 2);
                    for(int smaller = startDigit; smaller < digit; smaller += 2) {
                        if (smaller + reversedDigit >= 10) {
                            lessCount++;
                        }
                    }
                }
                else {
                    int startDigit = reversedDigit % 2 == 0 ? 0 : 1;
                    for(int smaller = startDigit; smaller < digit && smaller + reversedDigit < 10; smaller += 2) {
                        lessCount++;
                    }
                }

                if(reverseIndex - 1 >= 0) {
                    count += lessCount * prefixDigitProduct[reverseIndex - 1];
                }
                else {
                    count += lessCount;
                }

                int sum = digit + reversedDigit;
                if(evenIndex) {
                   //sum must carry over and be odd
                   if(sum < 10 || sum % 2 == 0) {
                      break;
                   }
                }
                else {
                   //sum must not carry over and must be even
                   if(sum >= 10 || sum % 2 == 1) {
                      break;
                   }
                }

                reverseIndex--;
            }
        }

        return count;
    }

    private static DigitsInfo toDigitsInfo(long number) {
        List<Integer> digits = new ArrayList<>();
        while (number > 0) {
            int digit = (int) (number % 10);
            digits.add(digit);
            number = number / 10;
        }
        return new DigitsInfo(digits, digits.size());
    }

    private static class DigitsInfo {
        List<Integer> digits;
        int size;

        public DigitsInfo(List<Integer> digits, int size) {
            this.digits = digits;
            this.size = size;
        }

        int get(int i) {
            return digits.get(size - 1 - i);
        }

        int getReversed(int i) {
            return digits.get(i);
        }
    }

}
