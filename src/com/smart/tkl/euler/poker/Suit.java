package com.smart.tkl.euler.poker;

public enum Suit {
    HEARTS("H"), DIAMONDS("D"), CLUBS("C"), SPADES("S");

    private String code;

    Suit(String code) {
        this.code = code;
    }

    public static Suit fromCode(String code) {
        for(Suit suit : values()) {
            if(suit.code.equals(code)) {
                return suit;
            }
        }
        throw new RuntimeException("Unrecognized suit for code: " + code);
    }

    public String code() {
        return code;
    }
}
