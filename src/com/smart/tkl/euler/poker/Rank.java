package com.smart.tkl.euler.poker;

public enum Rank {
    HIGH_CARD(1),
    ONE_PAIR(3),
    TWO_PAIRS(5),
    THREE_OF_A_KIND(7),
    STRAIGHT(9),
    FLUSH(10),
    FULL_HOUSE(12),
    FOUR_OF_A_KIND(14),
    STRAIGHT_FLUSH(18),
    ROYAL_FLUSH(19);

    private final Integer rank;

    Rank(int rank) {
        this.rank = rank;
    }

    public Integer rank() {
        return rank;
    }
}
