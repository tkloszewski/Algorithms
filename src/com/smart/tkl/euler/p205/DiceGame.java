package com.smart.tkl.euler.p205;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiceGame {

    private final int peterDices;
    private final int peterSides;
    private final int colinDices;
    private final int colinSides;
    private final int scale;

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        DiceGame diceGame = new DiceGame(9, 4, 6, 6, 7);
        double p = diceGame.countProbability();
        long time2 = System.currentTimeMillis();
        System.out.printf("Probability: %.7f\n", p);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public DiceGame(int pyramidDices, int pyramidSides, int cubeDices, int cubeSides, int scale) {
        this.peterDices = pyramidDices;
        this.peterSides = pyramidSides;
        this.colinDices = cubeDices;
        this.colinSides = cubeSides;
        this.scale = scale;
    }

    public double countProbability() {
        int[] colinFaces = new int[this.colinSides];
        for(int i = 0; i < this.colinSides; i++) {
            colinFaces[i] = i + 1;
        }

        int[] peterFaces = new int[this.peterSides];
        for(int i = 0; i < this.peterSides; i++) {
            peterFaces[i] = i + 1;
        }

        long[] colinScores = countScores(colinFaces, this.colinDices);
        long[] peterScores = countScores(peterFaces, this.peterDices);

        long totalColin = (long)Math.pow(this.colinSides, this.colinDices);
        long totalPeter = (long)Math.pow(this.peterSides, this.peterDices);

        long[] cumulativeColinScores = new long[colinScores.length];
        for(int i = this.colinDices; i <= this.colinDices * this.colinSides; i++) {
            cumulativeColinScores[i] = colinScores[i] + cumulativeColinScores[i - 1];
        }

        int maxPeterScore = this.peterDices * this.peterSides;
        int maxColinScore = this.colinDices * this.colinSides;

        long numerator = 0;
        for(int score = this.peterDices; score <= maxPeterScore; score++) {
            int colinScore = Math.min(score - 1, maxColinScore);
            numerator += peterScores[score] * cumulativeColinScores[colinScore];
        }

        BigDecimal bd = new BigDecimal(Double.toString((double) numerator / (totalColin * totalPeter)));
        bd = bd.setScale(this.scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    private static long[] countScores(int[] faces, int n) {
        int highestNum = faces[faces.length - 1];
        long[] ways = new long[n * highestNum + 1];
        fillWays(ways, faces, n, 0);
        return ways;
    }

    private static void fillWays(long[] ways, int[] faces, int left, int sum) {
        if(left == 1) {
           for(int face : faces) {
               ways[sum + face]++;
           }
        }
        else {
            for(int face : faces) {
                fillWays(ways, faces,left - 1, sum + face);
            }
        }
    }

}
