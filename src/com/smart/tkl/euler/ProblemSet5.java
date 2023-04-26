package com.smart.tkl.euler;

import com.smart.tkl.lib.combinatorics.permutation.SwapPermutationGenerator;
import com.smart.tkl.lib.combinatorics.permutation.PermutationListener;
import com.smart.tkl.lib.utils.GenericUtils;
import com.smart.tkl.lib.utils.MathUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.smart.tkl.lib.utils.MathUtils.isPrime;

public class ProblemSet5 {

    private static final String TRIANGLE_WORDS_FILE_PATH = "c";

    private static volatile long time1, time2;

    public static void main(String[] args) {
        time1 = System.currentTimeMillis();
        System.out.println("Pan digital prime1: " + panDigitalPrime());
        time2 = System.currentTimeMillis();
        System.out.println("Time1: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        System.out.println(codedTriangleNumbers());
        time2 = System.currentTimeMillis();
        System.out.println("Time2: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        System.out.println(panDigitalNumbers());
        time2 = System.currentTimeMillis();
        System.out.println("Time3: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        System.out.println("Pentagonal numbers: " + pentagonalNumbers());
        time2 = System.currentTimeMillis();
        System.out.println("Time4: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        System.out.println("Next triangularPentagonalHexagonal: " + nextTriangularPentagonalHexagonal());
        time2 = System.currentTimeMillis();
        System.out.println("Time5: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        System.out.println("Smallest Goldbach other conjecture: " + smallestGoldbachOtherConjecture());
        time2 = System.currentTimeMillis();
        System.out.println("Time6: " + (time2 - time1));


        time1 = System.currentTimeMillis();
        System.out.println("Distinct prime factors: " + distinctPrimeFactors());
        time2 = System.currentTimeMillis();
        System.out.println("Time7: " + (time2 - time1));


        System.out.println("selfPowers: " + selfPowers());
        System.out.println("Consecutive prime sum: " + consecutivePrimesSum(1000000));
    }

    public static long consecutivePrimesSum(int limit) {
        boolean[] primesSieve = MathUtils.primesSieve(limit);
        List<Integer> primes = getPrimes(primesSieve);

        int longestSeqLength = 0;

        List<Integer> result = new ArrayList<>();
        for(int from = 0; from < primes.size(); from++) {
            if(primes.size() - from < longestSeqLength) {
               break;
            }
            List<Integer> consecutivePrimes = getConsecutivePrimes(from, primes, primesSieve);
            if(consecutivePrimes.size() > longestSeqLength) {
                longestSeqLength = consecutivePrimes.size();
                result = consecutivePrimes;
            }
        }


        return result.stream().reduce(0, Integer::sum);
    }

    private static List<Integer> getConsecutivePrimes(int from, List<Integer> primes, boolean[] primesSieve) {
        List<Integer> result = new ArrayList<>();

        int maxPrime = primes.get(primes.size() - 1);
        List<Integer> consecutivePrimes = new ArrayList<>();

        for(int i = from, sum = 0; i < primes.size() && sum <= maxPrime; i++) {
            if(primesSieve[sum]) {
               result = new ArrayList<>(consecutivePrimes);
            }
            sum += primes.get(i);
            consecutivePrimes.add(primes.get(i));
        }
        return result;
    }


    private static List<Integer> getPrimes(boolean[] primesSieve) {
        List<Integer> primes = new ArrayList<>(primesSieve.length);
        for(int i = 0; i < primesSieve.length; i++) {
            if(primesSieve[i]) {
               primes.add(i);
            }
        }
        return primes;
    }

    public static String selfPowers() {
        BigDecimal result = BigDecimal.ZERO;
        for(long i = 1; i <= 1000; i++) {
            result = result.add(new BigDecimal(i).pow((int)i));
        }
        String s = result.toString();
        return s.substring(s.length() - 10);
    }

    public static int distinctPrimeFactors() {
        final int limit = 1000000;
        boolean[] primesSieve = MathUtils.primesSieve(limit);

        int result = -1;

        for(int i = 2 * 3 * 5 * 7; i <= limit - 4; i++) {
            if(!primesSieve[i] && !primesSieve[i + 1] && !primesSieve[i + 2] && !primesSieve[i + 3]) {
                boolean found = true;
                for(int k = i; k <= i + 3; k++) {
                   List<Integer> primeFactors = MathUtils.listPrimeFactors(k, primesSieve);
                   if(primeFactors.size() != 4) {
                      found = false;
                      break;
                   }
                }
                if(found) {
                   result = i;
                   break;
                }
            }
        }

        return result;
    }

    public static long smallestGoldbachOtherConjecture() {
        final int LIMIT = 10000;
        boolean[] primesSieve = MathUtils.primesSieve(LIMIT);

        boolean foundConjecture = true;
        int compositeOdd = 33;
        int result = -1;

        while (foundConjecture && compositeOdd < LIMIT) {
            compositeOdd += 2;
            if(primesSieve[compositeOdd]) {
               continue;
            }

            foundConjecture = false;
            for(int odd = compositeOdd - 2; odd >= 3; odd -= 2) {
                if(primesSieve[odd] && isGoldbachConjecture(compositeOdd, odd)) {
                   foundConjecture = true;
                   break;
                }
            }
        }

        if(!foundConjecture) {
            result = compositeOdd;
        }

        return result;
    }

    private static boolean isGoldbachConjecture(long compositeOdd, long prime) {
        long diff = (compositeOdd - prime)/2;
        double root = Math.sqrt(diff);
        return root == ((int)root);
    }

    public static long nextTriangularPentagonalHexagonal() {
        long result = 0;
        boolean notFound = true;
        long i = 286;
        while (notFound) {
            long triangular = (i * (i + 1)) / 2;

            outer:
            for(long j = i - 1; j >= 2; j--) {
                long pentagonal = (j * (3 * j - 1)) / 2;
                if(pentagonal < triangular) {
                   break;
                }
                if(pentagonal == triangular) {
                   for(long k = j - 1; k >= 2; k--) {
                       long hexagonal = k * (2 * k - 1);
                       if(hexagonal < pentagonal) {
                          break;
                       }
                       if(hexagonal == pentagonal) {
                          result = triangular;
                          notFound = false;
                          break outer;
                       }
                   }
                }
            }
            i++;
        }

        return result;
    }

    public static int pentagonalNumbers() {
        int result = 0;
        boolean notFound = true;
        int i = 2;
        while (notFound) {
            int n = (i * (3 * i - 1)) / 2;
            for(int j = i - 1; j >= 1; j--) {
                int m = (j * (3 * j - 1)) / 2;
                if(isPentagonal(n + m) && isPentagonal(n - m)) {
                    result = n - m;
                    notFound = false;
                    break;
                }
            }
            i++;
        }

        return result;
    }

    private static boolean isPentagonal(int n) {
        double delta = (Math.sqrt(1 + 24 * n) + 1) / 6;
        return delta == ((int)delta);
    }

    public static long panDigitalNumbers() {
        long sum = 0;

        int[] t = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        SubstringPanDigitalListener panDigitalListener = new SubstringPanDigitalListener();
        SwapPermutationGenerator permutationGenerator = new SwapPermutationGenerator(panDigitalListener);
        permutationGenerator.generate(t);

        for(String panDigitalNumber : panDigitalListener.panDigitalNumbers) {
           sum += Long.parseLong(panDigitalNumber);
        }

        return sum;
    }

    public static long codedTriangleNumbers() {
        Set<Integer> triangleNumbers = new HashSet<>(5000);
        for(int i = 1; i <= 5000; i++) {
            triangleNumbers.add( (i * (i + 1))/2);
        }

        int count = 0;
        List<String> words = readTriangleWords();
        for(String word : words) {
            if(triangleNumbers.contains(getWordNumber(word))) {
                count++;
            }
        }

        // return words.stream().filter(ProblemSet5::isTriangleWord).count();
        return count;
    }

    private static List<String> readTriangleWords() {
        List<String> names = new ArrayList<>(2000);

        try(FileReader fr = new FileReader(TRIANGLE_WORDS_FILE_PATH)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                for(String part : line.split(",")) {
                    String name = part.trim().replaceAll("\"","");
                    names.add(name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return names;
    }

    private static boolean isTriangleWord(String word) {
        return isTriangleNumber(getWordNumber(word));
    }

    private static int getWordNumber(String word) {
        int result = 0;
        for(char c : word.toCharArray()) {
            result += c - 'A' + 1;
        }
        return result;
    }

    private static boolean isTriangleNumber(int n) {
        int k = 1 + 8 * n;
        int i = 1;
        for(; i * i < k; i++);
        return i * i == k;
    }

    public static int panDigitalPrime() {
        int largestPanDigital = 0;
        for(int digitsCount = 9; digitsCount >= 1; digitsCount--) {
            PrimePermutationListener permutationListener = new PrimePermutationListener();
            SwapPermutationGenerator permutationGenerator = new SwapPermutationGenerator(permutationListener);
            int[] tab = GenericUtils.newRangeReversed(1, digitsCount);
            permutationGenerator.generate(tab);

            if(permutationListener.getLargestPrimeOptional().isPresent()) {
                largestPanDigital = permutationListener.getLargestPrimeOptional().get();
                break;
            }

        }
        return largestPanDigital;
    }



    private static class SubstringPanDigitalListener implements PermutationListener {
        List<String> panDigitalNumbers = new ArrayList<>();

        @Override
        public void permutation(int[] permutation) {
            if(checkPermutation(permutation)) {
                panDigitalNumbers.add(toString(permutation));
            }
        }

        public boolean checkPermutation(int[] permutation) {
            if(permutation[3] % 2 != 0) {
                return false;
            }
            if(permutation[5] != 0 && permutation[5] != 5) {
                return false;
            }
            if(toSum(permutation, 2, 4) % 3 != 0) {
                return false;
            }
            int value = toValue(permutation, 4, 6);
            if(value % 7 != 0) {
                return false;
            }
            value = toValue(permutation, 5, 7);
            if(value % 11 != 0) {
                return false;
            }
            value = toValue(permutation, 6, 8);
            if(value % 13 != 0) {
                return false;
            }
            value = toValue(permutation, 7, 9);
            return value % 17 == 0;
        }

        private int toSum(int[] permutation, int from, int to) {
            int value = 0;
            for(int k = from; k <= to; k++) {
                value += permutation[k];
            }
            return value;
        }

        private int toValue(int[] permutation, int from, int to) {
            int value = 0, pow = 0;
            for(int k = to; k >= from; k--) {
                value += permutation[k] * Math.pow(10, pow++);
            }
            return value;
        }

        private String toString(int[] permutation) {
            StringBuilder s = new StringBuilder();
            for(int n : permutation) {
                s.append(n);
            }
            return s.toString();
        }

        static Set<String> generateUniqueSubStringDivisibleBy(int n) {
            Set<String> result = new HashSet<>();
            for(int i = 0; i < 1000; i += n) {
                if(i > 10 && GenericUtils.hasUniqueChars(String.valueOf(i))) {
                    result.add(String.valueOf(i));
                }
            }
            return result;
        }
    }

    private static class PrimePermutationListener implements PermutationListener {
        Integer largestPrime = null;

        @Override
        public void permutation(int[] permutation) {
            int lastDigit = permutation[permutation.length - 1];
            if(lastDigit % 2 != 0 && lastDigit % 5 != 0 && toSum(permutation) % 3 != 0) {
                int value = toValue(permutation);
                if((largestPrime == null || largestPrime < value) && isPrime(value)) {
                    largestPrime = value;
                }
            }
        }

        public Optional<Integer> getLargestPrimeOptional() {
            return Optional.ofNullable(largestPrime);
        }

        private int toSum(int[] permutation) {
            int value = 0;
            for(int k = permutation.length - 1; k >= 0; k--) {
                value += permutation[k];
            }
            return value;
        }

        private int toValue(int[] permutation) {
            int value = 0, pow = 0;
            for(int k = permutation.length - 1; k >= 0; k--) {
                value += permutation[k] * Math.pow(10, pow++);
            }
            return value;
        }


    }
}
