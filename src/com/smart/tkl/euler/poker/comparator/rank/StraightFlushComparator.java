package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Card;
import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;
import static com.smart.tkl.euler.poker.RankUtils.*;

public class StraightFlushComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        Card highestCard1 = hand1.getCard(4);
        Card highestCard2 = hand2.getCard(4);
        return highestCard1.compareTo(highestCard2);
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        boolean flush1 = isStraightFlush(hand1.getCards());
        boolean flush2 = isStraightFlush(hand2.getCards());
        return flush1 && flush2;
    }

    @Override
    protected Rank getRank() {
        return Rank.STRAIGHT_FLUSH;
    }
}
