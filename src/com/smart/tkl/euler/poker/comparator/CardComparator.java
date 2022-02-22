package com.smart.tkl.euler.poker.comparator;

import com.smart.tkl.euler.poker.Hand;
import com.smart.tkl.euler.poker.Rank;
import com.smart.tkl.euler.poker.RankResolver;
import com.smart.tkl.euler.poker.comparator.rank.*;

import java.util.HashMap;
import java.util.Map;

public class CardComparator {

    private final RankResolver rankResolver = new RankResolver();
    private final Map<Rank, RankComparator> rankComparatorMap = new HashMap<>();

    public CardComparator() {
        rankComparatorMap.put(Rank.HIGH_CARD,       new HighCardComparator());
        rankComparatorMap.put(Rank.ONE_PAIR,        new OnePairComparator());
        rankComparatorMap.put(Rank.TWO_PAIRS,       new TwoPairsComparator());
        rankComparatorMap.put(Rank.THREE_OF_A_KIND, new ThreeOfKindComparator());
        rankComparatorMap.put(Rank.STRAIGHT,        new StraightComparator());
        rankComparatorMap.put(Rank.FLUSH,           new FlushComparator());
        rankComparatorMap.put(Rank.FULL_HOUSE,      new FullHouseComparator());
        rankComparatorMap.put(Rank.FOUR_OF_A_KIND,  new FourOfKindComparator());
        rankComparatorMap.put(Rank.STRAIGHT_FLUSH,  new StraightFlushComparator());
        rankComparatorMap.put(Rank.ROYAL_FLUSH,     new RoyalFlushComparator());
    }

    public int compare(Hand hand1, Hand hand2) {
        Rank rank1 = getOrResolveRank(hand1);
        Rank rank2 = getOrResolveRank(hand2);
        int result = rank1.rank().compareTo(rank2.rank());
        if(result == 0) {
           RankComparator rankComparator = rankComparatorMap.get(rank1);
           result = rankComparator.compare(hand1, hand2);
        }
        return result;
    }

    private Rank getOrResolveRank(Hand hand) {
        return hand.getRank() != null ? hand.getRank() : rankResolver.resolveRank(hand.getCards());
    }
}
