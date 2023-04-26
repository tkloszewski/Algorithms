package com.smart.tkl.euler.p164;

import com.smart.tkl.lib.utils.MathUtils;
import java.math.BigInteger;
import java.util.List;

public class ConsecutiveThreeDigits2 {

    private final int length;
    private final int consecutive;
    private final int maxSum;

    public ConsecutiveThreeDigits2(int length, int consecutive, int maxSum) {
        if(length < consecutive) {
            throw new IllegalArgumentException("Length cannot be less than " + consecutive);
        }
        if(maxSum < 0) {
            throw new IllegalArgumentException("Max sum cannot be less than zero");
        }
        this.length = length;
        this.consecutive = consecutive;
        this.maxSum = maxSum;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ConsecutiveThreeDigits2 consecutiveThreeDigits = new ConsecutiveThreeDigits2(20, 3, 9);
        BigInteger count = consecutiveThreeDigits.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count 2: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public BigInteger count() {
        BigInteger count = BigInteger.ZERO;

        BigInteger[][] ways = getWays(this.length, this.consecutive, this.maxSum);

        for(BigInteger value : ways[this.length]) {
            count = count.add(value);
        }

        return count;
    }

    private static BigInteger[][] getWays(int length, int consecutive, int maxSum) {
        long startNum = (long)Math.pow(10, consecutive - 1);
        long endNum = startNum * 10 - 1;

        BigInteger[][] ways = new BigInteger[length + 1][(int)startNum];

        for(int i = 0; i <= length; i++) {
            for(int j = 0; j < startNum; j++) {
                ways[i][j] = BigInteger.ZERO;
            }
        }

        for(long n = startNum; n <= endNum; n++) {
            List<Integer> digits = MathUtils.getDigits(n);
            int sum = digits.stream().reduce(0, Integer::sum);
            if(sum <= maxSum) {
                int suffix = (int)(n % startNum);
                ways[consecutive][suffix] = ways[consecutive][suffix].add(BigInteger.ONE);
            }
        }

        long mod = startNum / 10;

        for(int digits = consecutive + 1; digits <= length; digits++) {
            for(int digit = 0; digit <= 9; digit++) {
                for(int suffixValue = 0; suffixValue <= startNum - 1; suffixValue++) {
                    int suffixDigitsSum = getDigitsSum(suffixValue);
                    if(digit + suffixDigitsSum <= maxSum) {
                        int newSuffixValue = (int)(10 * (suffixValue % mod) + digit);
                        ways[digits][newSuffixValue] = ways[digits][newSuffixValue].add(ways[digits - 1][suffixValue]);
                    }
                }
            }
        }

        return ways;
    }

    private static int getDigitsSum(long n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n = n / 10;
        }
        return sum;
    }

}
