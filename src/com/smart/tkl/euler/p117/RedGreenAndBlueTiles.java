package com.smart.tkl.euler.p117;

import com.smart.tkl.combinatorics.CombinatoricsUtils;
import com.smart.tkl.combinatorics.permutation.BitMaskPermutationGenerator;
import com.smart.tkl.utils.MathUtils;

import java.math.BigDecimal;
import java.math.MathContext;

public class RedGreenAndBlueTiles {

    private final int length;
    private final BigDecimal[][] memo;
    private final BigDecimal[] factorialMemo;

    public RedGreenAndBlueTiles(int length) {
        this.length = length;
        this.memo = new BigDecimal[length + 1][length + 1];
        this.factorialMemo = new BigDecimal[length + 1];
        for(int i = 0; i <= this.length; i++) {
            factorialMemo[i] = BigDecimal.ZERO;
            for(int j = 0; j <= this.length; j++) {
                memo[i][j] = BigDecimal.ZERO;
            }
        }
    }

    public static void main(String[] args) {
        RedGreenAndBlueTiles redGreenAndBlueTiles = new RedGreenAndBlueTiles(5);
        BigDecimal redTiles = redGreenAndBlueTiles.countWaysForSingleKindOfOblongTile(2);
        BigDecimal greenTiles = redGreenAndBlueTiles.countWaysForSingleKindOfOblongTile(3);
        BigDecimal blueTiles = redGreenAndBlueTiles.countWaysForSingleKindOfOblongTile(4);
        BigDecimal redGreenTiles = redGreenAndBlueTiles.countWaysForTwoKindsOfOblongTiles(2, 3);
        BigDecimal redBlueTiles = redGreenAndBlueTiles.countWaysForTwoKindsOfOblongTiles(2, 4);
        BigDecimal greenBlueTiles = redGreenAndBlueTiles.countWaysForTwoKindsOfOblongTiles(3, 4);

        System.out.println("Red and green tiles ways: " + redGreenTiles);
        System.out.println("Red and blue tiles ways: " + redBlueTiles);
        System.out.println("Green and blue tiles ways: " + greenBlueTiles);

        System.out.println("All tiles: " + redTiles.add(greenTiles).add(blueTiles));
    }

    private BigDecimal countWaysForSingleKindOfOblongTile(int tileLength) {
        BigDecimal result = BigDecimal.ZERO;
        int maxTiles = this.length / tileLength;
        for(int tilesNum = 1; tilesNum <= maxTiles; tilesNum++) {
            int grayTiles = this.length - tilesNum * tileLength;
            int totalTiles = grayTiles + tilesNum;
            result = result.add(CombinatoricsUtils.countCombinations(totalTiles, tilesNum, this.memo));
        }
        return result;
    }

    private BigDecimal countWaysForTwoKindsOfOblongTiles(int tileLength1, int tileLength2) {
        BigDecimal result = BigDecimal.ZERO;
        int maxTilesOfLength1 = (this.length - tileLength2) / tileLength1;
        for(int tilesOfLength1 = 1; tilesOfLength1 <= maxTilesOfLength1; tilesOfLength1++) {
            int maxTilesOfLength2 = (this.length - tilesOfLength1 * tileLength1) / tileLength2;
            for(int tilesOfLength2 = 1; tilesOfLength2 <= maxTilesOfLength2; tilesOfLength2++) {
                int grayTiles = this.length - tilesOfLength1 * tileLength1 - tilesOfLength2 * tileLength2;
                int totalTiles = grayTiles + tileLength1 + tileLength2;
                BigDecimal combinations = CombinatoricsUtils.countCombinations(totalTiles, grayTiles, this.memo);
                BigDecimal permutations = countPermutations(tilesOfLength1, tilesOfLength2);
                result = result.add(combinations.multiply(permutations));
             }
        }
        return result;
    }

    private BigDecimal countWaysForRedGreenAndBlueTiles() {
        BigDecimal result = BigDecimal.ZERO;
        int maxRedTiles = (this.length - 3 - 4) / 2;

        for(int redTiles = 1; redTiles <= maxRedTiles; redTiles++) {
            int maxGreenTiles = (this.length - 2 * redTiles - 4) / 3;
            for(int greenTiles = 1; greenTiles <= maxGreenTiles; greenTiles++) {
                int maxBlueTiles = (this.length - 2 * redTiles - 3 * greenTiles) / 4;
                for(int blueTiles = 1; blueTiles <= maxBlueTiles; blueTiles++) {
                    int grayTiles = this.length - redTiles * 2 - greenTiles * 3 - blueTiles * 4;
                    int totalTiles = grayTiles + redTiles + greenTiles + blueTiles;
                    BigDecimal combinations = CombinatoricsUtils.countCombinations(totalTiles, grayTiles, this.memo);
                    BigDecimal permutations = countPermutations(redTiles, greenTiles, blueTiles);
                    result = result.add(combinations.multiply(permutations));
                }
            }
        }
        return result;
    }

    private BigDecimal countPermutations(int numOfTiles1, int numOfTiles2) {
        BigDecimal numerator = factorial(numOfTiles1 + numOfTiles2);
        BigDecimal denominator1 = factorial(numOfTiles1);
        BigDecimal denominator2  = factorial(numOfTiles2);

        return numerator.
                divide(denominator1, MathContext.DECIMAL32).
                divide(denominator2, MathContext.DECIMAL32);
    }

    private BigDecimal countPermutations(int numOfTiles1, int numOfTiles2, int numOfTiles3) {
        BigDecimal numerator = factorial(numOfTiles1 + numOfTiles2 + numOfTiles3);
        BigDecimal denominator1 = factorial(numOfTiles1);
        BigDecimal denominator2  = factorial(numOfTiles2);
        BigDecimal denominator3  = factorial(numOfTiles3);

        return numerator.
                divide(denominator1, MathContext.DECIMAL32).
                divide(denominator2, MathContext.DECIMAL32).
                divide(denominator3, MathContext.DECIMAL32);
    }

    private BigDecimal factorial(int number) {
        if(!factorialMemo[number].equals(BigDecimal.ZERO)) {
           return factorialMemo[number];
        }
        BigDecimal result = BigDecimal.ONE;
        int n = 2;
        while(n <= number) {
            result = result.multiply(BigDecimal.valueOf(n));
            factorialMemo[n] = result;
            n++;
        }
        return result;
    }

}
