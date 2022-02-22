package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Card;
import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;

import static com.smart.tkl.euler.poker.RankUtils.*;

public class TwoPairsComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        Card[] threeCards1 = getUniqueThreeCards(hand1.getCards());
        Card[] threeCards2 = getUniqueThreeCards(hand2.getCards());
        return compareCards(2, threeCards1, threeCards2);
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        return isTwoPairs(hand1.getCards()) && isTwoPairs(hand2.getCards());
    }

    @Override
    protected Rank getRank() {
        return Rank.TWO_PAIRS;
    }

    private Card[] getUniqueThreeCards(Card[] cards) {
        Card[] result = new Card[3];

        int lowerPairCardPos = -1, higherPairCardPos = -1;
        for(int i = 0; i <= 3; i++) {
            if(twoEquals(i, cards)) {
               if(i == 0 || i == 1) {
                  result[1] = cards[i];
                  lowerPairCardPos = i;
               }
               else {
                  result[2] = cards[i];
                  higherPairCardPos = i;
               }
            }
        }

        if(lowerPairCardPos == 1) {
           result[0] = cards[0];
        }
        else if(higherPairCardPos == 2) {
           result[0] = cards[4];
        }
        else {
           result[0] = cards[2];
        }

        return result;
    }
}
