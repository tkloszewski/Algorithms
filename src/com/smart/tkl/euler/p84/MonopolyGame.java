package com.smart.tkl.euler.p84;

import static com.smart.tkl.euler.p84.MonopolyGameConstants.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;

public class MonopolyGame {

    private final int dice;
    private final int steps;
    private final int mostFrequentCount;
    private final boolean consecutiveDoublesRuleEnabled;
    private final SplittableRandom diceRoller = new SplittableRandom();
    private int communityChestCardPos = 0;
    private int chanceCardPos = 0;

    public static void main(String[] args) {
        int numOfSteps = 1000000;

        MonopolyGame monopolyGame1 = new MonopolyGame(6, numOfSteps, 3, true);
        String sixDigitModal = monopolyGame1.play();
        System.out.println("Six digit modal for 6-sided dice: " + sixDigitModal);

        MonopolyGame monopolyGame2 = new MonopolyGame(4, numOfSteps, 3, true);
        sixDigitModal = monopolyGame2.play();
        System.out.println("Six digit modal for 4-sided dice: " + sixDigitModal);
    }

    public MonopolyGame(int dice, int steps, int mostFrequentCount, boolean consecutiveDoublesRuleEnabled) {
        this.dice = dice;
        this.steps = steps;
        this.mostFrequentCount = mostFrequentCount;
        this.consecutiveDoublesRuleEnabled = consecutiveDoublesRuleEnabled;
    }

    public String play() {
        List<Integer> mostFrequentSquares = findMostFrequentSquare();
        for(int mostFrequentSquare : mostFrequentSquares) {
            System.out.println("Most frequent square: " + BOARD_MAP.get(mostFrequentSquare));
        }
        return resolveSixDigitModal(mostFrequentSquares);
    }

    public String play2() {
        StringBuilder sb = new StringBuilder();
        List<Integer> mostFrequentSquares = findMostFrequentSquare();
        for(int i = 0; i < mostFrequentSquares.size(); i++) {
            int mostFrequentSquare = mostFrequentSquares.get(i);
            sb.append(BOARD_MAP.get(mostFrequentSquare));
            if (i < mostFrequentSquares.size() - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    private List<Integer> findMostFrequentSquare() {
        int[] boardFreq = doPlay();

        System.out.println("Freq: " + Arrays.toString(boardFreq));

        List<SquareFreq> squareFreqList = new ArrayList<>();
        for(int i = 0; i < boardFreq.length; i++) {
            squareFreqList.add(new SquareFreq(i, boardFreq[i]));
        }

        Collections.sort(squareFreqList);

        List<Integer> mostFrequentSquares = new ArrayList<>(mostFrequentCount);
        for(int i = 0; i < mostFrequentCount; i++) {
            mostFrequentSquares.add(squareFreqList.get(i).square);
        }
        return mostFrequentSquares;
    }

    private int[] doPlay() {
        int[] boardFreq = new int[40];
        int currentPos = 0;
        int doublesCount = 0;
        for(int step = 0; step < steps; step++) {
            int roll1 = rollDice();
            int roll2 = rollDice();
            if(consecutiveDoublesRuleEnabled && roll1 == roll2) {
                if(++doublesCount == 3) {
                    currentPos = JAIL;
                    doublesCount = 0;
                }
            }
            else {
                doublesCount = 0;
                currentPos = (currentPos + (roll1 + roll2)) % 40;
            }

            if(COMMUNITY_CHEST_SQUARES.contains(currentPos) || CHANCE_SQUARES.contains(currentPos)) {
                int card = COMMUNITY_CHEST_SQUARES.contains(currentPos) ? pickNextCommunityChestCard() :
                        pickNextChanceCard();

                if(card != STAY) {
                    currentPos = resolveNextCardPosition(currentPos, card);
                    if(currentPos == CC3) {
                       card = pickNextCommunityChestCard();
                       currentPos = resolveNextCardPosition(currentPos, card);
                    }
                }
            }
            if(currentPos == G2J) {
                currentPos = JAIL;
            }

            boardFreq[currentPos]++;
        }
        return boardFreq;
    }

    private int resolveNextCardPosition(int currentPos, int card) {
        if(card == STAY) {
            return currentPos;
        }
        if(card == ADVANCE_TO_GO) {
            return GO;
        }
        if(card == GO_TO_JAIL) {
            return JAIL;
        }
        if(card == GO_TO_C1) {
            return C1;
        }
        if(card == GO_TO_E3) {
            return E3;
        }
        if(card == GO_TO_R1) {
            return R1;
        }
        if(card == GO_TO_H2) {
            return H2;
        }
        if(card == GO_TO_NEXT_R) {
            if(currentPos < R1 || currentPos > R3) {
                return R1;
            }
            if(currentPos > R1 && currentPos < R2) {
                return R2;
            }
            if(currentPos > R2 && currentPos < R3) {
                return R3;
            }
        }
        if(card == GO_TO_NEXT_U) {
            if(currentPos < U1 || currentPos > U2) {
                return U1;
            }
            if(currentPos > U1 && currentPos < U2) {
                return U2;
            }
        }
        if(card == GO_BACK_3_SQUARES) {
            return currentPos - 3;
        }
        throw new IllegalArgumentException("Unrecognized card value: " + card);
    }

    private List<Integer> getThreeMostFrequentlyVisitedSquares(int[] boardFreq) {
        List<Integer> result = new ArrayList<>(3);
        for(int i = 0; i < 3; i++) {
            int max = 0, maxIndex = -1;
            for(int k = 0; k < boardFreq.length; k++) {
                if(!result.contains(k) && boardFreq[k] >= max) {
                    max = boardFreq[k];
                    maxIndex = k;
                }
            }
            if(maxIndex != -1) {
                result.add(maxIndex);
            }
        }
        return result;
    }

    private int rollDice() {
        return diceRoller.nextInt(1, dice + 1);
    }

    private int pickNextCommunityChestCard() {
        return communityChestCards[communityChestCardPos++ % 16];
    }

    private int pickNextChanceCard() {
        return chanceCards[chanceCardPos++ % 16];
    }

    private static String resolveSixDigitModal(List<Integer> mostFrequentSquares) {
        StringBuilder sb = new StringBuilder();
        for(int mostFreqSquare : mostFrequentSquares) {
            sb.append(indexToString(mostFreqSquare));
        }
        return sb.toString();
    }

    private static String indexToString(int index) {
        return String.format("%02d", index);
    }

    private static class SquareFreq implements Comparable<SquareFreq> {
        int square, freq;

        public SquareFreq(int square, int freq) {
            this.square = square;
            this.freq = freq;
        }

        @Override
        public int compareTo(SquareFreq o) {
            return Integer.compare(o.freq, this.freq);
        }
    }
}
