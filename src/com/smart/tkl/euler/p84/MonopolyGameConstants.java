package com.smart.tkl.euler.p84;

import com.smart.tkl.lib.utils.MathUtils;
import java.util.Map;
import java.util.Set;

public class MonopolyGameConstants {
    static final Map<Integer, String> BOARD_MAP = Map.ofEntries(
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

    static final int STAY = 0;
    static final int ADVANCE_TO_GO = 1;
    static final int GO_TO_JAIL = 2;
    static final int GO_TO_C1 = 3;
    static final int GO_TO_E3 = 4;
    static final int GO_TO_H2 = 5;
    static final int GO_TO_R1 = 6;
    static final int GO_TO_NEXT_R = 7;
    static final int GO_TO_NEXT_U = 8;
    static final int GO_BACK_3_SQUARES = 9;

    static final int GO = 0;
    static final int T1 = 4;
    static final int JAIL = 10;
    static final int G2J = 30;
    static final int C1 = 11;
    static final int E3 = 24;
    static final int H2 = 39;
    static final int R1 = 5;
    static final int R2 = 15;
    static final int R3 = 25;
    static final int U1 = 12;
    static final int U2 = 28;
    static final int D3 = 19;
    static final int CC1 = 2;
    static final int CC2 = 17;
    static final int CC3 = 33;
    static final int CH1 = 7;
    static final int CH2 = 22;
    static final int CH3 = 36;

    static final Set<Integer> COMMUNITY_CHEST_SQUARES = Set.of(CC1, CC2, CC3);
    static final Set<Integer> CHANCE_SQUARES = Set.of(CH1, CH2, CH3);

    final static int[] communityChestCards = MathUtils.shuffle(new int[]{ADVANCE_TO_GO, GO_TO_JAIL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

    final static int[] chanceCards = MathUtils.shuffle(new int[]{ADVANCE_TO_GO, GO_TO_JAIL, GO_TO_C1, GO_TO_E3, GO_TO_H2, GO_TO_R1, GO_TO_NEXT_R,
            GO_TO_NEXT_R, GO_TO_NEXT_U, GO_BACK_3_SQUARES, 0, 0, 0, 0, 0, 0});
}
