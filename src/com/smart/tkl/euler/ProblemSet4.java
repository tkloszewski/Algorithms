package com.smart.tkl.euler;

import com.smart.tkl.lib.combinatorics.Combination;
import com.smart.tkl.lib.combinatorics.CombinationGenerator;
import com.smart.tkl.lib.combinatorics.CombinationListener;
import com.smart.tkl.lib.SortingUtils;
import com.smart.tkl.lib.utils.MathUtils;

import java.util.*;

import static com.smart.tkl.lib.utils.MathUtils.*;

public class ProblemSet4 {

    private static final int[][] MATRIX_5 = new int[][]{
            {7,   53,  183, 439, 863},
            {497, 383, 563, 79,  973},
            {287, 63,  343, 169, 583},
            {627, 343, 773, 959, 943},
            {767, 473, 103, 699, 303}
    };

    private static final double[][] MATRIX_15 = new double[][]{
            {7,   53, 183,  439, 863, 497, 383, 563,  79, 973, 287,  63, 343, 169, 583},
            {627, 343, 773, 959, 943, 767, 473, 103, 699, 303, 957, 703, 583, 639, 913},
            {447, 283, 463,  29,  23, 487, 463, 993, 119, 883, 327, 493, 423, 159, 743},
            {217, 623,   3, 399, 853, 407, 103, 983,  89, 463, 290, 516, 212, 462, 350},
            {960, 376, 682, 962, 300, 780, 486, 502, 912, 800, 250, 346, 172, 812, 350},
            {870, 456, 192, 162, 593, 473, 915,  45, 989, 873, 823, 965, 425, 329, 803},
            {973, 965, 905, 919, 133, 673, 665, 235, 509, 613, 673, 815, 165, 992, 326},
            {322, 148, 972, 962, 286, 255, 941, 541, 265, 323, 925, 281, 601,  95, 973},
            {445, 721,  11, 525, 473,  65, 511, 164, 138, 672,  18, 428, 154, 448, 848},
            {414, 456, 310, 312, 798, 104, 566, 520, 302, 248, 694, 976, 430, 392, 198},
            {184, 829, 373, 181, 631, 101, 969, 613, 840, 740, 778, 458, 284, 760, 390},
            {821, 461, 843, 513,  17, 901, 711, 993, 293, 157, 274,  94, 192, 156, 574},
            {34,  124,   4, 878, 450, 476, 712, 914, 838, 669, 875, 299, 823, 329, 699},
            {815, 559, 813, 459, 522, 788, 168, 586, 966, 232, 308, 833, 251, 631, 107},
            {813, 883, 451, 509, 615,  77, 281, 613, 459, 205, 380, 274, 302,  35, 805}
    };

    public static void main(String[] args) {
        System.out.println("Coin sums: " + coinSums(new Integer[]{1, 2, 5, 10, 20, 50, 100, 200}, 200));
        System.out.println("Coin sums ways: " + coinSumsWays(new int[]{1, 2, 3, 4}, 5));
        System.out.println("Pan digital products: " + panDigitalProducts());
        System.out.println("Digit cancelling fractions: " + digitCancellingFractions());
        System.out.println("Maximum number for factorial digits: " + digitFactorials());
        System.out.println("Circular primes: " + circularPrimesCount(1000000));
        System.out.println("Double-base palindromes: " + doubleBasePalindromes(1000000));
        System.out.println("Truncatable primes: " + truncatablePrimes());
        System.out.println("Pandigital multiples: " + pandigitalMultiples());
        System.out.println("integerRightTriangles: " + integerRightTriangles(1000));
        System.out.println("Chosen digit: " + getNthDigitOfIrrationalDecimalFraction(2898));
        System.out.println("Champernowne's constant: " + C10());
    }

    /*Champernowne constant */
    public static int C10() {
        int result = 1;
        for(int i = 1; i <= 1000000; i *= 10) {
            int digit = getNthDigitOfIrrationalDecimalFraction(i);
            result *= digit;
        }
        return result;
    }

    private static int getNthDigitOfIrrationalDecimalFraction(int n) {
        int integersCount = 9, digitsCount = 1, previousTotalDigits = 0;
        int totalDigitsCount = integersCount * digitsCount;

        while(totalDigitsCount < n) {
            integersCount *= 10;
            digitsCount++;
            previousTotalDigits = totalDigitsCount;
            totalDigitsCount += integersCount * digitsCount;
        }
        
        int digitDiff = (n - previousTotalDigits);
        int chosenNumber = (int)Math.pow(10, digitsCount - 1) + (digitDiff - 1)/digitsCount;
        int chosenDigitIdx = (digitDiff - 1) % digitsCount;

        String chosenDigit = String.valueOf(chosenNumber).substring(chosenDigitIdx, chosenDigitIdx + 1);
        return Integer.parseInt(chosenDigit);
    }

