package com.smart.tkl.euler.p719;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NumberSplitting {

    /*
    Has sum true: S number = 55225 sqrt = 235
    Has sum true: S number = 455625 sqrt = 675
    * */


    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long sum = testSquaresFast();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum= " + sum);
        System.out.println("Solution took: " + (time2 - time1));
    }

    private static long testSquares() {
        long sum = 0;
        for(long n = 9; n <= 1000000; n++) {
            long square = n * n;
            List<Integer> squareDigits = getDigits(square);
            if(hasSum(squareDigits,  0, n)) {
                sum += square;
            }
        }
        return sum;
    }

    private static long testSquaresFast() {
        long sum = 0;
        for(long n = 9; n <= 1000000; n++) {
            long square = n * n;
            if(isSNumber(n, square)) {
                sum += square;
            }
        }
        return sum;
    }

    private static boolean isSNumber(long n, long square) {
        if(square == n) {
           return true;
        }
        if(square < n) {
           return false;
        }
        long k = 10;
        while (k < square) {
            long r = square % k;
            long q = square / k;
            if(r < n) {
               boolean isS = isSNumber(n - r, q);
               if(isS) {
                  return true;
               }
            }
            k *= 10;
        }
        return false;
    }

    private static boolean isSNumber(List<Integer> squareDigits, long sqrt) {
        int winSizeMax = (int)Math.log10(sqrt) + 1;
        int winSizeMin = Math.max(1, winSizeMax - 1);

        for(int winSize = winSizeMin; winSize <= winSizeMax; winSize++) {

            long winValue = 0;
            for(int i = 0; i < winSize; i++) {
                int digit = squareDigits.get(i);
                winValue = winValue * 10 + digit;
            }

            long mod = (long)Math.pow(10, winSize - 1);

            for(int i = 0; i <= squareDigits.size() - winSize; i++) {
                Set<Long> sums1 = i > 0 ? getSums(squareDigits.subList(0, i)) : Set.of(0L);
                winValue = i > 0 ? (winValue % mod) * 10 + squareDigits.get(i + winSize - 1) : winValue;
                Set<Long> sums2 = i + winSize < squareDigits.size() ? getSums(squareDigits.subList(i + winSize, squareDigits.size())) : Set.of(0L);

                for(long sum1 : sums1) {
                    for(long sum2 : sums2) {
                        if(sum1 + winValue + sum2 == sqrt) {
                           return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static Set<Long> getSums(List<Integer> digits, int maxWinSize) {
        Set<Long> sums = new LinkedHashSet<>();
        if(maxWinSize == 1) {
            sums.add((long)digits.get(0));
        }
        else {
            long sum = 0, prevSum = 0;
            for (int winSize = 1; winSize <= maxWinSize; winSize++) {
                int idx = winSize - 1;
                sum = prevSum * 10 + digits.get(idx);
                prevSum = sum;
                if(idx < maxWinSize - 1) {
                    Set<Long> subSums = getSums(digits.subList(idx + 1, maxWinSize));
                    for(Long subSum: subSums) {
                        sums.add(sum + subSum);
                    }
                }
                else {
                    sums.add(sum);
                }
            }
        }
        return sums;
    }

    private static boolean hasSum(List<Integer> digits, long currentSum, long sqrt) {
        int maxWinSize = digits.size();
        if(maxWinSize == 1) {
            return currentSum + digits.get(0) == sqrt;
        }
        else {
            long sum = 0, prevSum = 0;
            for (int winSize = 1; winSize <= maxWinSize; winSize++) {
                int idx = winSize - 1;
                sum = prevSum * 10 + digits.get(idx);
                prevSum = sum;
                if(idx < maxWinSize - 1) {
                    boolean hasSum = hasSum(digits.subList(idx + 1, maxWinSize), currentSum + sum, sqrt);
                    if(hasSum) {
                       return true;
                    }
                }
                else {
                    return currentSum + sum == sqrt;
                }
            }
        }
        return false;
    }

    private static Set<Long> getSums(List<Integer> digits) {
        Set<Long> sums = new LinkedHashSet<>();
        int maxWinSize = digits.size();
        if(maxWinSize == 1) {
            sums.add((long)digits.get(0));
        }
        else {
            long sum = 0, prevSum = 0;
            for (int winSize = 1; winSize <= maxWinSize; winSize++) {
                int idx = winSize - 1;
                sum = prevSum * 10 + digits.get(idx);
                prevSum = sum;
                if(idx < maxWinSize - 1) {
                   Set<Long> subSums = getSums(digits.subList(idx + 1, maxWinSize));
                   for(Long subSum: subSums) {
                       sums.add(sum + subSum);
                   }
                }
                else {
                   sums.add(sum);
                }
            }
        }
        return sums;
    }




    private static List<Integer> getDigits(long n) {
        LinkedList<Integer> digits = new LinkedList<>();
        while (n != 0) {
            int digit = (int)(n % 10);
            digits.addFirst(digit);
            n = n / 10;
        }
        return digits;
    }
}
