package com.smart.tkl.euler;

import com.smart.tkl.lib.combinatorics.permutation.SwapPermutationGenerator;
import com.smart.tkl.lib.combinatorics.permutation.PermutationListener;
import com.smart.tkl.lib.graph.triangle.TriangleBuilder;
import com.smart.tkl.lib.graph.triangle.TriangleNode;
import com.smart.tkl.lib.graph.triangle.TrianglePathFinder;
import com.smart.tkl.lib.utils.GenericUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.smart.tkl.lib.utils.MathUtils.*;

public class ProblemSet2 {

    private static final int[][] GRID_20_20 = {
            {8,  2,  22, 97, 38, 15, 0,  40, 0,  75, 4,  5,  7,  78, 52, 12, 50, 77, 91, 8},
            {49, 49, 99, 40, 17, 81, 18, 57, 60, 87, 17, 40, 98, 43, 69, 48, 4,  56, 62, 0},
            {81, 49, 31, 73, 55, 79, 14, 29, 93, 71, 40, 67, 53, 88, 30, 3,  49, 13, 36, 65},
            {52, 70, 95, 23, 4,  60, 11, 42, 69, 24, 68, 56, 1,  32, 56, 71, 37, 2,  36, 91},
            {22, 31, 16, 71, 51, 67, 63, 89, 41, 92, 36, 54, 22, 40, 40, 28, 66, 33, 13, 80},
            {24, 47, 32, 60, 99, 3,  45, 2,  44, 75, 33, 53, 78, 36, 84, 20, 35, 17, 12, 50},
            {32, 98, 81, 28, 64, 23, 67, 10, 26, 38, 40, 67, 59, 54, 70, 66, 18, 38, 64, 70},
            {67, 26, 20, 68, 2,  62, 12, 20, 95, 63, 94, 39, 63, 8,  40, 91, 66, 49, 94, 21},
            {24, 55, 58, 5,  66, 73, 99, 26, 97, 17, 78, 78, 96, 83, 14, 88, 34, 89, 63, 72},
            {21, 36, 23, 9,  75,  0, 76, 44, 20, 45, 35, 14, 0,  61, 33, 97, 34, 31, 33, 95},
            {78, 17, 53, 28, 22, 75, 31, 67, 15, 94,  3, 80, 4,  62, 16, 14, 9,  53, 56, 92},
            {16, 39, 5,  42, 96, 35, 31, 47, 55, 58, 88, 24, 0,  17, 54, 24, 36, 29, 85, 57},
            {86, 56, 0,  48, 35, 71, 89, 7,  5,  44, 44, 37, 44, 60, 21, 58, 51, 54, 17, 58},
            {19, 80, 81, 68, 5,  94, 47, 69, 28, 73, 92, 13, 86, 52, 17, 77, 4,  89, 55, 40},
            {4,  52, 8,  83, 97, 35, 99, 16, 7,  97, 57, 32, 16, 26, 26, 79, 33, 27, 98, 66},
            {88, 36, 68, 87, 57, 62, 20, 72, 3,  46, 33, 67, 46, 55, 12, 32, 63, 93, 53, 69},
            {4,  42, 16, 73, 38, 25, 39, 11, 24, 94, 72, 18, 8 , 46, 29, 32, 40, 62, 76, 36},
            {20, 69, 36, 41, 72, 30, 23, 88, 34, 62, 99, 69, 82, 67, 59, 85, 74, 4,  36, 16},
            {20, 73, 35, 29, 78, 31, 90, 1,  74, 31, 49, 71, 48, 86, 81, 16, 23, 57, 5,  54},
            {1,  70, 54, 71, 83, 51, 54, 69, 16, 92, 33, 48, 61, 43, 52, 1,  89, 19, 67, 48},
    };

    private static final Map<Integer, String> DIGIT_TO_NAMES = new HashMap<Integer, String>() {{
        put(1, "one"); put(2, "two"); put(3, "three"); put(4, "four");  put(5, "five");
        put(6, "six"); put(7, "seven"); put(8, "eight"); put(9, "nine"); put(10, "ten");
        put(11, "eleven"); put(12, "twelve"); put(13, "thirteen"); put(14, "fourteen");
        put(15, "fifteen"); put(16, "sixteen"); put(17, "seventeen"); put(18, "eighteen");
        put(19, "nineteen"); put(20, "twenty"); put(30, "thirty"); put(40, "forty");
        put(50, "fifty"); put(60, "sixty"); put(70, "seventy"); put(80, "eighty");
        put(90, "ninety"); put(100, "hundred"); put(1000, "thousand");
    }};

