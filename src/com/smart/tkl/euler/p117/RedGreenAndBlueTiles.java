package com.smart.tkl.euler.p117;

import java.math.BigDecimal;
import java.math.MathContext;

public class RedGreenAndBlueTiles {

    private final int length;

    private long[][] redSolutions;
    private long[][] greenSolutions;
    private long[][] blueSolutions;
    private long[][][] redGreenSolutions;
    private long[][][] redBlueSolutions;
    private long[][][] greenBlueSolutions;
    private long[][][][] redGreenBlueSolutions;

    private final BigDecimal[] factorialMemo;
    private BigDecimal[] recursiveSolutions;

    public RedGreenAndBlueTiles(int length) {
        this.length = length;
        this.factorialMemo = new BigDecimal[length + 1];
        for(int i = 0; i <= this.length; i++) {
            factorialMemo[i] = BigDecimal.ZERO;
        }
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        RedGreenAndBlueTiles redGreenAndBlueTiles = new RedGreenAndBlueTiles(100);
        long totalCount = redGreenAndBlueTiles.countAllWays();
        long time2 = System.currentTimeMillis();
        System.out.println("Total count: " + totalCount);
        System.out.println("Solution took: " + (time2 - time1));
        time1 = System.currentTimeMillis();
        BigDecimal totalCountBg = redGreenAndBlueTiles.countRecursive();
        time2 = System.currentTimeMillis();
        System.out.println("Recursive total count: " + totalCountBg);
        System.out.println("Recursive solution took: " + (time2 - time1));
    }

    public BigDecimal countRecursive() {
        this.recursiveSolutions = new BigDecimal[this.length + 1];
        for(int i = 1; i <= this.length; i++) {
            this.recursiveSolutions[i] = BigDecimal.ZERO;
        }
        return countRecursive(this.length);
    }

    private BigDecimal countRecursive(int length) {
        if(length == 0) {
           return BigDecimal.ONE;
        }
        if(!recursiveSolutions[length].equals(BigDecimal.ZERO)) {
           return recursiveSolutions[length];
        }
        BigDecimal result = BigDecimal.ZERO;
        for(int tileLength = 1; tileLength <= 4 && tileLength <= length; tileLength++) {
            result = result.add(countRecursive(length - tileLength));
        }
        recursiveSolutions[length] = result;
        return result;
    }

    public long countAllWays() {
        long result = 0;

        int redMaxTiles = this.length / 2;
        int greenMaxTiles = this.length / 3;
        int blueMaxTiles = this.length / 4;

        this.redSolutions = countWaysForSingleKindOfOblongTile(2, redMaxTiles);
        this.greenSolutions = countWaysForSingleKindOfOblongTile(3, greenMaxTiles);
        this.blueSolutions = countWaysForSingleKindOfOblongTile(4, blueMaxTiles);

        for(int redTiles = 1; redTiles <= redMaxTiles; redTiles++) {
            result += this.redSolutions[redTiles][this.length];
        }
        for(int greenTiles = 1; greenTiles <= greenMaxTiles; greenTiles++) {
            result += this.greenSolutions[greenTiles][this.length];
        }
        for(int blueTiles = 1; blueTiles <= blueMaxTiles; blueTiles++) {
            result += this.blueSolutions[blueTiles][this.length];
        }

        this.redGreenSolutions = countWaysForRedGreenTiles(redMaxTiles, greenMaxTiles);
        this.redBlueSolutions = countWaysForRedBlueTiles(redMaxTiles, blueMaxTiles);
        this.greenBlueSolutions = countWaysForGreenBlueTiles(greenMaxTiles, blueMaxTiles);
        this.redGreenBlueSolutions = countWaysForRedGreenBlueTiles(redMaxTiles, greenMaxTiles, blueMaxTiles);

        long redGreenCount = 0;
        for(int redTiles = 1; redTiles <= redMaxTiles; redTiles++) {
            for(int greenTiles = 1; greenTiles <= greenMaxTiles; greenTiles++) {
                redGreenCount += this.redGreenSolutions[redTiles][greenTiles][this.length];
            }
        }

        long redBlueCount = 0;
        for(int redTiles = 1; redTiles <= redMaxTiles; redTiles++) {
            for(int blueTiles = 1; blueTiles <= blueMaxTiles; blueTiles++) {
                redBlueCount += this.redBlueSolutions[redTiles][blueTiles][this.length];
            }
        }

        long greenBlueCount = 0;
        for(int greenTiles = 1; greenTiles <= greenMaxTiles; greenTiles++) {
            for(int blueTiles = 1; blueTiles <= blueMaxTiles; blueTiles++) {
                greenBlueCount += this.greenBlueSolutions[greenTiles][blueTiles][this.length];
            }
        }

        long redGreenBlueCount = 0;
        for(int redTiles = 1; redTiles <= redMaxTiles; redTiles++) {
            for (int greenTiles = 1; greenTiles <= greenMaxTiles; greenTiles++) {
                for (int blueTiles = 1; blueTiles <= blueMaxTiles; blueTiles++) {
                    redGreenBlueCount += this.redGreenBlueSolutions[redTiles][greenTiles][blueTiles][this.length];
                }
            }
        }

        /*System.out.println("------------------");
        System.out.println("Single kind tiles count: " + result);
        System.out.println("Red green tiles count: " + redGreenCount);
        System.out.println("Red blue tiles count: " + redBlueCount);
        System.out.println("Green blue tiles count: " + greenBlueCount);
        System.out.println("Red green blue tiles count: " + redGreenBlueCount);*/

        result += redGreenCount;
        result += redBlueCount;
        result += greenBlueCount;
        result += redGreenBlueCount;

        result++;

        /*System.out.println("Result = " + result);*/

        return result;
    }

