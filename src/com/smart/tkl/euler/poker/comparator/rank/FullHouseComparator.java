package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Card;
import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;

import static com.smart.tkl.euler.poker.RankUtils.*;

public class FullHouseComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        Card[] twoUniqueCards1 = getTwoUniqueCards(hand1.getCards());
        Card[] twoUniqueCards2 = getTwoUniqueCards(hand2.getCards());
        return compareCards(1, twoUniqueCards1, twoUniqueCards2);
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        return isFullHouse(hand1.getCards()) && isFullHouse(hand2.getCards());
    }

    @Override
    protected Rank getRank() {
        return Rank.FULL_HOUSE;
    }

    private Card[] getTwoUniqueCards(Card[] cards) {
        Card[] result = new Card[2];
        if(threeEquals(0, cards) && twoEquals(3, cards)) {
            result[0] = cards[3];
            result[1] = cards[0];
        }
        else {
            result[0] = cards[0];
            result[1] = cards[2];
        }
        return result;
    }
}
