package com.smart.tkl.euler.poker;

import java.util.Arrays;
import java.util.List;

public class Hand {

    private final Card[] cards;
    private Rank rank;

    public Hand(List<Card> cards, Rank rank) {
        assert cards != null;
        assert cards.size() == 5;
        this.cards = cards.toArray(new Card[5]);
        this.rank = rank;
    }

    public Hand(Card[] cards, Rank rank) {
        this.cards = cards;
        this.rank = rank;
    }

    public Card[] getCards() {
        return cards;
    }

    public Card getCard(int index) {
        assert index >= 0 && index < 5;
        return cards[index];
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        if(this.rank == null) {
            this.rank = rank;
        }
    }

    public int length() {
        return cards.length;
    }

    @Override
    public String toString() {
        String rank = this.rank != null ? this.rank.name() : "CARDS";
        return rank + " => " + Arrays.toString(cards);
    }
}
