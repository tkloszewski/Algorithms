package com.smart.tkl.euler;

import com.smart.tkl.combinatorics.CombinatoricsUtils;
import com.smart.tkl.graph.triangle.TriangleBuilder;
import com.smart.tkl.graph.triangle.TriangleFileReader;
import com.smart.tkl.graph.triangle.TriangleNode;
import com.smart.tkl.graph.triangle.TrianglePathFinder;
import com.smart.tkl.utils.MathUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.smart.tkl.utils.MathUtils.*;

public class ProblemSet6 {

    private static final String P_067_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p067_triangle.txt";
    private static final String P_059_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p059_cipher.txt";

    public static void main(String[] args) {
        System.out.println("XOR decryption: " + xorDecryption());
        System.out.println("Prime digit replacement: " + primeDigitReplacement(8));
        System.out.println("Max path sum2: " + getMaxPathSum67());
        System.out.println("Min path sum2: " + getMinPathSum67());
        System.out.println("Permuted multiples: " + permutedMultiples());
        System.out.println("Combinatoric selections: " + combinatoricSelections());
        System.out.println("Lychrel numbers: " + countLychrelNumbers());
        System.out.println("Powerful digit sum: " + powerfulDigitSum());
        System.out.println("Square root convergents: " + squareRootConvergents());
        System.out.println("Spiral primes: " + spriralPrimes(0.1));
    }

    public static int xorDecryption() {
        int[] message = readEncryptedMessage(P_059_FILE_PATH);
        int[] key = getEncryptionKey(message, 3);
        char[] decryptedMessage = encrypt(message, key);

        int result = 0;
        for(char ch : decryptedMessage) {
            result += ch;
        }
        return result;
    }

    private static char[] encrypt(int[] message, int[] key) {
        char[] result = new char[message.length];
        for(int i = 0; i < message.length; i++) {
            result[i] = (char)(message[i] ^ key[i % key.length]);
        }
        return result;
    }

    private static int[] readEncryptedMessage(String filePath) {
        List<Integer> list = new ArrayList<>(1000);
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                for(String part : line.split(",")) {
                    Integer num = Integer.parseInt(part.trim());
                    list.add(num);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] result = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private static int[] getEncryptionKey(int[] message, int keyLength) {
        int[] key = new int[keyLength];
        int maxSize = 0;
        for (int value : message) {
            if (maxSize < value) {
                maxSize = value;
            }
        }

        int[][] frequencyTab = new int[keyLength][maxSize + 1];

        for(int i = 0; i < message.length; i++) {
            int value = message[i];
            int idx = i % keyLength;
            frequencyTab[idx][value]++;
            if(frequencyTab[idx][value] > frequencyTab[idx][key[idx]]) {
               key[idx] = value;
            }
        }

        int space = ' ';
        for(int i = 0; i < key.length; i++) {
            key[i] = key[i] ^ space;
        }

        return key;
    }

    public static int spriralPrimes(double th) {
        double ratio = 1.0;
        int pC = 0, k = 1;

        while(ratio > th) {
            k++;
            int firstTerm = 4 * k * k;
            for(int i = 0; i < 3; i++) {
                int p = firstTerm - (10 - 2 * i) * k + (7 - 2 * i);
                if(isPrime(p)) {
                    pC++;
                }
            }

            int total = 4 * k - 3;
            ratio = ((double)pC)/total;
        }
        return 2 * k - 1;
    }

    public static int squareRootConvergents() {
        int result = 0;
        Fraction f = new Fraction(BigDecimal.ONE, BigDecimal.ONE);
        for(int i = 1; i <= 1000; i++) {
            f = nextFraction(f);
            if(getDigitsCount(f.numerator) > getDigitsCount(f.denominator)) {
                result++;
            }
        }
        return result;
    }

    private static Fraction nextFraction(Fraction f) {
        BigDecimal numerator = f.numerator.add(f.denominator.multiply(BigDecimal.valueOf(2)));
        BigDecimal denominator = f.numerator.add(f.denominator);
        return new Fraction(numerator, denominator);
    }

    public static int powerfulDigitSum() {
        int result = 0;

        outer:
        for(int i = 99; i >= 1; i--) {
            BigDecimal base = BigDecimal.valueOf(i);
            for(int j = 99; j >= 1; j--) {
                String sNum = base.pow(j).toString();
                if(sNum.length() * 9 < result) {
                    if(j == 99) {
                        break outer;
                    }
                    break;
                }
                int sum = sumOfDigits(sNum);
                if(sum > result) {
                    result = sum;
                }
            }
        }
        return result;
    }

    public static int countLychrelNumbers() {
        final int LIMIT = 10000, ITERATIONS_LIMIT = 50;
        int result = 0;
        for(int num = 1; num < LIMIT; num++) {
            if(isLychrelNumber(num, ITERATIONS_LIMIT)) {
                result++;
            }
        }
        return result;
    }

    private static boolean isLychrelNumber(int n, int iterationsLimit) {
        int i = 1;
        boolean found = false;
        BigDecimal current = BigDecimal.valueOf(n);
        while(i++ < iterationsLimit && !found) {
            BigDecimal sum = current.add(reverse(current));
            found = isPalindrome(sum.toString());
            current = sum;
        }
        return !found;
    }

    public static int combinatoricSelections() {
        final int UPPER = 100;
        final int MAX = 1000000;

        int result = 0;
        for(int n = 1; n <= UPPER; n++) {
            result += getNumberOfCombinationsGreaterThan(n, MAX);
        }
        return result;
    }

    private static int getNumberOfCombinationsGreaterThan(int n, int threshold) {
        if(combinations(n, 1) > threshold) {
            return n;
        }
        int result = 0;
        for(int r = 2; r <= n/2; r++) {
            long combinationsCount = combinations(n ,r);
            if(combinationsCount > threshold) {
                result = n - 2 * r + 1;
                break;
            }
        }
        return result;
    }

    public static int permutedMultiples() {
        Map<Integer, Set<Integer>> digitMap = new HashMap<>();
        Set<Integer> evenDigits = new HashSet<>(Arrays.asList(0, 1, 2, 4, 6, 8));
        digitMap.put(1, new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6)));
        digitMap.put(2, evenDigits);
        digitMap.put(3, new HashSet<>(Arrays.asList(1, 2, 3, 5, 6, 8, 9)));
        digitMap.put(4, evenDigits);
        digitMap.put(5, new HashSet<>(Arrays.asList(0, 1, 5)));
        digitMap.put(6, evenDigits);
        digitMap.put(7, new HashSet<>(Arrays.asList(1, 2, 4, 5, 7, 8)));
        digitMap.put(8, evenDigits);
        digitMap.put(9, new HashSet<>(Arrays.asList(1, 5, 6, 7, 8, 9)));