    private static final String VERY_LARGE_NUMBERS_FILE_PATH = "C:\\Projects\\microservices\\Algorithms\\src\\com\\smart\\tkl\\euler\\oneHundred50DigitNumbers.txt";


    public static void main(String[] args) {
        int[] millionthPermutation = lexicographicPermutation(1000000);
        System.out.println("1 million-th permutation: ");
        GenericUtils.printTable(millionthPermutation);

        System.out.println("Largest product in grid: " + largestProductInGrid(GRID_20_20, 4));
        System.out.println("First triangular number: " + getFirstTriangularNumber(500));
        System.out.println("Very large sum: " + first10DigitsOfVeryLargeSum());
        System.out.println("longestCollatzSequence: " + longestCollatzSequence(1000000));
        System.out.println("3 four digit prime permutation in arithmetic order: " + fourDigitPrimePermutations());
        System.out.println("Lattice paths: " + latticePaths(20, 20));
        System.out.println("Power sum of 2: " + powerSumOf2(4));
        System.out.println("numberLetterCounts: " + numberLetterCounts());
        System.out.println("maximumPathSum1: " + maximumPathSum1());
        System.out.println("sundays: " + countSundays());
        System.out.println("factorialSum: " + factorialSum(100));
    }

    public static int factorialSum(int n) {
        List<Integer> factorial = getDigits(1);
        for(int i = 2; i <= n; i++) {
            factorial = writtenMultiplication(factorial, i);
        }
        System.out.println(factorial);
        return factorial.stream().reduce(0, Integer::sum);
    }

    public static int countSundays() {
        return countWeekdayOnFirstDayOfMonth(6);
    }

    //SUN=6,MON=0,TUE=1
    public static int countWeekdayOnFirstDayOfMonth(int weekday) {
        int[] weekdayOffsets = new int[]{0, 1, 2, 3, 4, 5, 6};
        int offset = weekdayOffsets[weekday];
        int[] months = new int[] {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int weekDaysCount = 0;
        int days = 0;
        for(int year = 1901; year <= 2000; year++) {
            if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                months[1] = 29;
            }
            else {
                months[1] = 28;
            }
            for(int i = 0; i <= 11; i++) {
                days += months[i];
                if(year != 2000 || i != 11) {
                   if((days + 1) % 7 == offset) {
                       weekDaysCount++;
                   }
                }
            }
        }
        return weekDaysCount;

    }

    public static long maximumPathSum1() {
        //int[] nums = new int[]{3, 7, 4, 2, 4, 6, 8, 5, 9, 3};

        int[] nums = new int[]{75,
                95, 64,
                17, 47, 82,
                18, 35, 87, 10,
                20, 4,  82, 47, 65,
                19, 1,  23, 75, 3, 34,
                88, 2,  77, 73, 7, 63, 67,
                99, 65, 4,  28, 6 ,16, 70, 92,
                41, 41, 26, 56, 83, 40, 80, 70, 33,
                41, 48, 72, 33, 47, 32, 37, 16, 94, 29,
                53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14,
                70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57,
                91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48,
                63, 66,  4, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31,
                 4, 62, 98, 27, 23,  9, 70, 98, 73, 93, 38, 53, 60,  4, 23};

        TriangleNode top = new TriangleBuilder().buildFromTable(nums);
        TrianglePathFinder pathFinder = new TrianglePathFinder(top);
        long cost = pathFinder.findBiggestCostPath();
        return cost;
    }

    public static int numberLetterCounts() {
        int count = 0;
        for(int i = 1; i <= 1000; i++) {
            String digitWord = numberToWord(i);
            count += digitWord.length();
        }
        return count;
    }

    public static String numberToWord(int n) {
        if(n > 1000) {
            return "";
        }
        if(n == 1000) {
            return "onethousand";
        }
        StringBuilder result = new StringBuilder();
        while(n > 0) {
            if(n <= 20) {
                result.append(DIGIT_TO_NAMES.get(n));
                break;
            }
            else if(n < 100) {
                int tensKey = n - (n % 10);
                result.append(DIGIT_TO_NAMES.get(tensKey));
                n = n % 10;
            }
            else {
                String hundredDigit = DIGIT_TO_NAMES.get(n/100);
                result.append(hundredDigit).append(DIGIT_TO_NAMES.get(100));
                if(n % 100 != 0) {
                   result.append("and");
                }
                n = n - (n / 100) * 100;
            }
        }
        return result.toString();
    }


