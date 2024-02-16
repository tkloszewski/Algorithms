package com.smart.tkl.euler.p84;

import com.smart.tkl.lib.linear.MatrixUtils;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.smart.tkl.euler.p84.MonopolyGameConstants.*;

public class MonopolyGameWithMarkovChains {

    private final int dice;
    private static final MathContext MATH_CONTEXT = new MathContext(30, RoundingMode.HALF_EVEN);

    private static final int ITERATIONS = 100;

    public MonopolyGameWithMarkovChains(int dice) {
        this.dice = dice;
    }

    public static void main(String[] args) {
        MonopolyGameWithMarkovChains game = new MonopolyGameWithMarkovChains(6);
        double[][] transitionMatrix = game.getTransitionMatrix();
        for(int pos = 0; pos < 40; pos++) {
            double[] probability = transitionMatrix[pos];
            double totalProbability = 0.0;
            for(double p : probability) {
                totalProbability += p;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%5.12f",totalProbability));
            sb.append(" => ");
            sb.append(BOARD_MAP.get(pos));
            sb.append(" => [");
            for(double p : probability) {
                sb.append(String.format("%5.7f",p));
                sb.append(", ");
            }
            sb.append("]");
            System.out.println(sb);
        }

        List<String> fullSolution = game.getFullSolution();
        System.out.println(fullSolution);
        System.out.println(String.join(" ", fullSolution));
        System.out.println(game.getSolution(3));
    }

    public String getSolution(int K) {
        List<String> fullSolution = getFullSolution();
        List<String> bestSolution = fullSolution.subList(0, K);
        return String.join(" ", bestSolution);
    }

    public List<String> getFullSolution() {
        List<String> result = new ArrayList<>();
        List<SquareProbability> squareProbabilities = getSortedProbability();
        for(SquareProbability squareProbability : squareProbabilities) {
            result.add(BOARD_MAP.get(squareProbability.square));
        }
        return result;
    }

    private List<SquareProbability> getSortedProbability() {
        double[] stationaryVector = getStationaryVector();
        List<SquareProbability> squareProbabilities = new ArrayList<>();
        for(int i = 0; i < 40; i++) {
            squareProbabilities.add(new SquareProbability(i, stationaryVector[i]));
        }
        Collections.sort(squareProbabilities);
        return squareProbabilities;
    }

    private double[] getStationaryVector() {
        double[][] vector = new double[1][40];
        for(int i = 0; i < 40; i++) {
            vector[0][i] = 1.0/ 40;
        }

        double[][] transitionMatrix = getTransitionMatrix();
        transitionMatrix = MatrixUtils.pow(transitionMatrix, ITERATIONS);
        vector = MatrixUtils.multiply(vector, transitionMatrix);

        return vector[0];
    }

    private double[][] getTransitionMatrix() {
        double[][] transition = new double[40][40];
        BigDecimal[] rollsDistribution = getRollsDistribution(this.dice);

        for(int pos = 0; pos < 40; pos++) {
            transition[pos] = getDistribution(pos, rollsDistribution);
        }
        return transition;
    }

    private static BigDecimal[] getRollsDistribution(int dice) {
        BigDecimal[] distribution = new BigDecimal[2 * dice + 1];
        Arrays.fill(distribution, BigDecimal.ZERO);

        BigDecimal all = BigDecimal.valueOf(dice).pow(2);
        for(int i = 1; i <= dice; i++) {
            for(int j = 1; j <= dice; j++) {
                distribution[i + j] = distribution[i + j].add(BigDecimal.ONE);
            }
        }
        for(int i = 0; i < distribution.length; i++) {
            distribution[i] = distribution[i].divide(all, MATH_CONTEXT);
        }
        return distribution;
    }

    private static double[] getDistribution(int pos, BigDecimal[] rollsProbability) {
        BigDecimal[] distribution = new BigDecimal[40];
        for(int i = 0; i < 40; i++) {
            distribution[i] = BigDecimal.ZERO;
        }

        BigDecimal bg16 = new BigDecimal("16");
        BigDecimal advanceToGoCCProbability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal jailCCProbability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal stayCCProbability = new BigDecimal("14").divide(bg16, MATH_CONTEXT);;

        BigDecimal advanceToGoCHProbability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal jailCHProbability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal stayCHProbability = new BigDecimal("6").divide(bg16, MATH_CONTEXT);;
        BigDecimal c1Probability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal e3Probability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal h2Probability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal r1Probability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal nextRProbability = new BigDecimal("2").divide(bg16, MATH_CONTEXT);
        BigDecimal nextUProbability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);
        BigDecimal goBack3SquaresProbability = BigDecimal.ONE.divide(bg16, MATH_CONTEXT);

        for(int roll = 2; roll < rollsProbability.length; roll++) {
            BigDecimal rollProbability = rollsProbability[roll];
            int nextPos = (pos + roll) % 40;
            if(nextPos == G2J) {
                distribution[JAIL] = distribution[JAIL].add(rollProbability);
            }
            else if(COMMUNITY_CHEST_SQUARES.contains(nextPos)) {
                distribution[JAIL] = distribution[JAIL].add(rollProbability.multiply(jailCCProbability));
                distribution[GO] = distribution[GO].add(rollProbability.multiply(advanceToGoCCProbability));
                distribution[nextPos] = distribution[nextPos].add(rollProbability.multiply(stayCCProbability));
            }
            else if(CHANCE_SQUARES.contains(nextPos)) {
                if(nextPos == CH1 || nextPos == CH2) {
                    distribution[JAIL] = distribution[JAIL].add(rollProbability.multiply(jailCHProbability));
                    distribution[GO] = distribution[GO].add(rollProbability.multiply(advanceToGoCHProbability));
                    distribution[nextPos] = distribution[nextPos].add(rollProbability.multiply(stayCHProbability));
                    distribution[C1] = distribution[C1].add(rollProbability.multiply(c1Probability));
                    distribution[E3] = distribution[E3].add(rollProbability.multiply(e3Probability));
                    distribution[H2] = distribution[H2].add(rollProbability.multiply(h2Probability));
                    distribution[R1] = distribution[R1].add(rollProbability.multiply(r1Probability));

                    if (nextPos == CH1) {
                        distribution[R2] = distribution[R2].add(rollProbability.multiply(nextRProbability));
                        distribution[U1] = distribution[U1].add(rollProbability.multiply(nextUProbability));
                        distribution[T1] = distribution[T1].add(rollProbability.multiply(goBack3SquaresProbability));
                    }
                    else {
                        distribution[R3] = distribution[R3].add(rollProbability.multiply(nextRProbability));
                        distribution[U2] = distribution[U2].add(rollProbability.multiply(nextUProbability));
                        distribution[D3] = distribution[D3].add(rollProbability.multiply(goBack3SquaresProbability));
                    }
                }
                else if(nextPos == CH3) {
                    distribution[CH3] = distribution[CH3].add(rollProbability.multiply(stayCHProbability));
                    distribution[CC3] = distribution[CC3].add(rollProbability.multiply(goBack3SquaresProbability).multiply(stayCCProbability));
                    distribution[GO] = distribution[GO].add(rollProbability.multiply(advanceToGoCHProbability.add(goBack3SquaresProbability.multiply(advanceToGoCCProbability))));
                    distribution[JAIL] = distribution[JAIL].add(rollProbability.multiply(jailCHProbability.add(goBack3SquaresProbability.multiply(jailCCProbability))));
                    distribution[C1] = distribution[C1].add(rollProbability.multiply(c1Probability));
                    distribution[E3] = distribution[E3].add(rollProbability.multiply(e3Probability));
                    distribution[H2] = distribution[H2].add(rollProbability.multiply(h2Probability));
                    distribution[R1] = distribution[R1].add(rollProbability.multiply(r1Probability.add(nextRProbability)));
                    distribution[U1] = distribution[U1].add(rollProbability.multiply(nextUProbability));
                }
            }
            else {
                distribution[nextPos] = distribution[nextPos].add(rollProbability);
            }
        }

        double[] result = new double[40];
        for(int i = 0; i < 40; i++) {
            result[i] = distribution[i].doubleValue();
        }

        return result;
    }

    private static class SquareProbability implements Comparable<SquareProbability> {
        int square;
        double probability;

        public SquareProbability(int square, double probability) {
            this.square = square;
            this.probability = probability;
        }

        @Override
        public int compareTo(SquareProbability o) {
            return Double.compare(o.probability, this.probability);
        }
    }

}
