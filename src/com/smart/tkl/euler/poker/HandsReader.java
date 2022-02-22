package com.smart.tkl.euler.poker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandsReader {

    private final String filePath;
    private final RankResolver rankResolver = new RankResolver();

    public HandsReader(String filePath) {
        this.filePath = filePath;
    }

    public List<Hands> readHands() throws IOException {
        List<Hands> result = new ArrayList<>(1000);

        try(BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
            String line;
            while((line = br.readLine()) != null) {
                Hands hands = readHands(line);
                if(hands != null) {
                   result.add(hands);
                }
            }
        }

        return result;
    }

    private Hands readHands(String line) {
        String[] cardCodes = line.trim().split("\\s+");
        if(cardCodes.length != 10) {
            throw new RuntimeException("Line must have 10 line codes");
        }
        return readHands(cardCodes);
    }

    private Hands readHands(String[] cardCodes) {
        if(cardCodes.length != 10) {
            return null;
        }
        Hand player1 = readFirstHand(cardCodes);
        Hand player2 = readSecondHand(cardCodes);
        return new Hands(player1, player2);
    }

    private Hand readFirstHand(String[] cardCodes) {
        return readHand(cardCodes, 0, 4);
    }

    private Hand readSecondHand(String[] cardCodes) {
        return readHand(cardCodes, 5, 9);
    }

    private Hand readHand(String[] cardCodes, int from, int to) {
        List<Card> cards = new ArrayList<>();
        for(int i = from; i <= to; i++) {
            Card card = readCard(cardCodes[i]);
            cards.add(card);
        }
        Collections.sort(cards);
        Card[] cardsArray = cards.toArray(new Card[5]);
        Rank rank = rankResolver.resolveRank(cardsArray);
        return new Hand(cardsArray, rank);
    }

    private Card readCard(String cardCode) {
        CardType cardType = CardType.fromCode(cardCode.substring(0,1));
        Suit suit = Suit.fromCode(cardCode.substring(1,2));
        return new Card(cardType, suit);
    }
}