    public static int integerRightTriangles(int limit) {
        Map<Integer, Set<RightTriangle>> solutionsMap = new LinkedHashMap<>();

        /*b = p(p-2a)/2(p-a)*/
        //p must even
        for(int p = 3 + 4 + 5; p <= limit; p += 2) {
            Set<RightTriangle> solutions = new LinkedHashSet<>();
            for(int a = 3; a <= p/2 -1; a++) {
                int numerator = p * (p - 2 * a);
                int denominator = 2 * (p - a);
                if(numerator % denominator == 0) {
                   solutions.add(new RightTriangle(a, numerator/ denominator));
                }
            }
            if(solutions.size() > 0) {
               solutionsMap.put(p, solutions);
            }
        }


        int maxSolutions = 0;
        Map.Entry<Integer, Set<RightTriangle>> maxEntry = null;
        for(Map.Entry<Integer, Set<RightTriangle>> entry : solutionsMap.entrySet()) {
            if(entry.getValue().size() > maxSolutions) {
               maxSolutions = entry.getValue().size();
               maxEntry = entry;
            }
        }

        return maxEntry != null ? maxEntry.getKey() : 0;
    }

    public static int pandigitalMultiples() {
        int maxPandigitalProduct = Integer.MIN_VALUE;
        Map<Integer, Integer> panDigitalNumbers = new LinkedHashMap<>();
        for(int i = 2; i < 10000; i++) {
            Optional<Integer> panDigitalOptional = getPandigitalProductOrEmpty(i);
            if(panDigitalOptional.isPresent()) {
                int panDigitalProduct = panDigitalOptional.get();
                if (maxPandigitalProduct < panDigitalProduct) {
                    maxPandigitalProduct = panDigitalProduct;
                }
                panDigitalNumbers.put(i, panDigitalProduct);
            }
        }
        return maxPandigitalProduct;
    }

    public static int truncatablePrimes() {
        final int NUM_OF_PRIMES = 11;
        int limit = 1000000;
        boolean[] notPrimes = notPrime(limit);
        List<Integer> truncatablePrimes = new ArrayList<>(NUM_OF_PRIMES);
        for(int i = 11; truncatablePrimes.size() < NUM_OF_PRIMES && i > 0; i += 2) {
            if(isTruncatablePrime(i, notPrimes)) {
                truncatablePrimes.add(i);
                System.out.println("Truncantable primes: " + truncatablePrimes);
            }
        }
        return truncatablePrimes.stream().reduce(0, Integer::sum);
    }

    public static int doubleBasePalindromes(int limit) {
        List<Integer> palindromes = new ArrayList<>();
        for(int i = 1; i < limit; i++) {
            if(i % 2 == 1) {
                if(isPalindrome(i) && isBinaryPalindrome(i)) {
                    palindromes.add(i);
                }
            }
        }


        return palindromes.stream().reduce(0, Integer::sum);
    }

    public static int circularPrimesCount(int limit) {
        Set<Long> cachedPrimes = new HashSet<>(limit);
        cachedPrimes.addAll(generatePrimesUpTo(limit));

        Set<Long> circularPrimes = new TreeSet<>();
        circularPrimes.add(2L);
        circularPrimes.add(5L);
        for(long prime : cachedPrimes) {
            List<Integer> digits = MathUtils.getDigits(prime);
            if(!containsEvenOrFive(digits)) {
                Set<Long> rotations = MathUtils.generateRotationValues(prime, digits);
                if (cachedPrimes.containsAll(rotations)) {
                    circularPrimes.addAll(rotations);
                }
            }
        }

        return circularPrimes.size();
    }

    private static Optional<Integer> getPandigitalProductOrEmpty(int n) {
        String s = String.valueOf(n);
        for(int i = 2; !containsZeroOrIsNonUnique(s) && s.length() < 9; i++) {
            s = s + i * n;
        }
        if(s.length() != 9 || containsZeroOrIsNonUnique(s)) {
            return Optional.empty();
        }
        return Optional.of(Integer.valueOf(s));
    }

    private static boolean containsZeroOrIsNonUnique(String s) {
        if(s.contains("0")) {
           return true;
        }
        Set<Integer> uniqueChars = new HashSet<>();
        for(char c : s.toCharArray()) {
            uniqueChars.add((int)c);
        }
        return uniqueChars.size() != s.length();
    }

