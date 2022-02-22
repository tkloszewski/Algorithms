package com.smart.tkl.euler.poker;

public class Card implements Comparable<Card> {

   private final CardType type;
   private final Suit suit;

    public Card(CardType type, Suit suit) {
        this.type = type;
        this.suit = suit;
    }

    public Card(String type, String suit) {
        this.type = CardType.fromCode(type);
        this.suit = Suit.fromCode(suit);
    }

    @Override
    public int compareTo(Card other) {
        return this.type.value().compareTo(other.getType().value());
    }

    public CardType getType() {
        return type;
    }

    public Suit getSuit() {
        return suit;
    }

    public Integer getValue() {
        return type.value();
    }

    @Override
    public String toString() {
        return type + ":" + suit;
    }
}
