package com.smart.tkl.euler.p98.hk;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AnagramicSquares2 {

    private final long limit;

    private final Map<Long, SquareSet> anagramicMap = new HashMap<>();

    public AnagramicSquares2(long limit) {
        this.limit = limit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        AnagramicSquares2 anagramicSquares = new AnagramicSquares2(4);
        long maxElement = anagramicSquares.findMaxElementOfLargestSet();
        long time2 = System.currentTimeMillis();
        System.out.println("Max element: " + maxElement);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long findMaxElementOfLargestSet() {
        long minBound = (long) Math.pow(10, limit - 1);
        long maxBound = minBound * 10 - 1;
        long minNumber = (long)Math.ceil(Math.sqrt(minBound));
        long maxNumber = (long) Math.sqrt(maxBound);

        List<SquareSet> squareSets = new LinkedList<>();
        int largestSize = 0;
        long maxElement = 0;

        for(long number = minNumber; number <= maxNumber; number++) {
            long square = number * number;
            long rep = getRep(square);
            SquareSet squareSet = anagramicMap.get(rep);
            if(squareSet != null) {
                squareSet.addSquare(square);
            }
            else {
                squareSet = new SquareSet(rep, square);
                anagramicMap.put(rep, squareSet);
            }

            int size = squareSet.size();

            if(size == largestSize) {
                maxElement = Math.max(squareSet.getMax(), maxElement);
            }
            else if(size > largestSize) {
                largestSize = size;
                maxElement = squareSet.getMax();
            }
        }

        return maxElement;
    }

    private static long getRep(long square) {
        int[] freq = new int[10];
        while (square > 0) {
            int digit = (int)(square % 10);
            freq[digit]++;
            square = square / 10;
        }
        long rep = 0;
        for(int digit = 9; digit >= 0; digit--) {
            for(int i = 0; i < freq[digit]; i++) {
                rep = 10 * rep + digit;
            }
        }
        return rep;
    }

    private static class SquareSet {
        long rep;
        long max = 0;
        int size;

        SquareSet(long rep, long square) {
            this.rep = rep;
            this.max = square;
            this.size = 1;
        }

        void addSquare(long square) {
            max = Math.max(square, max);
            size++;
        }

        int size() {
            return size;
        }

        long getMax() {
            return max;
        }

        @Override
        public String toString() {
            return "{" +
                    "rep=" + rep +
                    ", size=" + size +
                    '}';
        }
    }
}
