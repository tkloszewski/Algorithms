package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Card;
import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;
import static com.smart.tkl.euler.poker.RankUtils.*;

public class HighCardComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        return compareCards(4, hand1.getCards(), hand2.getCards());
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        return isHighCard(hand1) && isHighCard(hand2);
    }

    @Override
    protected Rank getRank() {
        return Rank.HIGH_CARD;
    }

    private boolean isHighCard(Hand hand) {
        Card[] cards = hand.getCards();
        return !isRoyalFlush(cards)
                && !isStraightFlush(cards)
                && !isFourOfKind(cards)
                && !isFullHouse(cards)
                && !isFlush(cards)
                && !isStraight(cards)
                && !isThreeOfKind(cards)
                && !isTwoPairs(cards)
                && !isOnePair(cards);
    }
}
