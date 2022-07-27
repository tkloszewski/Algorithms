package com.smart.tkl.euler.p84;

import com.smart.tkl.utils.MathUtils;

import java.util.*;

public class MonopolyGame {

    private static final Map<Integer, String> BOARD_MAP = Map.ofEntries(
            Map.entry(0, "GO"),
            Map.entry(1, "A1"),
            Map.entry(2, "CC1"),
            Map.entry(3, "A2"),
            Map.entry(4, "T1"),
            Map.entry(5, "R1"),
            Map.entry(6, "B1"),
            Map.entry(7, "CH1"),
            Map.entry(8, "B2"),
            Map.entry(9, "B3"),
            Map.entry(10, "JAIL"),
            Map.entry(11, "C1"),
            Map.entry(12, "U1"),
            Map.entry(13, "C2"),
            Map.entry(14, "C3"),
            Map.entry(15, "R2"),
            Map.entry(16, "D1"),
            Map.entry(17, "CC2"),
            Map.entry(18, "D2"),
            Map.entry(19, "D3"),
            Map.entry(20, "FP"),
            Map.entry(21, "E1"),
            Map.entry(22, "CH2"),
            Map.entry(23, "E2"),
            Map.entry(24, "E3"),
            Map.entry(25, "R3"),
            Map.entry(26, "F1"),
            Map.entry(27, "F2"),
            Map.entry(28, "U2"),
            Map.entry(29, "F3"),
            Map.entry(30, "G2J"),
            Map.entry(31, "G1"),
            Map.entry(32, "G2"),
            Map.entry(33, "CC3"),
            Map.entry(34, "G3"),
            Map.entry(35, "R4"),
            Map.entry(36, "CH3"),
            Map.entry(37, "H1"),
            Map.entry(38, "T2"),
            Map.entry(39, "H2"));

    private static final int STAY = 0;
    private static final int ADVANCE_TO_GO = 1;
    private static final int GO_TO_JAIL = 2;
    private static final int GO_TO_C1 = 3;
    private static final int GO_TO_E3 = 4;
    private static final int GO_TO_H2 = 5;
    private static final int GO_TO_R1 = 6;
    private static final int GO_TO_NEXT_R = 7;
    private static final int GO_TO_NEXT_U = 8;
    private static final int GO_BACK_3_SQUARES = 9;

    private static final int GO = 0;
    private static final int JAIL = 10;
    private static final int G2J = 30;
    private static final int C1 = 11;
    private static final int E3 = 24;
    private static final int H2 = 39;
    private static final int R1 = 5;
    private static final int R2 = 15;
    private static final int R3 = 25;
    private static final int U1 = 12;
    private static final int U2 = 28;
    private static final int CC1 = 2;
    private static final int CC2 = 17;
    private static final int CC3 = 33;
    private static final int CH1 = 7;
    private static final int CH2 = 22;
    private static final int CH3 = 36;

    private static final Set<Integer> COMMUNITY_CHEST_SQUARES = Set.of(CC1, CC2, CC3);
    private static final Set<Integer> CHANCE_SQUARES = Set.of(CH1, CH2, CH3);

    private final int[] communityChestCards = MathUtils.shuffle(new int[]{ADVANCE_TO_GO, GO_TO_JAIL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

    private final int[] chanceCards = MathUtils.shuffle(new int[]{ADVANCE_TO_GO, GO_TO_JAIL, GO_TO_C1, GO_TO_E3, GO_TO_H2, GO_TO_R1, GO_TO_NEXT_R,
            GO_TO_NEXT_R, GO_TO_NEXT_U, GO_BACK_3_SQUARES, 0, 0, 0, 0, 0, 0});

    private final int dice;
    private final int steps;
    private final SplittableRandom diceRoller = new SplittableRandom();
    private int communityChestCardPos = 0;
    private int chanceCardPos = 0;

    public static void main(String[] args) {
        int numOfSteps = 100000;

        MonopolyGame monopolyGame1 = new MonopolyGame(6, numOfSteps);
        String sixDigitModal = monopolyGame1.play();
        System.out.println("Six digit modal for 6-sided dice: " + sixDigitModal);

        MonopolyGame monopolyGame2 = new MonopolyGame(4, numOfSteps);
        sixDigitModal = monopolyGame2.play();
        System.out.println("Six digit modal for 4-sided dice: " + sixDigitModal);
    }

    public MonopolyGame(int dice, int steps) {
        this.dice = dice;
        this.steps = steps;
    }

    public String play() {
        int[] boardFreq = new int[40];
        int currentPos = 0;
        int doublesCount = 0;
        for(int step = 0; step < steps; step++) {
            int roll1 = rollDice();
            int roll2 = rollDice();
            if(roll1 == roll2) {
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
                }
            }
            if(currentPos == G2J) {
                currentPos = JAIL;
            }

            boardFreq[currentPos]++;
        }

        System.out.println("Board Freq: " + Arrays.toString(boardFreq));
        List<Integer> mostFrequentSquares = getThreeMostFrequentlyVisitedSquares(boardFreq);
        for(int mostFrequentSquare : mostFrequentSquares) {
            System.out.println("Most frequent square: " + BOARD_MAP.get(mostFrequentSquare));
        }

        return resolveSixDigitModal(mostFrequentSquares);
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
}