    private long[][] countWaysForSingleKindOfOblongTile(int tileLength, int maxTiles) {
        long[][] result = new long[maxTiles + 1][this.length + 1];
        for(int pos = tileLength; pos <= this.length; pos++) {
            result[1][pos] = pos - tileLength + 1;
        }

        for(int tilesNum = 2; tilesNum <= maxTiles; tilesNum++) {
            for(int pos = tilesNum * tileLength; pos <= this.length; pos++) {
                result[tilesNum][pos] = result[tilesNum][pos - 1] + result[tilesNum - 1][pos - tileLength];
            }
        }

        return result;
    }

    private long[][][] countWaysForRedGreenTiles(int redMaxTiles, int greenMaxTiles) {
        long[][][] solutions = new long[redMaxTiles + 1][greenMaxTiles + 1][this.length + 1];
        for(int redTiles = 1; redTiles <= redMaxTiles; redTiles++) {
            for(int greenTiles = 1; greenTiles <= greenMaxTiles; greenTiles++) {
                for(int pos = redTiles * 2 + greenTiles * 3; pos <= this.length; pos++) {
                    if(pos == redTiles * 2 + greenTiles * 3) {
                        solutions[redTiles][greenTiles][pos] = countPermutations(redTiles, greenTiles).longValue();
                    }
                    else {
                        solutions[redTiles][greenTiles][pos] = solutions[redTiles][greenTiles][pos - 1];

                        //count one less red tile
                        if(redTiles - 1 == 0) {
                            solutions[redTiles][greenTiles][pos] += this.greenSolutions[greenTiles][pos - 2];
                        }
                        else {
                            solutions[redTiles][greenTiles][pos] += solutions[redTiles - 1][greenTiles][pos - 2];
                        }

                        //count one less green tile
                        if(greenTiles - 1 == 0) {
                            solutions[redTiles][greenTiles][pos] += this.redSolutions[redTiles][pos - 3];
                        }
                        else {
                            solutions[redTiles][greenTiles][pos] += solutions[redTiles][greenTiles - 1][pos - 3];
                        }
                    }
                }
            }
        }
        return solutions;
    }

    private long[][][] countWaysForRedBlueTiles(int redMaxTiles, int blueMaxTiles) {
        long[][][] solutions = new long[redMaxTiles + 1][blueMaxTiles + 1][this.length + 1];
        for(int redTiles = 1; redTiles <= redMaxTiles; redTiles++) {
            for(int blueTiles = 1; blueTiles <= blueMaxTiles; blueTiles++) {
                for(int pos = redTiles * 2 + blueTiles * 4; pos <= this.length; pos++) {
                    if(pos == redTiles * 2 + blueTiles * 4) {
                        solutions[redTiles][blueTiles][pos] = countPermutations(redTiles, blueTiles).longValue();
                    }
                    else {
                        solutions[redTiles][blueTiles][pos] = solutions[redTiles][blueTiles][pos - 1];

                        //count one less red tile
                        if(redTiles - 1 == 0) {
                            solutions[redTiles][blueTiles][pos] += this.blueSolutions[blueTiles][pos - 2];
                        }
                        else {
                            solutions[redTiles][blueTiles][pos] += solutions[redTiles - 1][blueTiles][pos - 2];
                        }

                        //count one less blue tile
                        if(blueTiles - 1 == 0) {
                            solutions[redTiles][blueTiles][pos] += this.redSolutions[redTiles][pos - 4];
                        }
                        else {
                            solutions[redTiles][blueTiles][pos] += solutions[redTiles][blueTiles - 1][pos - 4];
                        }
                    }
                }
            }
        }
        return solutions;
    }

