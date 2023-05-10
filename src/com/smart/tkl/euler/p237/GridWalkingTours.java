package com.smart.tkl.euler.p237;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GridWalkingTours {

    private final static Set<String> states = Stream.of(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G"
    ).collect(Collectors.toCollection(LinkedHashSet::new));

    private final static Set<String> allowedTransitions = Set.of(
            "AA",
            "BB",
            "CC",
            "DA",
            "BD",
            "CD",
            "AE",
            "AF",
            "EB",
            "FC",
            "DG",
            "GD",
            "ED",
            "DF",
            "DE",
            "FD",
            "AH",
            "DH"
    );

    private final long n;
    private final long modulo;
    private final Map<String, Long> memo = new HashMap<>();

    public GridWalkingTours(long n, long modulo) {
        this.n = n;
        this.modulo = modulo;
    }

    public static void main(String[] args) {
        long n = 1000000000000L;
        long mod = (long)Math.pow(10, 8);
        GridWalkingTours gridWalkingTours = new GridWalkingTours(n, mod);
        long time1 = System.currentTimeMillis();
        long fastCount = gridWalkingTours.countTours();
        long time2 = System.currentTimeMillis();

        System.out.println("Count: " + fastCount);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long countTours() {
      return doCountTours("D", "H", this.n);
    }

    private long doCountTours(String in, String out, long width) {
        String transition = in + out;
        if(width == 1) {
           boolean isTransitionAllowed =  allowedTransitions.contains(transition);
           return isTransitionAllowed ? 1 : 0;
        }
        String key = transition + width;
        if(memo.containsKey(key)) {
           return memo.get(key);
        }

        long leftWidth = width / 2;
        long rightWidth = width - leftWidth;

        long result = 0;
        for(String state : states) {
            long leftCount = doCountTours(in, state, leftWidth);
            if(leftCount != 0) {
                long rightCount = doCountTours(state, out, rightWidth);
                long product = (leftCount * rightCount) % this.modulo;
                result += product;
                result = result % this.modulo;
            }
        }

        this.memo.put(key, result);

        return result;
    }

    public long bruteForceCount() {
        return bruteForce(0, 0, 0, new int[(int)this.n][4]);
    }

    public long bruteForce(int x, int y, int visited, int[][] grid) {
        long result = 0;
        if(x == 0 && y == 3) {
            if(visited == 4 * this.n - 1) {
                return 1;
            }
            else {
                return 0;
            }
        }

        grid[x][y] = 1;

        if(x > 0 && grid[x - 1][y] == 0) {
            result += bruteForce(x - 1, y, visited + 1, grid);
        }
        if(x < this.n - 1 && grid[x + 1][y] == 0) {
            result += bruteForce(x + 1, y, visited + 1, grid);
        }
        if(y > 0 && grid[x][y - 1] == 0) {
            result += bruteForce(x, y - 1, visited + 1, grid);
        }
        if(y < 3 && grid[x][y + 1] == 0) {
            result += bruteForce(x, y + 1, visited + 1, grid);
        }

        grid[x][y] = 0;

        return result;
    }

}
