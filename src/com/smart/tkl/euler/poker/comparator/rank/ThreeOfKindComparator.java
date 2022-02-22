package com.smart.tkl.euler.poker.comparator.rank;

import com.smart.tkl.euler.poker.Card;
import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;
import com.smart.tkl.euler.poker.RankUtils;

import static com.smart.tkl.euler.poker.RankUtils.compareCards;
import static com.smart.tkl.euler.poker.RankUtils.threeEquals;

public class ThreeOfKindComparator extends BaseRankComparator {

    @Override
    protected int doCompare(Hand hand1, Hand hand2) {
        int pos1 = getFirstTripleCardPos(hand1);
        int pos2 = getFirstTripleCardPos(hand2);

        Card card1 = hand1.getCard(pos1);
        Card card2 = hand2.getCard(pos2);

        int result = card1.compareTo(card2);
        if(result == 0) {
            Card[] other1 = getOtherTwoCards(hand1, pos1);
            Card[] other2 = getOtherTwoCards(hand2, pos2);
            result = compareCards(1, other1, other2);
        }

        return result;
    }

    @Override
    protected boolean isRankSupported(Hand hand1, Hand hand2) {
        return RankUtils.isThreeOfKind(hand1.getCards()) && RankUtils.isThreeOfKind(hand2.getCards());
    }

    @Override
    protected Rank getRank() {
        return Rank.THREE_OF_A_KIND;
    }

    private Card[] getOtherTwoCards(Hand hand, int pos) {
        Card[] other = new Card[2];
        for(int i = 0, idx = 0; i < hand.length(); i++) {
            if(i != pos && i != pos + 1 && i != pos + 2) {
                other[idx++] = hand.getCard(i);
            }
        }
        return other;
    }

    private int getFirstTripleCardPos(Hand hand) {
        int pos = 0;
        while(pos < 3 && !threeEquals(pos, hand.getCards())) {
            pos++;
        }
        return pos;
    }
}
