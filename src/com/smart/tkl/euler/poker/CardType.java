package com.smart.tkl.euler.poker;

public enum CardType {
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "T"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K"),
    ACE(14,"A");

    private Integer value;
    private String code;

    CardType(int value, String code) {
        this.value = value;
        this.code = code;
    }

    public static CardType fromCode(String code) {
        for(CardType cardType : values()){
            if(cardType.code.equals(code)) {
               return cardType;
            }
        }
        throw new RuntimeException("Unrecognized card for code: " + code);
    }

    public Integer value() {
        return value;
    }

    public String code() {
        return code;
    }
}
