package com.smart.tkl.euler.p156;

public class DigitCounter {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long sum = sum();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long sum() {
        long result = 0;
        final long limit = (long)Math.pow(10, 12);

        for(int digit = 1; digit <= 9; digit++) {
            long sumForDigit = searchNumbers(1, limit, digit);
            result += sumForDigit;
        }

        return result;
    }

    private static long searchNumbers(long left, long right, int digit) {
        long result = 0;
        if(right - left < 10) {
            for(long n = left; n <= right; n++) {
                if(f(n, digit) == n) {
                    result += n;
                }
            }
        }
        else {
            long middle = (left + right) / 2;
            if(isInRange(left, middle, digit)) {
                result += searchNumbers(left, middle, digit);
            }
            if(isInRange(middle + 1, right, digit)) {
                result += searchNumbers(middle + 1, right, digit);
            }
        }
        return result;
    }

    private static boolean isInRange(long left, long right, int digit) {
        return f(right, digit) >= left && f(left, digit) <= right;
    }

    private static long f(long n, int d) {
        long result = 0;

        int k = 0;
        long pow = 1, num = 0;

        while (n > 0) {
            int digit = (int)(n % 10);
            result += digit * k * (pow / 10);
            if(digit > d) {
                result += pow;
            }
            else if(digit == d) {
                result +=  (1 + num);
            }
            num += digit * pow;
            n = n / 10;
            pow *= 10;
            k++;
        }

        return result;
    }

}