    public static int powerSumOf2(int power) {
        int size = (int)Math.ceil(power * Math.log10(2));
        int[] digits = new int[size];
        digits[0] = 1;

        int digitPos = 0;
        for(int k = 1; k <= power; k++) {
            int rest = 0;
            for (int i = 0; i <= digitPos; i++) {
                int m1 = digits[i] * 2 + rest;
                digits[i] = m1 % 10;
                rest = m1/10;
            }
            if(rest > 0) {
                digits[++digitPos] = rest;
            }
        }

        return Arrays.stream(digits).sum();
    }

    public static long latticePaths(int x, int y, int n, Map<Point, Long> pathsMap) {
        Point key = new Point(x, y);
        if(pathsMap.containsKey(key)){
            return pathsMap.get(key);
        }
        if(x == n - 1 && y == n - 1) {
            return 2;
        }
        if(x == n || y == n) {
            return 1;
        }
        long paths = latticePaths(x + 1, y, n, pathsMap) + latticePaths(x, y + 1, n, pathsMap);
        pathsMap.put(key, paths);
        return paths;
    }

    public static long latticePaths(int m, int n) {
        long[][] grid = new long[m + 1][n + 1];
        for(int i = 0; i <= n -1 ;i++) {
            grid[m][i] = 1;
        }
        for(int i = 0; i <= m - 1; i++) {
            grid[i][n] = 1;
        }

        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                grid[i][j] = grid[i + 1][j] + grid[i][j + 1];
            }
        }
        return grid[0][0];
    }

    public static List<List<Integer>> fourDigitPrimePermutations() {
        List<Integer> primes = new ArrayList<>();
        for(int i = 1000; i <= 9999;i ++) {
            if(isPrime(i)) {
                primes.add(i);
            }
        }

        //filter 3 consecutive primes forming arithmetic sequence
        List<List<Integer>> sameDigitPrimesSet = new ArrayList<>();
        for(int i = 0; i < primes.size(); i++) {
            int prime1 = primes.get(i);
            List<List<Integer>> permutations = generatePermutations(prime1);
            for(int j = i + 1; j < primes.size(); j++) {
                int prime2 = primes.get(j);
                if(permutations.contains(getDigits(prime2))) {
                    for(int k = j + 1; k < primes.size(); k++) {
                        int prime3 = primes.get(k);
                        if(permutations.contains(getDigits(prime3))) {
                            if(prime2 - prime1 == prime3 - prime2) {
                                List<Integer> set = new ArrayList<>(3);
                                set.add(prime1);
                                set.add(prime2);
                                set.add(prime3);
                                sameDigitPrimesSet.add(set);
                            }
                        }
                    }
                }
            }
        }

        return sameDigitPrimesSet;
    }

    public static int[] lexicographicPermutation(int n) {
        int[] tab = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        PermutationCatcher permutationCatcher = new PermutationCatcher(n);
        new SwapPermutationGenerator(permutationCatcher).generateSorted(tab);
        return permutationCatcher.getCaughtPermutation();
    }

    public static long longestCollatzSequence(long n) {
        Map<Long, Integer> collatzMap = new HashMap<>((int)(2 * n));
        collatzMap.put(1L, 1);
        collatzMap.put(2L, 2);

        int longestSequence = 2;
        long longestSeqTerm = 2;

        List<Long> sequenceTerms = new ArrayList<>(2000);

        for(long i = 3; i < n; i++) {
            long term = i;
            int sequenceLen = 1;

            if(!collatzMap.containsKey(i)) {
                while(term != 1) {
                    int inc = 1;
                    if (term % 2 == 0) {
                        term = term / 2;
                    } else {
                        inc = 2;
                        sequenceTerms.add(term * 3 + 1);
                        term = (term * 3 + 1)/2;
                    }
                    if (collatzMap.containsKey(term)) {
                        sequenceLen = collatzMap.get(term) + sequenceLen + (inc - 1);
                        collatzMap.put(i, sequenceLen);
                        for(int j = 1; j <= sequenceTerms.size(); j++) {
                            collatzMap.put(sequenceTerms.get(j-1), sequenceLen - j);
                        }
                        sequenceTerms.clear();
                        break;
                    } else {
                        sequenceLen += inc;
                        sequenceTerms.add(term);
                    }
                }
            }
            else {
                sequenceLen = collatzMap.get(term);
            }
            if(longestSequence < sequenceLen) {
                longestSeqTerm = i;
                longestSequence = sequenceLen;
            }
        }

        return longestSeqTerm;
    }

    public static String first10DigitsOfVeryLargeSum() {
        BigDecimal sum = BigDecimal.ZERO;

        try(FileReader fr = new FileReader(VERY_LARGE_NUMBERS_FILE_PATH)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                sum = sum.add(BigDecimal.valueOf(Double.parseDouble(line)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sum.toString().substring(0, 10);
    }

    public static int largestProductInGrid(int[][] grid, int cellsCount) {
        if(cellsCount > grid.length) {
            return 0;
        }
        int len = grid.length;
        for (int[] ints : grid) {
            if (ints.length != len) {
                return 0;
            }
        }

        List<Integer> maxRowProductFactors = new ArrayList<>();
        int largestRowProduct = 0;
        //check all rows
        for (int[] ints : grid) {
            for (int j = 0; j <= len - cellsCount; j++) {
                int rowProduct = 1;
                List<Integer> maxProductFactors = new ArrayList<>();
                for (int k = 0; k < cellsCount; k++) {
                    rowProduct *= ints[j + k];
                    maxProductFactors.add(ints[j+ k]);
                }
                if (rowProduct > largestRowProduct) {
                    largestRowProduct = rowProduct;
                    maxRowProductFactors = maxProductFactors;
                }
            }
        }

        List<Integer> maxColumnProductFactors = new ArrayList<>();
        //check all columns
        int largestColumnProduct = 0;
        for(int i = 0; i < len; i++) {
            for(int j = 0; j <= len - cellsCount; j++) {
                int columnProduct = 1;
                List<Integer> maxProductFactors = new ArrayList<>();
                for(int k = 0; k < cellsCount; k++) {
                    columnProduct *= grid[j + k][i];
                    maxProductFactors.add(grid[j + k][i]);
                }
                if(columnProduct > largestColumnProduct) {
                    largestColumnProduct = columnProduct;
                    maxColumnProductFactors = maxProductFactors;
                }
            }
        }

        List<Integer> maxDiagonalProductFactors1 = new ArrayList<>();
        int largestDiagonalProductUpRight1 = 0;
        for(int start = cellsCount - 1; start < len; start++) {
            for(int offset = 0; offset <= start - (cellsCount - 1); offset++) {
                int diagonalProduct = 1;
                List<Integer> maxProductFactors = new ArrayList<>();
                for(int k = 0; k < cellsCount; k++) {
                    int rowIdx = start - offset - k;
                    int colIdx = offset + k;
                    diagonalProduct *= grid[rowIdx][colIdx];
                    maxProductFactors.add(grid[rowIdx][colIdx]);
                }
                if(diagonalProduct > largestDiagonalProductUpRight1) {
                    largestDiagonalProductUpRight1 = diagonalProduct;
                    maxDiagonalProductFactors1 = maxProductFactors;
                }
            }
        }

        List<Integer> maxDiagonalProductFactors2 = new ArrayList<>();
        int largestDiagonalProductUpRight2 = 0;
        for(int start = 1; start <= len - cellsCount; start++) {
            for(int offset = 0; offset <= len - start - cellsCount; offset++) {
                int diagonalProduct = 1;
                List<Integer> maxProductFactors = new ArrayList<>();
                for(int k = 0; k < cellsCount; k++) {
                    int rowIdx = len - 1 - offset - k;
                    int colIdx = start + offset + k;
                    diagonalProduct *= grid[rowIdx][colIdx];
                    maxProductFactors.add(grid[rowIdx][colIdx]);
                }
                if(diagonalProduct > largestDiagonalProductUpRight2) {
                    largestDiagonalProductUpRight2 = diagonalProduct;
                    maxDiagonalProductFactors2 = maxProductFactors;
                }
            }
        }

        List<Integer> maxDiagonalProductFactors3 = new ArrayList<>();
        int largestDiagonalProductDownRight1 = 0;
        for(int start = 0; start <= len - cellsCount; start++) {
            for(int offset = 0; offset <= len - start - cellsCount; offset++) {
                int diagonalProduct = 1;
                List<Integer> maxProductFactors = new ArrayList<>();
                for(int k = 0; k < cellsCount; k++) {
                    int rowIdx = offset + k;
                    int colIdx = start + offset + k;
                    diagonalProduct *= grid[rowIdx][colIdx];
                    maxProductFactors.add(grid[rowIdx][colIdx]);
                }
                if(diagonalProduct > largestDiagonalProductDownRight1) {
                    largestDiagonalProductDownRight1 = diagonalProduct;
                    maxDiagonalProductFactors3 = maxProductFactors;
                }
            }
        }

        List<Integer> maxDiagonalProductFactors4 = new ArrayList<>();
        int largestDiagonalProductDownRight2 = 0;
        for(int start = 1; start <= len - cellsCount; start++) {
            for(int offset = 0; offset <= len - start - cellsCount; offset++) {
                int diagonalProduct = 1;
                List<Integer> maxProductFactors = new ArrayList<>();
                for(int k = 0; k < cellsCount; k++) {
                    int rowIdx = start + offset + k;
                    int colIdx = offset + k;
                    diagonalProduct *= grid[rowIdx][colIdx];
                    maxProductFactors.add(grid[rowIdx][colIdx]);
                }
                if(diagonalProduct > largestDiagonalProductDownRight2) {
                    largestDiagonalProductDownRight2 = diagonalProduct;
                    maxDiagonalProductFactors4 = maxProductFactors;
                }
            }
        }

        int maxRowColumn = Math.max(largestRowProduct, largestColumnProduct);
        int maxDiagonal1 = Math.max(largestDiagonalProductUpRight1, largestDiagonalProductUpRight2);
        int maxDiagonal2 = Math.max(largestDiagonalProductDownRight1, largestDiagonalProductDownRight2);
        int maxDiagonal = Math.max(maxDiagonal1, maxDiagonal2);

        List<Integer> maxRowColumnProductsFactor = largestRowProduct > largestColumnProduct ?
                maxRowProductFactors : maxColumnProductFactors;

        List<Integer> maxDiagonalFactor1 = largestDiagonalProductUpRight1 > largestDiagonalProductUpRight2 ?
                maxDiagonalProductFactors1 : maxDiagonalProductFactors2;

        List<Integer> maxDiagonalFactor2 = largestDiagonalProductDownRight1 > largestDiagonalProductDownRight2 ?
                maxDiagonalProductFactors3 : maxDiagonalProductFactors4;

        List<Integer> maxDiagonalFactor = maxDiagonal1 > maxDiagonal2 ? maxDiagonalFactor1 : maxDiagonalFactor2;
        List<Integer> maxFactor = maxRowColumn > maxDiagonal ? maxRowColumnProductsFactor : maxDiagonalFactor;

        System.out.println("Max factor: " + maxFactor);

        return Math.max(maxRowColumn, maxDiagonal);
    }

    public static int getFirstTriangularNumber(int numberOfDivisors) {
        int result = 1;
        int divisorsCount = 1;
        int n = 1;
        while(divisorsCount <= numberOfDivisors) {
            int firstFactor = n % 2 == 0 ? n/2 : n;
            int secondFactor = (n+1) % 2 == 0 ? (n+1)/2 : n+1;
            result = firstFactor * secondFactor;
            divisorsCount = countDivisors(firstFactor) * countDivisors(secondFactor);
            n++;
        }
        return result;
    }

    public static int countDivisors(int n) {
        int count = n == 1 ? 1 : 2;
        if(n != 1 && isPrime(n)) {
            return 2;
        }

        int end = n;
        for(int i = 2; i < end; i++) {
            if(n % i == 0) {
                if(i * i == n) {
                    count++;
                }
                else {
                    count += 2;
                    end = n/i;
                }
            }
        }

        return count;
    }

    private static class PermutationCatcher implements PermutationListener {
        private final int num;
        private int[] caughtPermutation;
        private int count = 0;

        public PermutationCatcher(int num) {
            this.num = num;
        }

        @Override
        public void permutation(int[] permutation) {
            if(++count == num) {
                caughtPermutation = permutation.clone();
            }
        }

        public int[] getCaughtPermutation() {
            return caughtPermutation;
        }
    }

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
