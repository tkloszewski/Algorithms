package com.smart.tkl.euler.poker;

import com.smart.tkl.euler.poker.comparator.CardComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokerPlayer {

    private static final String POKER_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\poker\\p054_poker.txt";
    private static final String POKER_TEST_FILE_PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\poker\\test_poker.txt";

    private final HandsReader handsReader = new HandsReader(POKER_FILE_PATH);
    private final CardComparator cardComparator = new CardComparator();

    public static void main(String[] args) {
        System.out.println("Poker hands: " + pokerHands());
    }

    public static int pokerHands() {
        PokerPlayer pokerPlayer = new PokerPlayer();
        List<GameResult> games = pokerPlayer.play();
        printGameResults(games);
        return games.stream().map(GameResult::getPlayer1Points).reduce(0, Integer::sum);
    }

    private static void printGameResults(List<GameResult> gameResults) {
        for(GameResult gameResult : gameResults) {
            System.out.println(gameResult + "\n");
        }
    }

    public List<GameResult> play() {
        List<GameResult> games = new ArrayList<>(1000);
        try {
            for(Hands game : handsReader.readHands()) {
                GameResult gameResult = new GameResult(game.player1, game.player2);
                int result = cardComparator.compare(game.player1, game.player2);
                if(result == 1) {
                   gameResult.setPlayer1Points(1);
                }
                else if(result == -1) {
                   gameResult.setPlayer2Points(1);
                }
                games.add(gameResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }
}
