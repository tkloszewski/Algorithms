package com.smart.tkl.euler.poker;

import static com.smart.tkl.euler.poker.RankUtils.*;

public class RankResolver {

    public Rank resolveRank(Card[] cards) {
        Rank rank = Rank.HIGH_CARD;

        if(isRoyalFlush(cards)) {
            rank = Rank.ROYAL_FLUSH;
        }
        else if(isStraightFlush(cards)) {
            rank = Rank.STRAIGHT_FLUSH;
        }
        else if(isFourOfKind(cards)) {
            rank = Rank.FOUR_OF_A_KIND;
        }
        else if(isFullHouse(cards)) {
            rank = Rank.FULL_HOUSE;
        }
        else if(isFlush(cards)) {
            rank = Rank.FLUSH;
        }
        else if(isStraight(cards)) {
            rank = Rank.STRAIGHT;
        }
        else if(isThreeOfKind(cards)) {
            rank = Rank.THREE_OF_A_KIND;
        }
        else if(isTwoPairs(cards)) {
            rank = Rank.TWO_PAIRS;
        }
        else if(isOnePair(cards)) {
            rank = Rank.ONE_PAIR;
        }

        return rank;
    }
}
