package com.smart.tkl.euler.p164;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.Arrays;
import java.util.List;

public class ConsecutiveThreeDigits {

    private final int length;
    private final int maxSum;

    public ConsecutiveThreeDigits(int length, int maxSum) {
        if(length < 3) {
            throw new IllegalArgumentException("Length cannot be less than 3");
        }
        if(maxSum < 0) {
            throw new IllegalArgumentException("Max sum cannot be less than zero");
        }
        this.length = length;
        this.maxSum = maxSum;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        ConsecutiveThreeDigits consecutiveThreeDigits = new ConsecutiveThreeDigits(20, 9);
        long count = consecutiveThreeDigits.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count() {
        if(this.maxSum == 0) {
            return 1;
        }

        long count = 0;

        long[][] suffixSums = new long[this.maxSum + 1][];
        for(int sum = 0; sum <= this.maxSum; sum++) {
            suffixSums[sum] = new long[sum + 1];
        }

        for(long n = 100; n <= 999; n++) {
            List<Integer> digits = MathUtils.getDigits(n);
            int sum = digits.stream().reduce(0, Integer::sum);
            if(sum <= this.maxSum) {
                count++;
                int singleSuffixSum = digits.get(0);
                int doubleSuffixSum = digits.get(0) + digits.get(1);

                suffixSums[doubleSuffixSum][singleSuffixSum]++;
            }
        }

        if(this.length == 3) {
            return count;
        }

        count = 0;

        int remainingDigits = this.length - 3;
        for(int k = 0; k < remainingDigits - 1; k++) {

            long[][] newSuffixSums = new long[this.maxSum + 1][];
            for(int sum = 0; sum <= this.maxSum; sum++) {
                newSuffixSums[sum] = new long[sum + 1];
            }

            for(int digit = 0; digit <= 9; digit++) {
                for(int remainingSum = 0; remainingSum <= this.maxSum - digit; remainingSum++) {
                    long[] toJoinSums = suffixSums[remainingSum];
                    for(int lastDigitSuffix = 0; lastDigitSuffix <= remainingSum; lastDigitSuffix++) {
                        newSuffixSums[lastDigitSuffix + digit][digit] += toJoinSums[lastDigitSuffix];
                    }
                }
            }

            suffixSums = newSuffixSums;
        }

        long[] lastDoubleSuffixSums = new long[this.maxSum + 1];
        for(int sum = 0; sum <= this.maxSum; sum++) {
            lastDoubleSuffixSums[sum] = Arrays.stream(suffixSums[sum]).sum();
        }

        for(int digit = 0; digit <= 9; digit++) {
            for(int remainingSum = 0; remainingSum <= this.maxSum - digit; remainingSum++) {
                count += lastDoubleSuffixSums[remainingSum];
            }
        }

        return count;
    }

}
