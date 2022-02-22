package com.smart.tkl.euler.poker;

public class RankUtils {

    public static int compareCards(int from, Card[] cards1, Card[] cards2) {
        int result = 0;
        for(int i = from; i >= 0 && result == 0; i--) {
            result = cards1[i].compareTo(cards2[i]);
        }
        return result;
    }

    public static boolean isRoyalFlush(Card[] cards) {
        return isStraightFlush(cards) && cards[4].getType().equals(CardType.ACE);
    }

    public static boolean isStraightFlush(Card[] cards) {
        return isStraight(cards) && isFlush(cards);
    }

    public static boolean isFourOfKind(Card[] cards) {
        return fourEquals(0, cards) || fourEquals(1, cards);
    }

    public static boolean isFullHouse(Card[] cards) {
        return (threeEquals(0, cards) && twoEquals(3, cards)) ||
                (twoEquals(0, cards) && threeEquals(2, cards));
    }

    public static boolean isFlush(Card[] cards) {
        Suit suit = cards[0].getSuit();
        for(int i = 1; i < cards.length; i++) {
            if(!cards[i].getSuit().equals(suit)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStraight(Card[] cards) {
        for(int i = 0; i < cards.length - 1; i++) {
            int value1 = cards[i].getValue();
            int value2 = cards[i + 1].getValue();
            if(value1 + 1 != value2) {
                return false;
            }
        }
        return true;
    }

    public static boolean isThreeOfKind(Card[] cards) {
        return threeEquals(0, cards) || threeEquals(1, cards) || threeEquals(2, cards);
    }

    public static boolean isTwoPairs(Card[] cards) {
        return (twoEquals(0, cards) && twoEquals(2, cards)) ||
                (twoEquals(0, cards) && twoEquals(3, cards)) ||
                (twoEquals(1, cards) && twoEquals(3, cards));
    }

    public static boolean isOnePair(Card[] cards) {
        return twoEquals(0, cards) || twoEquals(1, cards) ||
                twoEquals(2, cards) || twoEquals(3, cards);
    }

    public static boolean twoEquals(int pos, Card[] cards) {
        return cards[pos].getValue().equals(cards[pos + 1].getValue());
    }

    public static boolean threeEquals(int pos, Card[] cards) {
        Integer value = cards[pos].getValue();
        return cards[pos + 1].getValue().equals(value) &&
                cards[pos + 2].getValue().equals(value);
    }

    public static boolean fourEquals(int pos, Card[] cards) {
        Integer value = cards[pos].getValue();
        return cards[pos + 1].getValue().equals(value) &&
                cards[pos + 2].getValue().equals(value) &&
                cards[pos + 3].getValue().equals(value);
    }
}
