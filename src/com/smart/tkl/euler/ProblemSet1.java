package com.smart.tkl.euler;

import com.smart.tkl.utils.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.smart.tkl.utils.MathUtils.isPalindrome;
import static com.smart.tkl.utils.MathUtils.isPrime;

public class ProblemSet1 {

    private static final String DIGIT_1000 =
            "73167176531330624919225119674426574742355349194934" +
            "96983520312774506326239578318016984801869478851843" +
            "85861560789112949495459501737958331952853208805511" +
            "12540698747158523863050715693290963295227443043557" +
            "66896648950445244523161731856403098711121722383113" +
            "62229893423380308135336276614282806444486645238749" +
            "30358907296290491560440772390713810515859307960866" +
            "70172427121883998797908792274921901699720888093776" +
            "65727333001053367881220235421809751254540594752243" +
            "52584907711670556013604839586446706324415722155397" +
            "53697817977846174064955149290862569321978468622482" +
            "83972241375657056057490261407972968652414535100474" +
            "82166370484403199890008895243450658541227588666881" +
            "16427171479924442928230863465674813919123162824586" +
            "17866458359124566529476545682848912883142607690042" +
            "24219022671055626321111109370544217506941658960408" +
            "07198403850962455444362981230987879927244284909188" +
            "84580156166097919133875499200524063689912560717606" +
            "05886116467109405077541002256983155200055935729725" +
            "71636269561882670428252483600823257530420752963450".replaceAll("[^0-9]","");


    public static void main(String[] args) {
         System.out.println("Multiple34: " + multiples34(1000));
         System.out.println("Even Fibonacci sum: " + fibSum(4000000));
         System.out.println("Largest prime factor: " + largestPrimeFactor2(600851475143L));
         System.out.println("Largest palindrome product: " + largestPalindromeProduct(4));
         System.out.println("Nth prime: " + getNthPrime(10001 ));
         System.out.println("Smallest multiple: " + smallestMultiple(20));
         System.out.println("Sum square difference: " + sumSquareDifference(100));
         System.out.println("Max product of 13 digit numbers in 1000 digit: " + largestProductIn1000DigitNumber(DIGIT_1000, 13));
         System.out.println("Pythagorean triplet product: " + getPythagoreanTripletProduct());
         System.out.println("Sum of primes less than 2 million: " + sumOfPrimesLessThan(2000000));
    }

    public static int multiples34(int n) {
        int sum = 0;
        for(int i = 3; i < n; i++) {
            if(i % 3 == 0 || i % 5 == 0) {
                sum += i;
            }
        }
        return sum;
    }

    public static int fibSum(int n) {
        int result = 2;
        int sum1 = 1, sum2 = 2;
        while(sum2 < n) {
            int sum3 = sum1 + sum2;
            sum1 = sum2;
            sum2 = sum3;
            if(sum2 < n && sum2 % 2 == 0) {
               result += sum3;
            }
        }
        return result;
    }

    public static long largestPrimeFactor(long n) {
        long maxFactor = 1;
        long end = n;
        for(long i = 2; i <= end; i++) {
            if(n % i == 0) {
                if(isPrime(i) && i > maxFactor) {
                    maxFactor = i;
                }
               end = n/i;
            }
        }
        return maxFactor;
    }

    public static long largestPrimeFactor2(long n) {
        long lastFactor;
        if(n % 2 == 0) {
            lastFactor = 2;
            n = n / 2;
            while (n % 2 == 0) {
                n = n / 2;
            }
        }
        else {
            lastFactor = 1;
        }

        long maxFactor = (long)Math.sqrt(n), factor = 3;
        while (n > 1 && factor <= maxFactor) {
            if(n % factor == 0) {
                n = n / factor;
                lastFactor = factor;
                while (n % factor == 0) {
                    n = n / factor;
                }
                maxFactor = (long)Math.sqrt(n);
            }
            factor += 2;
        }

        return n == 1 ? lastFactor : n;
    }

    public static int largestPalindromeProduct(int numOfDigits) {
        int n = (int)Math.pow(10, numOfDigits);
        int largestPalindrome = 1;
        for(int i = n - 1; i >= 1; i--) {
            if(i * i < largestPalindrome) {
               break;
            }
            for(int j = i; j >= 1; j--) {
                int product = i * j;
                if(product < largestPalindrome) {
                   break;
                }

                if(isPalindrome(product) && product > largestPalindrome) {
                    largestPalindrome = product;
                    break;
                }
            }
        }
        return largestPalindrome;
    }

    public static long getNthPrime(int n) {
        int primeCount = 1;
        int i = 2;
        while(primeCount != n) {
            if(isPrime(++i)) {
                primeCount++;
            }
        }
        return i;
    }

    public static int smallestMultiple(int n) {
        int result = 1;
        for(int i = 2; i <= n; i++) {
            result = MathUtils.LCM(i, result);
        }
        return result;
    }

    public static int sumSquareDifference(int n) {
        int sum = 0;
        for(int i = 0; i < n; i++) {
            for(int j = i+1; j <= n; j++) {
                sum += i * j;
            }
        }
        return 2 * sum;
    }

    public static long largestProductIn1000DigitNumber(String digit1000, int digitSize) {
        long result = 0;
        if(!digit1000.matches("\\d{1000}") || digitSize <= 0) {
            return result;
        }

        long maxProduct = maxProduct(digitSize);

        for(int i = 0; i <= digit1000.length() - digitSize; i++) {
            long product = digitProduct(Long.parseLong(digit1000.substring(i, i + digitSize)));
            if(product > result) {
                result = product;
            }
            if(result == maxProduct) {
               break;
            }
        }
        return result;
    }

    public static long digitProduct(long n) {
        long result = 1;
        while(n > 0) {
            result *= n % 10;
            n = n / 10;
        }
        return result;
    }

    public static long maxProduct(int length) {
        long maxValue = (int)(Math.pow(10, length) - 1);
        return maxValue * maxValue;
    }

    public static long sumOfPrimesLessThan(long n) {
        long sum = 0;
        for(long i = 2; i < n; i++) {
            if(isPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }

    public static long getPythagoreanTripletProduct() {
        int result = 1;
        for(int num : getPythagoreanTriplet()) {
            result *= num;
        }
        return result;
    }

    private static List<Integer> getPythagoreanTriplet() {
        List<Integer> result = new ArrayList<>(3);

        outer:
        for(int a = 1; a <= 1000; a++) {
            for(int b = a + 1; b <= 1000; b++) {
               if(b >= 1000 - a - b) {
                  break;
               }
               int x = 1000 * (a + b) - a * b;
               if(x == 500000) {
                   result = Arrays.asList(a, b, (int)Math.sqrt(a * a + b * b));
                   break outer;
               }
            }
        }
        return result;
    }
}