    private static boolean containsEvenOrFive(List<Integer> digits) {
        for(int digit : digits) {
            if(digit % 2 == 0 || digit == 5) {
                return true;
            }
        }
        return false;
    }

    public static long digitFactorials() {
        Map<Integer, Long> factorialMap = new HashMap<>();
        for(int i = 0; i <= 9; i++) {
            factorialMap.put(i, MathUtils.factorial(i));
        }

        List<Long> digitFactorials = new ArrayList<>();
        for(long i = 3; i < getMaximumNumberForFactorialDigits(); i++) {
            long digitFactorial = getDigitsFactorialSum(i, factorialMap);
            if(digitFactorial == i) {
                digitFactorials.add(digitFactorial);
            }
        }

        System.out.println(digitFactorials);

        return digitFactorials.stream().reduce(0L, Long::sum);
    }

    public static long digitCancellingFractions() {
        List<List<Long>> list = new ArrayList<>();
        for(int i = 11; i < 99; i++) {
            if(i % 10 == 0) {
                continue;
            }
            for(int j = i + 1; j <= 99; j++) {
                if(j % 10 == 0) {
                    continue;
                }
                if(haveDigitCancellingFractions(i, j)) {
                    List<Long> fractions = new ArrayList<>(2);
                    long biggestDivisor = MathUtils.GCD(i, j);
                    fractions.add(i/biggestDivisor);
                    fractions.add(j/biggestDivisor);
                    list.add(fractions);
                }
            }
        }

        int numerator = 1;
        int denominator = 1;

        for(List<Long> fraction : list) {
            numerator *= fraction.get(0);
            denominator *= fraction.get(1);
        }

        long biggestDivisor = MathUtils.GCD(numerator, denominator);
        return denominator/ biggestDivisor;
    }

    public static int panDigitalProducts() {
        Map<Integer, Set<Integer>> panDigitalMap = new TreeMap<>();

        for(int i = 2; i <= 98; i++) {
            if(i % 10 == 0 || i % 11 == 0) {
                continue;
            }
            List<Integer> digits1 = MathUtils.getDigits(i);
            for(int j = 12; j <= 9876; j++) {
                if(i > 9 && j > 999) {
                    break;
                }
                int product = i * j;
                if(product >= 64155) {
                    break;
                }
                if(product < 1234) {
                    continue;
                }
                List<Integer> digits2 = MathUtils.getDigits(j);
                if(hasUniqueDigitsWithoutZero(digits2) && haveDistinctDigits(digits1, digits2)) {
                    List<Integer> productDigits = MathUtils.getDigits(product);
                    if(hasUniqueDigitsWithoutZero(productDigits)) {
                        Set<Integer> retainedDigits = getRetainedDigits(digits1, digits2);
                        if(retainedDigits.equals(new HashSet<>(productDigits))) {
                            if(!panDigitalMap.containsKey(product)) {
                                Set<Integer> set = new HashSet<>();
                                set.add(i);
                                set.add(j);
                                panDigitalMap.put(product, set);
                            }
                        }
                    }
                }
            }
        }

        return panDigitalMap.keySet().stream().reduce(0, Integer::sum);
    }

    public static int coinSumsWays(int[] coinValues, int amount) {
        if(amount > 1000000) return 0;
        coinValues = SortingUtils.insertionSort(coinValues);
        int[] ways = new int[amount + 1];
        ways[0] = 1;
        for(int coinValue : coinValues) {
            for(int j = coinValue; j <= amount; j++) {
                ways[j] = ways[j] + ways[j - coinValue];
            }
        }
        return ways[amount];
    }

    public static int coinSums(Integer[] coinValues, int amount) {
        int result = 0;

        List<CombinationCatcher> combinationCatchers = new ArrayList<>();
        for(int length = 1; length <= coinValues.length; length++) {
            CombinationCatcher combinationCatcher = new CombinationCatcher(length);
            combinationCatchers.add(combinationCatcher);
            new CombinationGenerator<>(coinValues, length, combinationCatcher).generate();
        }
        for(CombinationCatcher combinationCatcher : combinationCatchers) {
            for(Set<Integer> combination : combinationCatcher.combinations) {
                List<Integer> sortedCombination = new ArrayList<>(combination);
                List<TermSet> termSets = getTerms(sortedCombination, amount);
                result += termSets.size();
            }
        }

        return result;
    }

