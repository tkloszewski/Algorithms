package com.smart.tkl.euler.p36;

public class DoubleBasePalindrome {

    private final int limit;
    private final int base;

    public DoubleBasePalindrome(int limit, int base) {
        this.limit = limit;
        this.base = base;
    }

    public static void main(String[] args) {
        DoubleBasePalindrome doubleBasePalindrome = new DoubleBasePalindrome(1000000, 2);
        long sum = doubleBasePalindrome.calcSum();
        System.out.println("Sum: " + sum);
    }

    public long calcSum() {
        long sum = 0;

        int i = 1;
        int palindrome = generatePalindrome(i, 10, true);
        while (palindrome < limit) {
            if(isPalindrome(palindrome, base)) {
               sum += palindrome;
            }
            i++;
            palindrome = generatePalindrome(i, 10, true);
        }

        i = 1;
        palindrome = generatePalindrome(i, 10, false);
        while (palindrome < limit) {
            if(isPalindrome(palindrome, base)) {
                sum += palindrome;
            }
            i++;
            palindrome = generatePalindrome(i, 10, false);
        }

        return sum;
    }

    private static int generatePalindrome(int n, int base, boolean oddLength) {
        int palindrome = n;
        if(oddLength) {
           n = n / base;
        }
        while (n > 0) {
            int digit = n % base;
            palindrome = palindrome * base + digit;
            n = n / base;
        }
        return palindrome;
    }

    private static boolean isPalindrome(int n, int base) {
        int num = n;
        int reversed = 0;
        while (n > 0) {
            int digit = n % base;
            reversed = reversed * base + digit;
            n = n / base;
        }
        return num == reversed;
    }

}
