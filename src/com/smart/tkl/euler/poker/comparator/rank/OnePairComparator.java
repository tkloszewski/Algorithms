package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Card;
import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;

import java.util.Arrays;

import static com.smart.tkl.euler.poker.RankUtils.*;

public class OnePairComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        int pos1 = getFirstPairCardPos(hand1);
        int pos2 = getFirstPairCardPos(hand2);

        Card card1 = hand1.getCard(pos1);
        Card card2 = hand2.getCard(pos2);

        int result = card1.compareTo(card2);
        if(result == 0) {
            Card[] other1 = getOtherThreeCards(hand1, pos1);
            Card[] other2 = getOtherThreeCards(hand2, pos2);
            result = compareCards(2, other1, other2);
        }

        return result;
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        return isOnePair(hand1.getCards()) && isOnePair(hand2.getCards());
    }

    @Override
    protected Rank getRank() {
        return Rank.ONE_PAIR;
    }

    private Card[] getOtherThreeCards(Hand hand, int pos) {
        Card[] other = new Card[3];
        for(int i = 0, idx = 0; i < hand.length(); i++) {
            if(i != pos && i != pos + 1) {
               other[idx++] = hand.getCard(i);
            }
        }
        return other;
    }

    private int getFirstPairCardPos(Hand hand) {
        int pos = 0;
        while(pos < 4 && !twoEquals(pos, hand.getCards())) {
            pos++;
        }
        return pos;
    }


}