    private static List<TermSet> getTerms(List<Integer> values, int sum) {
        if(values.size() == 1) {
            List<TermSet> result = new ArrayList<>(1);
            int value = values.get(0);
            if(sum % value == 0) {
                Term term = new Term(value, sum/value);
                result.add(new TermSet(term));
            }
            return result;
        }

        List<TermSet> result = new ArrayList<>(10);
        int i = 1;
        int value = values.get(0);
        while(i * value < sum) {
            Term term = new Term(value, i);
            List<TermSet> termSets = getTerms(values.subList(1, values.size()), sum - term.getTotalValue());
            for(TermSet termSet : termSets) {
                if(termSet.size() > 0) {
                    result.add(new TermSet(term, termSet.terms));
                }
            }
            i++;
        }
        return result;
    }

    private static long getDigitsFactorialSum(long n, Map<Integer, Long> factorialMap) {
        long sum = 0;
        for(Integer digit : MathUtils.getDigits(n)) {
            sum += factorialMap.get(digit);
        }
        return sum;
    }

    private static long getMaximumNumberForFactorialDigits() {
        long maxDigitFactor = 362880, n = 6;
        long value = n * maxDigitFactor;
        while(value > Math.pow(10,n) - 1) {
            value = ++n * maxDigitFactor;
        }
        return value;
    }

    private static Set<Integer> getRetainedDigits(List<Integer> digits1, List<Integer> digits2) {
        Set<Integer> retainedSet = new HashSet<>();
        for(int i = 1; i <= 9; i++) {
            retainedSet.add(i);
        }
        retainedSet.removeAll(digits1);
        retainedSet.removeAll(digits2);
        return retainedSet;
    }

    private static boolean haveDigitCancellingFractions(int n1, int n2) {
        if(n1 > 99 || n2 > 99 || n1 < 11 || n2 < 11) {
            return false;
        }
        int a = n1/10;
        int b = n1 % 10;
        int c = n2/10;
        int d = n2 % 10;
        if(b != c) {
            return false;
        }
        return 9 * a * d == c * (10 * a - d);
    }

    private static boolean haveDistinctDigits(List<Integer> digits1, List<Integer> digits2) {
        Set<Integer> digitSet = new HashSet<>(digits1);
        digitSet.addAll(digits2);
        return digitSet.size() == digits1.size() + digits2.size();
    }

    private static boolean hasUniqueDigitsWithoutZero(int n) {
        if(n <= 9 && n > 0) {
            return true;
        }
        return hasUniqueDigitsWithoutZero(MathUtils.getDigits(n));
    }

    private static boolean hasUniqueDigitsWithoutZero(List<Integer> digits) {
        if(digits.contains(0)) {
            return false;
        }
        return new HashSet<>(digits).size() == digits.size();
    }

    private static class CombinationCatcher implements CombinationListener<Integer> {

        int length;
        List<Set<Integer>> combinations = new ArrayList<>();

        public CombinationCatcher(int length) {
            this.length = length;
        }

        @Override
        public void combinationGenerated(Combination<Integer> combination) {
            combinations.add(combination.getData());
        }
    }

    private static class TermSet {
        List<Term> terms;
        int sum;

        public TermSet(Term term) {
            this(Collections.singletonList(term));
        }

        public TermSet(List<Term> terms) {
            this.terms = terms;
            this.sum  = this.terms.stream().map(Term::getTotalValue).reduce(0, Integer::sum);
        }

        public TermSet(Term term, List<Term> terms) {
            this.terms = new ArrayList<>();
            this.terms.add(term);
            this.terms.addAll(terms);
            this.sum  = this.terms.stream().map(Term::getTotalValue).reduce(0, Integer::sum);
        }

        public int size() {
            return terms.size();
        }

        @Override
        public String toString() {
            return "TermSet{" +
                    "terms=" + terms +
                    ", sum=" + sum +
                    '}';
        }
    }

    private static class Term implements Comparable<Term> {
        Integer value;
        int count;

        public Term(int value, int count) {
            this.value = value;
            this.count = count;
        }

        public int getTotalValue() {
            return value * count;
        }

        @Override
        public int compareTo(Term o) {
            return -(this.value.compareTo(o.value));
        }

        @Override
        public String toString() {
            return "Term{" +
                    "value=" + value +
                    ", count=" + count +
                    '}';
        }
    }

    private static class RightTriangle {
        int a;
        int b;
        int c;

        public RightTriangle(int a, int b) {
            this.a = a;
            this.b = b;
            this.c = (int)Math.sqrt(a * a + b * b);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RightTriangle that = (RightTriangle) o;
            return (a == that.a && b == that.b) || (a == that.b && b == that.a);
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(a).hashCode() + Integer.valueOf(b).hashCode();
        }

        @Override
        public String toString() {
            return "RightTriangle{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }
}
