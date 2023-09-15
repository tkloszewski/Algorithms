package com.smart.tkl.lib.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MathUtils {
    
    public static void main(String[] args) {
        System.out.println("Congruences solution: " + solveCongruences(List.of(new Congruence(2, 3), new Congruence(3, 5), new Congruence(2, 7))));
        System.out.println("Maximum sub array index: " + Arrays.toString(getMaxSubArrayIndex(new int[]{2, 1, 3, 4, -1, 2, 1, 5, -4, -20, 21, 2})));
        System.out.println("Linear diophantine equation: " + solveLinearEquation(6, 4, 12));
        System.out.println("Modulo inverse of 7 mod 2^16: " + moduloInverse(7, 2L << 32));
        System.out.println("Inverse of range: " + Arrays.toString(modInvInRange(11)));
        System.out.println("Inverse mod list of (1,2,3,4,5,6) % 7: " + modInverseInList(List.of(1L, 2L, 3L, 4L, 5L, 6L), 7));
        System.out.println("Modulo power 2639^3648 mod 7297: " + moduloPower(2639, 3648, 7279));
        System.out.println("Modulo power mod 10000000000: " + moduloPower(2, 7830457, 10000000000L));
        System.out.println("Is permutation: " + formDigitPermutations(1234567890, 3021987465L));
        System.out.println("Prime factors: " + listPrimeFactors(763012L));
        System.out.println("Is prime: " + isPrime(99194853094755497L));
        System.out.println(GCD(323, 361));
        System.out.println(generateRotationValues(1234));
        System.out.println("Truncatable prime: " + isTruncatablePrime(3797, notPrime(5000)));
        System.out.println("Reverse 30513123213: " + reverse(BigDecimal.valueOf(30513123213L)));
        System.out.println("Sum of digits: " + sumOfDigits("1234567890"));
    }

    public static List<Integer> toBase(long n, int base) {
        List<Integer> result = new ArrayList<>();
        while (n > 0) {
            int digit = (int)(n % base);
            result.add(digit);
            n = n / base;
        }
        return result;
    }
    
    public static Fraction toFraction(double f) {
        long m = 1;
        while ((long)(m * f) != m * f) {
            m *= 10;
        }
        long gcd = GCD((long)(m * f), m);
        return new Fraction((long)(m * f)/gcd, m/gcd);
    }

    public static boolean[] notPrime(int n) {
        boolean[] notPrime = new boolean[n];
        notPrime[0] = true;
        notPrime[1] = true;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (!notPrime[i]) {
                for (int j = 2*i; j < n; j += i) {
                    notPrime[j] = true;
                }
            }
        }
        return notPrime;
    }
    
    public static boolean isTruncatablePrime(int n, boolean[] notPrimes) {
        int right = n;
        int pow = 1, left = 0;
        
        while (right > 0) {
            if(notPrimes[right]) {
                return false;
            }
            
            int r = right % 10;
            left += r * pow;
            
            if(notPrimes[left]) {
                return false;
            }
            
            pow *= 10;
            right = right / 10;
        }
        
        return true;
    }
    
    public static boolean isBinaryPalindrome(int n) {
        List<Integer> digits = getBinaryDigits(n);
        for(int i = 0, j = digits.size() - 1; j >= i; i++, j--) {
            if(!digits.get(i).equals(digits.get(j))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isPalindrome(String s) {
        for(int i = 0, j = s.length() - 1; j >= i; i++, j--) {
            if(s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isPalindrome(long n) {
        List<Integer> digits = getDigits(n);
        for(int i = 0, j = digits.size() - 1; j >= i; i++, j--) {
            if(!digits.get(i).equals(digits.get(j))) {
                return false;
            }
        }
        return true;
    }
    
    public static int sumOfDigits(String sNum) {
        int sum = 0;
        for(int i = 0; i < sNum.length(); i++) {
            sum += sNum.charAt(i) - '0';
        }
        return sum;
    }
    
    public static String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for(int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }
    
    public static BigDecimal reverse(BigDecimal n) {
        BigDecimal reversed = BigDecimal.ZERO;
        while(n.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal remainder = n.remainder(BigDecimal.TEN);
            reversed = reversed.multiply(BigDecimal.TEN).add(n.remainder(BigDecimal.TEN));
            n = n.subtract(remainder).divide(BigDecimal.TEN);
        }
        return reversed;
    }
    
    public static long reverse(long n) {
        long reversed = 0;
        while(n > 0) {
            reversed = reversed * 10 + n % 10;
            n = n/10;
        }
        return reversed;
    }
    
    public static boolean formDigitPermutations(long a, long b) {
        int[] digitFreqTab = getDigitFreqTab(a);
        return formDigitPermutations(digitFreqTab, b);
    }
    
    public static boolean formDigitPermutations(int[] digitFreqTab, long b) {
        while (b > 0) {
            int digit = (int)(b % 10);
            if(digitFreqTab[digit]-- == 0) {
                return false;
            }
            b = b / 10;
        }
        for(int freq : digitFreqTab) {
            if(freq != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    public static int[] getDigitFreqTab(long a) {
        int[] digitFreqTab = new int[10];
        while (a > 0) {
            int digit = (int)(a % 10);
            digitFreqTab[digit]++;
            a = a / 10;
        }
        return digitFreqTab;
    }
    
    public static long combinations(int n, int r) {
        long numerator = 1;
        for(int i = 0; i <= r - 1; i++) {
            numerator *= (n - i);
        }
        return numerator/factorial(r);
    }
    
    public static long factorial(int n) {
        long result = 1;
        while(n > 1) {
            result *= n;
            n--;
        }
        return result;
    }
    
    /*Greatest Common Divisor*/
    public static long GCD(long a, long b) {
        if(a == 0) {
            return b;
        }
        if(b == 0) {
           return a;
        }
        long r1 = Math.max(a, b);
        long r2 = Math.min(a, b);
        long reminder;
        
        while((reminder = r1 % r2) != 0) {
            r1 = r2;
            r2 = reminder;
        }
        
        return r2;
    }
    
    public static LinearSolution solveLinearEquation(long a, long b, long c) {
        Coefficients coefficients = GCDExtended(a, b);
        if(c % coefficients.getGcd() != 0) {
            return LinearSolution.emptySolution();
        }
        long x = coefficients.getX();
        long y = coefficients.getY();
        long gcd = coefficients.getGcd();
        long q = c / gcd;
        
        return LinearSolution.solution(x * q, y * q, gcd);
    }

    public static long solveCongruences(List<Congruence> congruences) {
        long result = 0;
        long M = 1;
        for(Congruence congruence : congruences) {
            M *= congruence.getM();
        }
        for(Congruence congruence : congruences) {
            long mi = congruence.getM();
            long ai = congruence.getA();
            long Mi = M / mi;
            long inv = moduloInverse(Mi, mi);
            result = (result + ai * inv * Mi) % M;
        }
        return result;
    }

    /*
    * xi+2 = xi - q * xi+1
    * yi+2 = yi - q * yi+1
    * r = a - q * b => a * 1 + b *(-q) => x2 = 1 and y2 = -q
    * */
    public static Coefficients GCDExtended(long a, long b) {
        if(a == 0) {
            return new Coefficients(b, 0, 1);
        }
        if(b == 0) {
            return new Coefficients(a, 1, 0);
        }
        
        long x = 1, y = 0, x0, y0, x1 = 0, y1 = 1;

        long q, reminder;
        while (b != 0) {
            q = a / b;

            x0 = x1;
            y0 = y1;

            x1 = x - q * x1;
            y1 = y - q * y1;

            x = x0;
            y = y0;
            
            reminder = a % b;
            a = b;
            b = reminder;
        }
        
        return new Coefficients(a, x, y);
    }
    
    public static BigDecimal GCD(BigDecimal a, BigDecimal b) {
        int c = a.compareTo(b);
        BigDecimal r1 = c > 0 ? a : b;
        BigDecimal r2 = c > 0 ? b : a;
        BigDecimal reminder;
        
        while(!(reminder = r1.remainder(r2)).equals(BigDecimal.ZERO)) {
            r1 = r2;
            r2 = reminder;
        }
        
        return r2;
    }
    
    /*Least Common Multiplier*/
    public static int LCM(int a, int b) {
        int m1 = Math.max(a, b);
        int m2 = Math.min(a, b);
        int i = 1;
        
        while((i * m1) % m2 != 0) {
            i++;
        }
        
        return i * m1;
    }

    public static long LCM(List<Integer> values) {
        if(values.isEmpty()) {
           throw new IllegalArgumentException("List must not be empty");
        }
        long lcm = values.get(0);
        for(int i = 1; i < values.size(); i++) {
            int value = values.get(i);
            long gcd = GCD(lcm, value);
            lcm = (long)value * (lcm / gcd);
        }
        return lcm;
    }
    
    public static List<Integer> listPrimeFactors(int n, boolean[] primesSieve) {
        List<Integer> result = new ArrayList<>();
        int left = n;
        for(int i = 2; i * i <= left; i++) {
            if(left % i == 0 && primesSieve[i]) {
                while(left % i == 0) {
                    left = left / i;
                }
                result.add(i);
                if(primesSieve[left]) {
                    result.add(left);
                    break;
                }
                i = 2;
            }
        }
        return result;
    }
    
    public static List<PrimeFactor> listPrimeFactors(long n) {
        List<PrimeFactor> result = new ArrayList<>();

        for (int num : new int[]{2, 3, 5}) {
            int pow = 0;
            while (n % num == 0) {
                n = n / num;
                pow++;
            }
            if(pow > 0) {
               result.add(new PrimeFactor(num, pow));
            }
        }

        int[] increments = new int[]{4, 2, 4, 2, 4, 6, 2, 6};
        int i = 0;
        for(long p = 7; p * p <= n; p += increments[i], i = (i + 1) % 8) {
            int pow = 0;
            while (n % p == 0) {
                n = n / p ;
                pow++;
            }
            if(pow > 0) {
                result.add(new PrimeFactor(p, pow));
            }
        }


        /*for(long i = 3; i <= Math.sqrt(n); i += 2) {
            while (n % i == 0) {
                n = n / i ;
                pow++;
            }
            if(pow > 0) {
                result.add(new PrimeFactor(i, pow));
                pow = 0;
            }
        }
*/
        if(n > 1) {
            result.add(new PrimeFactor(n, 1));
        }

        return result;
    }


    
    public static int sumProperDivisors(int n) {
        List<Integer> divisors = listProperDivisors(n);
        return divisors.stream().reduce(0, Integer::sum);
    }
    
    public static List<Integer> listProperDivisors(int n) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        if(isPrime(n)) {
            return result;
        }
        
        int step = n % 2 == 0 ? 1 : 2;
        int start = n % 2 == 0 ? 2 : 3;
        int sqrt = (int)Math.sqrt(n);
        int end = sqrt;
        if(sqrt * sqrt == n) {
            result.add(sqrt);
            end = sqrt - 1;
        }
        
        for(int i = start; i <= end; i += step) {
            if(n % i == 0) {
                result.add(i);
                result.add(n/i);
            }
        }
        
        return result;
    }

    public static List<Long> generatePrimeList(int size) {
        int sieveSize = 2 * size;

        while (true) {
            boolean[] sieve = primesSieve(sieveSize);

            List<Long> result = new ArrayList<>(size);
            for (int i = 2; i < sieve.length; i++) {
                if (sieve[i]) {
                    result.add((long) i);
                    if (result.size() == size) {
                        return result;
                    }
                }
            }
            sieveSize *= 2;
        }
    }
    
    public static List<Long> generatePrimesUpTo(int n) {
        return generatePrimesUpTo(n, primesSieve(n));
    }
    
    public static List<Long> generatePrimesUpTo(int n, boolean[] sieve) {
        List<Long> result = new ArrayList<>(n);
        for(int i = 2; i <= n; i++) {
            if(sieve[i]) {
                result.add((long)i);
            }
        }
        return result;
    }

    public static long phi(long n) {
        List<Long> primes = listPrimeFactors(n).stream()
                .map(PrimeFactor::getFactor)
                .collect(Collectors.toList());

        long result = n;
        for(long prime : primes) {
            result = result / prime;
            result = result * (prime - 1);
        }

        return result;
    }
    
    public static long[] generateTotientsUpTo(int n) {
        long[] totients = new long[n + 1];
        for(int i = 1; i <= n; i++) {
            totients[i] = i;
        }
        for(int i = 2; i <= n; i++) {
            if(totients[i] == i) {
                for(int j = i; j <= n; j = j + i) {
                    totients[j] = totients[j] - totients[j] / i;
                }
            }
        }
        
        return totients;
    }
    
    /*Classic Eratosthenes Sieve*/
    public static boolean[] primesSieve(int n) {
        boolean[] result = new boolean[n + 1];
        Arrays.fill(result, true);
        result[0] = false;
        result[1] = false;
        
        int limit = (int)Math.sqrt(n);
        
        for(int i = 2; i <= limit; i++) {
            if(!result[i]) {
                continue;
            }
            for(int p = i * i; p <= n; p = p + i) {
                result[p] = false;
            }
        }
        
        return result;
    }

    public static long moduloPowerLong(long a, long pow, long mod) {
        if(mod == 1) {
            return 0;
        }

        long result = 1;
        while (pow > 0) {
            if((pow & 1) == 1){
                result = moduloMultiplyLong(a, result, mod);
            }
            a = moduloMultiplyLong(a, a, mod);
            pow = pow >> 1;
        }

        return result;
    }

    public static long moduloPower(long a, long pow, long mod) {
        if(mod == 1) {
            return 0;
        }
        
        long result = 1;
        while (pow > 0) {
            if((pow & 1) == 1){
                result = moduloMultiply(a, result, mod);
            }
            a = moduloMultiply(a, a, mod);
            pow = pow >> 1;
        }
        
        return result;
    }

    public static long moduloMultiplyLong(long a, long b, long mod) {
        long modA = a % mod;
        long modB = b % mod;

        try {
            long m = Math.multiplyExact(modA, modB);
            return m % mod;
        } catch (ArithmeticException e) {
            return moduloMultiply(a, b, mod);
        }
    }

    public static long moduloMultiply(long a, long b, long mod) {
        return BigInteger.valueOf(a)
                         .multiply(BigInteger.valueOf(b))
                         .remainder(BigInteger.valueOf(mod)).longValue();
    }
    
    public static boolean isPrime(long n) {
        if(n <= 1) {
            return false;
        }
        if(n % 2 == 0) {
            return n == 2;
        }
        if(n < 9) {
            return true;
        }
        if(n % 3 == 0) {
            return false;
        }
        
        long root = (long)Math.sqrt(n);
        long i = 5;
        
        while(i <= root) {
            if(n % i == 0) {
                return false;
            }
            if(n % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }
        
        return true;
    }
    
    public static List<Integer> writtenMultiplication(List<Integer> a, int b) {
        return writtenMultiplication(a, getDigits(b));
    }
    
    public static List<Integer> writtenMultiplication(List<Integer> a, List<Integer> b) {
        List<Integer> product = new ArrayList<>(a.size() + b.size());
        for(int i = 0; i < a.size() + b.size(); i++) {
            product.add(0);
        }
        
        List<Integer> n1 = a.size() <= b.size() ? a : b;
        List<Integer> n2 = b.size() >= a.size() ? b : a;
        
        for(int i = 0; i < n1.size(); i++) {
            List<Integer> digitProduct = multipleByDigit(n2, n1.get(i));
            for(int k = 0; k < i; k++) {
                digitProduct.add(0, 0);
            }
            product = writtenAddition(product, digitProduct);
        }
        
        return product;
    }
    
    public static List<Integer> multipleByDigit(List<Integer> a, int digit) {
        List<Integer> product = new ArrayList<>(a.size() + 1);
        
        int res = 0;
        for(int num : a) {
            int m = digit * num + res;
            res = m/10;
            product.add(m % 10);
        }
        if(res > 0) {
            product.add(res);
        }
        return product;
    }
    
    public static List<Integer> writtenAddition(List<Integer> a, List<Integer> b) {
        int size = Math.max(a.size(), b.size()) + 1;
        List<Integer> sum = new ArrayList<>(size);
        List<Integer> n1 = a.size() <= b.size() ? a : b;
        List<Integer> n2 = b.size() >= a.size() ? b : a;
        
        int res = 0;
        int pos = 0;
        while(pos < n1.size()) {
            int s = n1.get(pos) + n2.get(pos) + res;
            res = s/10;
            sum.add(s % 10);
            pos++;
        }
        while(pos < n2.size()) {
            int s = n2.get(pos) + res;
            res = s/10;
            sum.add(s % 10);
            pos++;
        }
        if(res > 0) {
            sum.add(res);
        }
        return sum;
    }
    
    public static List<Integer> getBinaryDigits(long n) {
        List<Integer> result = new ArrayList<>();
        while(n > 0) {
            result.add(result.size(), (int)n % 2);
            n = n/2;
        }
        return result;
    }
    
    public static List<Integer> getDigitsReversed(long n) {
        LinkedList<Integer> reversed = new LinkedList<>();
        while (n > 0) {
            reversed.addFirst((int)n % 10);
            n = n / 10;
        }
        return reversed;
    }
    
    public static List<Integer> getDigits(long n) {
        List<Integer> result = new ArrayList<>();
        while(n > 0) {
            result.add(result.size(), (int)n%10);
            n = n/10;
        }
        return result;
    }
    
    public static int getDigitsCount(long n) {
        int result = 0;
        while(n > 0) {
            result++;
            n = n/10;
        }
        return result;
    }
    
    public static int getDigitsCount(BigDecimal n) {
        return n.toString().length();
    }
    
    public static Set<Long> generateRotationValues(long n) {
        return generateRotationValues(n, getDigits(n));
    }
    
    public static Set<Long> generateRotationValues(long n, List<Integer> digits) {
        Set<Long> result = new LinkedHashSet<>();
        result.add(n);
        
        int rotationsCount = digits.size() - 1;
        for(int i = 1; i <= rotationsCount; i++) {
            n = rotateRight(n, digits.size());
            result.add(n);
        }
        
        return result;
    }
    
    public static Set<Long> generatePermutationValues(long n) {
        List<List<Integer>> permutations = generatePermutations(n);
        Set<Long> result = new TreeSet<>();
        for(List<Integer> permutation : permutations) {
            Long value = toValue(permutation);
            result.add(value);
        }
        return result;
    }
    
    public static List<List<Integer>> generatePermutations(long n) {
        List<Integer> digits = getDigits(n);
        List<List<Integer>> permutations = new ArrayList<>();
        permutation(digits, 0, permutations);
        return permutations;
    }
    
    public static long toValue(List<Integer> digits) {
        long value = 0, pow = 0;
        int pos = 0;
        while(digits.get(pos) == 0) {
            pos++;
        }
        for(int i = pos; i < digits.size(); i++, pow++) {
            int digit = digits.get(i);
            value += (long)(Math.pow(10, pow) * digit);
        }
        return value;
    }
    
    
    public static int[] getMaxSubArrayIndex(int[] tab) {
        int currentSum = 0, maxSum = Integer.MIN_VALUE, left = 0, right = tab.length - 1;
        int shift = left;
        for(int i = 0; i < tab.length; i++) {
            currentSum += tab[i];
            if(currentSum > maxSum) {
                maxSum = currentSum;
                left = shift;
                right = i;
            }
            else if(currentSum < 0) {
                currentSum = 0;
                shift = i + 1;
            }
        }
        return new int[]{left, right};
    }

    public static int[] shuffle(int[] input) {
        Random r = new Random();
        for(int i = input.length - 1; i > 0; i--) {
           int nextIdx = r.nextInt(i + 1);
           swap(input, i, nextIdx);
        }
        return input;
    }

    public static long moduloInverse(long x, long m) {
        if(x > m) {
           x = x % m;
        }
        if(x <= 1) {
           return x;
        }
        return m - (m / x) * moduloInverse(m % x, m);
    }

    public static int[] modInvInRange(int m) {
        int[] inv = new int[m + 1];
        inv[1] = 1;
        for(int i = 2; i < m; i++) {
            inv[i] = m - (m / i) * inv[m % i] % m;
        }
        return inv;
    }

    public static List<Long> modInverseInList(List<Long> list, int m) {
        List<Long> invList = new ArrayList<>(list.size());
        long v = 1;
        for (Long value : list) {
            invList.add(v);
            v = (v * value) % m;
        }
        long invV = moduloInverse(v, m);
        v = 1;
        for(int i = invList.size() - 1; i >= 0; i--) {
            long inv = (v * invList.get(i) * invV) % m;
            invList.set(i, inv);
            v = (v * list.get(i)) % m;
        }
        return invList;
    }
    
    private static List<Integer> rotateLeft(List<Integer> digits) {
        int firstNum = digits.get(0);
        for(int i = 0; i <= digits.size() - 2; i++) {
            digits.set(i, digits.get(i + 1));
        }
        digits.set(digits.size() - 1, firstNum);
        return digits;
    }
    
    private static long rotateRight(long n, int numOfDigits) {
        long res = n % 10;
        return  (n - res)/10 + (long)Math.pow(10, numOfDigits - 1) * res;
    }
    
    private static void permutation(List<Integer> t, int pos, List<List<Integer>> permutationSet) {
        if(pos == t.size() - 1) {
            permutationSet.add(new ArrayList<>(t));
            //permutationListener.permutation(t);
        }
        else {
            permutation(t, pos + 1, permutationSet);
            for(int i = pos + 1; i < t.size(); i++) {
                swapInList(t, pos, i);
                permutation(t, pos + 1, permutationSet);
                swapInList(t, pos, i);
            }
        }
        //Number of swaps: n! + 2(n-1)
    }
    
    private static List<Integer> toList(int[] t) {
        List<Integer> output = new ArrayList<Integer>();
        for (int value : t) {
            output.add(value);
        }
        return output;
    }
    
    private static void swapInList(List<Integer> t, int i, int j) {
        int temp = t.get(i);
        t.set(i, t.get(j));
        t.set(j, temp);
    }
    
    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }
}
