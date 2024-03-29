package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.CardType;
import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;
import static com.smart.tkl.euler.poker.RankUtils.*;

public class StraightComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        if(hand1.getCard(0).getType().equals(CardType.TWO) && hand1.getCard(4).getType().equals(CardType.ACE)) {
           return hand1.getCard(3).compareTo(hand2.getCard(4));
        }
        else if(hand2.getCard(0).getType().equals(CardType.TWO) && hand2.getCard(4).getType().equals(CardType.ACE)) {
            return hand1.getCard(4).compareTo(hand2.getCard(3));

        }
        else {
            return hand1.getCard(4).compareTo(hand2.getCard(4));
        }
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        return isStraight(hand1.getCards()) && isStraight(hand2.getCards());
    }

    @Override
    protected Rank getRank() {
        return Rank.STRAIGHT;
    }
}
