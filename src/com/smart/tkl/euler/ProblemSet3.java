package com.smart.tkl.euler;

import com.smart.tkl.utils.MathUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.smart.tkl.utils.MathUtils.*;

public class ProblemSet3 {

    private static final String NAMES_FILE_PATH = "C:\\Projects\\microservices\\Algorithms\\src\\com\\smart\\tkl\\euler\\p022_names.txt";

    public static void main(String[] args) {
        //13752620

        System.out.println("sumAmicableDivisorsLessThan 10000: " + sumAmicableDivisorsLessThan(10000));
        System.out.println("Index of first 1000-digit Fib number: " + getIndexOfFibNumber(1000));
        System.out.println("names scores: "  + namesScores());
        System.out.println("Non-abundant sums: " + listNonAbundantNumbersLessThan(28123));
        System.out.println("Reciprocal cycles: " + reciprocalCycles());
        System.out.println("Quadratic primes: " + quadraticPrimesMaxProduct());
        System.out.println("distinctPowers: " + distinctPowerTerms(100, 100));
        System.out.println("Digit fifth powers: " + sumOfDigitsNthPowerNumbers(5));
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
        for(int i = 2; i < 1000; i++) {
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

    private static boolean isAbundant(int n) {
        return sumProperDivisors(n) > n;
    }

    public static long namesScores() {
        long score = 0;
        List<String> names = getNamesFromFile();
        Collections.sort(names);
        for(int i = 0; i < names.size(); i++) {
            int pos = i + 1;
            int nameScore = getScore(names.get(i));
            score += pos * nameScore;
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
        List<Integer> a = getDigits(1);
        List<Integer> b = getDigits(1);
        int digitsNumber = 1;
        int idx = 2;
        while(digitsNumber != n) {
            List<Integer> sum = writtenAddition(a, b);
            a = b;
            b = sum;
            digitsNumber = getDigitsSize(b);
            idx++;
        }
        System.out.println(b);
        return idx;
    }


    public static int sumAmicableDivisorsLessThan(int n) {
        Map<Integer, Integer> divisorsMap = new HashMap<>();
        Set<Integer> amicableNumbers = new LinkedHashSet<>();

        for(int i = 1; i < n; i++) {
            int sum1 = divisorsMap.containsKey(i) ? divisorsMap.get(i) : sumProperDivisors(i);
            divisorsMap.putIfAbsent(i, sum1);
            if(i != sum1) {
                int sum2 = divisorsMap.containsKey(sum1) ? divisorsMap.get(sum1) : sumProperDivisors(sum1);
                divisorsMap.putIfAbsent(sum1, sum2);
                if(sum2 == i) {
                    amicableNumbers.add(i);
                    amicableNumbers.add(sum1);
                }
            }
        }
        System.out.println(amicableNumbers);
        return amicableNumbers.stream().reduce(0, Integer::sum);
    }

    public static int sumProperDivisors(int n) {
        List<Integer> divisors = listProperDivisors(n);
        return divisors.stream().reduce(0, Integer::sum);
    }



    private static int getDigitsSize(List<Integer> number) {
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
