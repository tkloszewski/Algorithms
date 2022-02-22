package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;

import static com.smart.tkl.euler.poker.RankUtils.isRoyalFlush;

public class RoyalFlushComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        return 0;
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        boolean flush1 = isRoyalFlush(hand1.getCards());
        boolean flush2 = isRoyalFlush(hand2.getCards());
        return flush1 && flush2;
    }

    @Override
    protected Rank getRank() {
        return Rank.ROYAL_FLUSH;
    }
}
