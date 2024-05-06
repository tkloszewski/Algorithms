package com.smart.tkl.euler;

import com.smart.tkl.lib.utils.Divisors;
import com.smart.tkl.lib.utils.MathUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static com.smart.tkl.lib.utils.MathUtils.*;

public class ProblemSet3 {

    private static final String NAMES_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p022_names.txt";

    public static void main(String[] args) {
        //13752620

        System.out.println("Spiral diagonals: " + spiralDiagonals(500) );
        System.out.println("sumAmicableDivisorsLessThan 10000: " + sumAmicableDivisorsLessThan(10000));
        System.out.println("Index of first 1000-digit Fib number: " + getIndexOfFibNumber(5000));
        System.out.println("names scores: "  + namesScores());
        System.out.println("Non-abundant sums: " + listNonAbundantNumbersLessThan(28123));
        System.out.println("Reciprocal cycles: " + reciprocalCycles());
        System.out.println("Quadratic primes: " + quadraticPrimesMaxProduct());
        System.out.println("distinctPowers: " + distinctPowerTerms(100, 100));
        System.out.println("Digit fifth powers: " + sumOfDigitsNthPowerNumbers(6));
    }

    public static long sumOfDigitsNthPowerNumbers(int power) {
        long maxValue = getMaximumDigitPowersValue(power);
        List<Long> numsAsDigitsPowers = new ArrayList<>();
        for(long i = 2; i <= maxValue; i++) {
            long value = getDigitsToPowerValue(getDigits(i), power);
            if(i == value) {
               numsAsDigitsPowers.add(i);
            }
        }
        return numsAsDigitsPowers.stream().reduce(0L, Long::sum);
    }

    public static int distinctPowerTerms(int a, int b) {
        Set<BigDecimal> values = new HashSet<>((a - 1) * (b - 1));
        for(int i = a; i >= 2; i--) {
            for(int j = b; j >= 2; j--) {
                BigDecimal value = new BigDecimal(i).pow(j);
                values.add(value);
            }
        }
        return values.size();
    }

    /* (2 / 3) * (8n^3 + 15n^2 + 13n) + 1
    * */
    private static BigInteger spiralDiagonals(long n) {
        BigInteger mod = BigInteger.valueOf((long)Math.pow(10, 9) + 7);
        BigInteger bn = BigInteger.valueOf(n);
        BigInteger t1 = bn.pow(3).multiply(BigInteger.valueOf(8));
        BigInteger t2 = bn.pow(2).multiply(BigInteger.valueOf(15));
        BigInteger t3 = bn.multiply(BigInteger.valueOf(13));

        return BigInteger.TWO.multiply(t1.add(t2).add(t3)).divide(BigInteger.valueOf(3)).add(BigInteger.ONE);
    }

    public static int quadraticPrimesMaxProduct() {
        List<Integer> primes = new ArrayList<>(500);
        for(int i = 1; i <= 1000;i ++) {
            if(isPrime(i)) {
                primes.add(i);
            }
        }

        QuadraticPrimes quadraticPrimes = new QuadraticPrimes(primes);

        int maxProduct = 0, longestSeq = 0;
        for(int b : primes) {
            for(int a = -999; a < 1000; a++) {
                int seqLength = quadraticPrimes.numberOfConsecutivePrimes(a, b);
                if(seqLength > longestSeq) {
                    longestSeq = seqLength;
                    maxProduct = a * b;
                }
            }
        }

        return maxProduct;
    }

    public static int reciprocalCycles() {
        int maxCycleLength = 0;
        int d = 2;
        for(int i = 2; i <= 10000; i++) {
            List<Integer> cycle = getCycle(i);
            int cycleLength = cycle.size();
            if(cycleLength > maxCycleLength) {
                maxCycleLength = cycleLength;
                d = i;
            }
        }
        return d;
    }

    public static List<Integer> getCycle(int n) {
        List<Integer> cycle = new ArrayList<>();
        Map<Integer, Integer> cycleMap = new LinkedHashMap<>();
        int value = 1;
        int pos = 0;
        while(!cycleMap.containsKey(value)) {
            cycleMap.put(value, pos);
            cycle.add(value * 10/ n);
            value = (value * 10) % n;
            if(value == 0) {
                return Collections.emptyList();
            }
            pos++;
        }
        return cycle.subList(cycleMap.get(value), cycle.size());
    }