        boolean notFound = true;
        int start = 10, permutedMultiple = -1;

        while (notFound && start > 0) {
            int upperBound = (start * 10) / 6;
            for(int i = start + 1; i < upperBound; i++) {
                if(isCandidateForPermutedMultiple(i, digitMap) && isPermutedMultiple(i)) {
                    permutedMultiple = i;
                    notFound = false;
                    break;
                }
            }
            start *= 10;
        }

        return permutedMultiple;
    }

    private static boolean isPermutedMultiple(int n) {
        Set<Integer> digits = new HashSet<>(MathUtils.getDigits(n));
        for(int i = 2; i <= 6; i++) {
            Set<Integer> multipliedDigits = new HashSet<>(MathUtils.getDigits(i * n));
            if(!digits.equals(multipliedDigits)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCandidateForPermutedMultiple(int n, Map<Integer, Set<Integer>> digitMap) {
        int lastDigit = n % 10, num = n, zeroes = 0;

        while(lastDigit == 0) {
            num = num / 10;
            zeroes++;
            lastDigit = num % 10;
        }

        String sNum = String.valueOf(n);
        Set<Integer> mandatoryDigits = digitMap.get(lastDigit);
        if(sNum.length() - zeroes < mandatoryDigits.size()) {
            return false;
        }
        return hasDigits(sNum, mandatoryDigits) || !allDigitsOdd(n);
    }

    private static boolean hasDigits(String sNum, Set<Integer> digits) {
        for(Integer digit : digits) {
            if(!sNum.contains(String.valueOf(digit))) {
                return false;
            }
        }
        return true;
    }

    private static boolean allDigitsOdd(int n) {
        while(n > 0) {
            int digit = n % 10;
            if(digit % 2 == 0) {
                return false;
            }
            n = n / 10;
        }
        return true;
    }

    public static long primeDigitReplacement(int familySize) {
        final String LIMIT = "10000000";
        final int FROM_LEN = 5;

        List<String> masks = generateNumberMasks(6, 3);
        List<Long> primes = MathUtils.generatePrimesUpTo(Integer.parseInt(LIMIT));
        Set<Long> primesSet = new HashSet<>(primes);

        Map<Integer, Map<String, List<Long>>> digitReplacementMap = new LinkedHashMap<>();
        for(int len = FROM_LEN; len <= LIMIT.length(); len++) {
            Map<String, List<Long>> maskMap = new LinkedHashMap<>();
            for(String mask : masks) {
                maskMap.put(mask, new ArrayList<>());
            }
            digitReplacementMap.put(len, maskMap);
        }

        long result = -1;

        outer:
        for(Long prime : primes) {
            String sPrime = prime.toString();
            int len = sPrime.length();
            if(len >= FROM_LEN) {
                Map<String, List<Long>> maskMap = digitReplacementMap.get(len);
                for(String mask : masks) {
                    if(matchesMask(sPrime, mask)) {
                        List<Long> consecutivePrimes = getConsecutivePrimes(prime, sPrime, Long.parseLong(mask), primesSet);
                        if(consecutivePrimes.size() == familySize) {
                            maskMap.get(mask).addAll(consecutivePrimes);
                            result = prime;
                            break outer;
                        }
                        break;
                    }
                }
            }
        }

        return result;
    }

    private static List<String> generateNumberMasks(int maxPosition, int combinationLength) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> input = new LinkedHashSet<>();
        for(int i = 0; i <= maxPosition; i++) {
            input.add(i);
        }
        Set<int[]> combinations = CombinatoricsUtils.combinations(input, combinationLength);
        for(int[] combination : combinations) {
            result.add(Integer.parseInt(getNumberFromPositions(combination)));
        }
        Collections.sort(result);
        return result.stream().map(Object::toString).collect(Collectors.toList());
    }

    private static String getNumberFromPositions(int[] positions) {
        char[] tab = new char[8];
        Arrays.fill(tab, '0');
        for(int pos : positions) {
            tab[pos] = '1';
        }
        String s = new String(tab);
        s = s.replaceFirst("^0+", "");
        return s;
    }

    private static boolean matchesMask(String number, String mask) {
        if(mask.length() > number.length()) {
            return false;
        }
        number = number.substring(number.length() - mask.length());
        char c = number.charAt(0);
        if(c != '0' && c != '1' && c != '2') {
            return false;
        }
        for(int i = 0; i < number.length(); i++) {
            if(mask.charAt(i) == '1') {
                if(number.charAt(i) != c) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Long> getConsecutivePrimes(Long number, String sPrime, Long diff, Set<Long> primesSet) {
        List<Long> result = new ArrayList<>();
        for(int i = 0; i <= 9; i++) {
            long num = number + i * diff;
            if(String.valueOf(num).length() != sPrime.length()) {
                break;
            }
            if (primesSet.contains(num)) {
                result.add(num);
            }

        }
        return result;
    }

    public static long maxPathSum2() {
        TriangleFileReader triangleFileReader = new TriangleFileReader(P_067_FILE_PATH);
        int[] nums = triangleFileReader.readTrianglesNum();
        TriangleNode top = new TriangleBuilder().buildFromTable(nums);
        TrianglePathFinder pathFinder = new TrianglePathFinder(top);
        return pathFinder.findBiggestCostPath();
    }

    private static int getMaxPathSum67() {
        TriangleFileReader triangleFileReader = new TriangleFileReader(P_067_FILE_PATH);
        int[][] triangle = triangleFileReader.readTrianglesNum2D();
        List<Integer> path = getMaxPath(triangle);
        return path.stream().reduce(0, Integer::sum);
    }

    private static int getMinPathSum67() {
        TriangleFileReader triangleFileReader = new TriangleFileReader(P_067_FILE_PATH);
        int[][] triangle = triangleFileReader.readTrianglesNum2D();
        return getMinPathSum(triangle);
    }

    private static int getMinPathSum(int[][] triangle) {
        for(int i = triangle.length - 2; i >= 0; i--) {
            for(int j = 0; j < triangle[i].length; j++) {
                int k = triangle[i + 1][j] < triangle[i + 1][j + 1] ? j : j + 1;
                triangle[i][j] += triangle[i + 1][k];
            }
        }
        return triangle[0][0];
    }

    private static List<Integer> getMaxPath(int[][] triangle) {
        List<List<Integer>> paths = new ArrayList<>(triangle.length);
        for(int k = 0; k < triangle[triangle.length - 1].length; k++) {
            List<Integer> initialPath = new ArrayList<>();
            initialPath.add(triangle[triangle.length - 1][k]);
            paths.add(initialPath);
        }
        for(int i = triangle.length - 2; i >= 0; i--) {
            List<List<Integer>> newPaths = new ArrayList<>();
            for(int j = 0; j < triangle[i].length; j++) {
                int k = triangle[i + 1][j] > triangle[i + 1][j + 1] ? j : j + 1;
                List<Integer> path = new ArrayList<>(paths.get(k));
                path.add(triangle[i][j]);
                newPaths.add(path);
                triangle[i][j] += triangle[i + 1][k];
            }
            paths = newPaths;
        }

        return paths.get(0);
    }

    private static class Fraction {
        BigDecimal numerator;
        BigDecimal denominator;

        public Fraction(BigDecimal numerator, BigDecimal denominator) {
            //long bcd = BCD(numerator, denominator);
            this.numerator = numerator;
            this.denominator = denominator;
        }

        @Override
        public String toString() {
            return "Fraction{" +
                    "numerator=" + numerator +
                    ", denominator=" + denominator +
                    '}';
        }
    }
}