    private long[][][] countWaysForGreenBlueTiles(int greenMaxTiles, int blueMaxTiles) {
        long[][][] solutions = new long[greenMaxTiles + 1][blueMaxTiles + 1][this.length + 1];
        for(int greenTiles = 1; greenTiles <= greenMaxTiles; greenTiles++) {
            for(int blueTiles = 1; blueTiles <= blueMaxTiles; blueTiles++) {
                for(int pos = greenTiles * 3 + blueTiles * 4; pos <= this.length; pos++) {
                    if(pos == greenTiles * 3 + blueTiles * 4) {
                        solutions[greenTiles][blueTiles][pos] = countPermutations(greenTiles, blueTiles).longValue();
                    }
                    else {
                        solutions[greenTiles][blueTiles][pos] = solutions[greenTiles][blueTiles][pos - 1];

                        //count one less green tile
                        if(greenTiles - 1 == 0) {
                            solutions[greenTiles][blueTiles][pos] += this.blueSolutions[blueTiles][pos - 3];
                        }
                        else {
                            solutions[greenTiles][blueTiles][pos] += solutions[greenTiles - 1][blueTiles][pos - 3];
                        }

                        //count one less blue tile
                        if(blueTiles - 1 == 0) {
                            solutions[greenTiles][blueTiles][pos] += this.greenSolutions[greenTiles][pos - 4];
                        }
                        else {
                            solutions[greenTiles][blueTiles][pos] += solutions[greenTiles][blueTiles - 1][pos - 4];
                        }
                    }
                }
            }
        }

        return solutions;
    }

    private long[][][][] countWaysForRedGreenBlueTiles(int redMaxTiles, int greenMaxTiles, int blueMaxTiles) {
        long[][][][] solutions = new long[redMaxTiles + 1][greenMaxTiles + 1][blueMaxTiles + 1][this.length + 1];
        for(int redTiles = 1; redTiles <= redMaxTiles; redTiles++) {
            for (int greenTiles = 1; greenTiles <= greenMaxTiles; greenTiles++) {
                for (int blueTiles = 1; blueTiles <= blueMaxTiles; blueTiles++) {
                    int startPos = redTiles * 2 + greenTiles * 3 + blueTiles * 4;
                    for(int pos = startPos; pos <= this.length; pos++) {
                        if(pos == startPos) {
                          solutions[redTiles][greenTiles][blueTiles][pos] = countPermutations(redTiles, greenTiles, blueTiles).longValue();
                        }
                        else {
                            solutions[redTiles][greenTiles][blueTiles][pos] = solutions[redTiles][greenTiles][blueTiles][pos - 1];

                            //count one less red tile
                            if(redTiles - 1 == 0) {
                               solutions[redTiles][greenTiles][blueTiles][pos] += this.greenBlueSolutions[greenTiles][blueTiles][pos - 2];
                            }
                            else {
                                solutions[redTiles][greenTiles][blueTiles][pos] += solutions[redTiles - 1][greenTiles][blueTiles][pos - 2];
                            }

                            //count one less green tile
                            if(greenTiles - 1 == 0) {
                                solutions[redTiles][greenTiles][blueTiles][pos] += this.redBlueSolutions[redTiles][blueTiles][pos - 3];
                            }
                            else {
                                solutions[redTiles][greenTiles][blueTiles][pos] += solutions[redTiles][greenTiles - 1][blueTiles][pos - 3];
                            }

                            //count one less blue tile
                            if(blueTiles - 1 == 0) {
                                solutions[redTiles][greenTiles][blueTiles][pos] += this.redGreenSolutions[redTiles][greenTiles][pos - 4];
                            }
                            else {
                                solutions[redTiles][greenTiles][blueTiles][pos] += solutions[redTiles][greenTiles][blueTiles - 1][pos - 4];
                            }
                        }
                    }
                }
            }
        }

        return solutions;
    }

    private BigDecimal countPermutations(int numOfTiles1, int numOfTiles2) {
        BigDecimal numerator = factorial(numOfTiles1 + numOfTiles2);
        BigDecimal denominator1 = factorial(numOfTiles1);
        BigDecimal denominator2  = factorial(numOfTiles2);
        BigDecimal denominator = denominator1.multiply(denominator2);

        return numerator.divide(denominator, MathContext.UNLIMITED);
    }

    private BigDecimal countPermutations(int numOfTiles1, int numOfTiles2, int numOfTiles3) {
        BigDecimal numerator = factorial(numOfTiles1 + numOfTiles2 + numOfTiles3);
        BigDecimal denominator1 = factorial(numOfTiles1);
        BigDecimal denominator2  = factorial(numOfTiles2);
        BigDecimal denominator3  = factorial(numOfTiles3);
        BigDecimal denominator = denominator1.multiply(denominator2).multiply(denominator3);

        return numerator.divide(denominator, MathContext.UNLIMITED);
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