    public static int listNonAbundantNumbersLessThan(int n) {
        List<Integer> abundantNumbers = new ArrayList<>(5000);
        Set<Integer> abundantSums = new HashSet<>();
        List<Integer> nonAbundantNumbers = new ArrayList<>(5000);

        for(int i = 1; i <= n; i++) {
            if(isAbundant(i)) {
                abundantNumbers.add(i);
            }
        }
        for(int i = 0; i < abundantNumbers.size(); i++) {
            for(int j = 0; j < abundantNumbers.size(); j++) {
                int abundant1 = abundantNumbers.get(i);
                int abundant2 = abundantNumbers.get(j);
                if(abundant1 + abundant2 > n) {
                    break;
                }
                abundantSums.add(abundant1 + abundant2);
            }
        }

        for(int i = 1; i <= n; i++) {
            if(!abundantSums.contains(i)) {
                nonAbundantNumbers.add(i);
            }
        }

        return nonAbundantNumbers.stream().reduce(0, Integer::sum);
    }

    private static boolean isAbundant(long n) {
        return sumProperDivisors(n) > n;
    }

    public static long namesScores() {
        long score = 0;
        List<String> names = getNamesFromFile();
        Collections.sort(names);
        for(int i = 0; i < names.size(); i++) {
            int pos = i + 1;
            int nameScore = getScore(names.get(i));
            score += (long)pos * nameScore;
        }
        return score;
    }


    private static List<String> getNamesFromFile() {
        List<String> names = new ArrayList<>(5000);

        try(FileReader fr = new FileReader(NAMES_FILE_PATH)) {
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

    public static int getIndexOfFibNumber(int n) {
        int[] firsTerms = new int[n + 1];
        List<Integer> a = getDigits(1);
        List<Integer> b = getDigits(1);
        int digitsNumber = 1;
        int idx = 2;

        firsTerms[1] = 1;
        int digitsSize = 1;

        while(digitsNumber != n) {
            List<Integer> sum = writtenAddition(a, b);
            a = b;
            b = sum;
            digitsNumber = getDigitsSize(b);
            idx++;

            if(digitsNumber > digitsSize) {
                firsTerms[digitsNumber] = idx;
                digitsSize = digitsNumber;
            }
        }
        System.out.println(b);
        return firsTerms[n];
    }


    public static long sumAmicableDivisorsLessThan(int n) {
        Map<Long, Long> divisorsMap = new HashMap<>();
        Set<Long> amicableNumbers = new LinkedHashSet<>();

        for(long i = 1; i < n; i++) {
            long sum1 = divisorsMap.containsKey(i) ? divisorsMap.get(i) : sumProperDivisors(i);
            divisorsMap.putIfAbsent(i, sum1);
            if(i != sum1) {
                long sum2 = divisorsMap.containsKey(sum1) ? divisorsMap.get(sum1) : sumProperDivisors(sum1);
                divisorsMap.putIfAbsent(sum1, sum2);
                if(sum2 == i) {
                    amicableNumbers.add(i);
                    amicableNumbers.add(sum1);
                }
            }
        }
        System.out.println(amicableNumbers);
        return amicableNumbers.stream().reduce(0L, Long::sum);
    }

    public static long sumProperDivisors(long n) {
        return Divisors.sumProperDivisors(n);
    }

    public static int getDigitsSize(List<Integer> number) {
        for(int i = number.size() - 1; i >= 0; i--) {
            if(number.get(i) != 0) {
                return i + 1;
            }
        }
        return 0;
    }

    private static int getScore(String s) {
        int score = 0;
        for(int i = 0; i < s.length(); i++) {
            score += (1 + (int)s.charAt(i) - (int)'A');
        }
        return score;
    }

    private static class QuadraticPrimes {
        private Set<Integer> primes;

        public QuadraticPrimes(Collection<Integer> primes) {
            this.primes = new HashSet<>(primes);
        }

        public int numberOfConsecutivePrimes(int a, int b) {
            int n = 0;
            int value = b;
            while (this.isPrime(value)) {
                n++;
                value = n * n + a * n + b;
            }
            return n;
        }

        private boolean isPrime(int n) {
            if(n <= 0) {
                return false;
            }
            if(!primes.contains(n)) {
                boolean isPrime = MathUtils.isPrime(n);
                if(isPrime) {
                    primes.add(n);
                }
                return isPrime;
            }
            return true;
        }
    }

    private static long getDigitsToPowerValue(List<Integer> digits, int power) {
        long sum = 0;
        for(int digit : digits) {
            sum += Math.pow(digit, power);
        }
        return sum;
    }

    private static long getMaximumDigitPowersValue(int power) {
        int numOfDigits = 1;
        long value = getMaximumDigitPowersValue(numOfDigits, power);
        while((long)Math.pow(10, numOfDigits) - 1 <= value) {
            value = getMaximumDigitPowersValue(++numOfDigits, power);
        }
        return value;
    }

    private static long getMaximumDigitPowersValue(int numOfDigits, int power) {
        return (long)(numOfDigits * Math.pow(9, power));
    }
}
