package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;
import com.smart.tkl.euler.poker.exception.UnsupportedRankException;

public abstract class BaseRankComparator implements RankComparator {

    @Override
    public int compare(Hand hand1, Hand hand2) {
        if(!isRankSupported(hand1, hand2)) {
            throw new UnsupportedRankException("One of the hands doesn't support the rank: " + getRank());
        }
        return doCompare(hand1, hand2);
    }

    protected abstract int doCompare(Hand hand1, Hand hand2);

    protected abstract boolean isRankSupported(Hand hand1, Hand hand2);

    protected abstract Rank getRank();
}
