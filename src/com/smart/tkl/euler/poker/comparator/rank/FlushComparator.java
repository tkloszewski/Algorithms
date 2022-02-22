package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;
import static com.smart.tkl.euler.poker.RankUtils.*;

public class FlushComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        return compareCards(4, hand1.getCards(), hand2.getCards());
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        return isFlush(hand1.getCards()) && isFlush(hand2.getCards());
    }

    @Override
    protected Rank getRank() {
        return Rank.FLUSH;
    }
}
