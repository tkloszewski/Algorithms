package com.smart.tkl.euler.poker;

public class GameResult {

    private Hand player1;
    private Hand player2;

    private int player1Points = 0;
    private int player2Points = 0;

    public GameResult(Hand player1, Hand player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Hand getPlayer1() {
        return player1;
    }

    public Hand getPlayer2() {
        return player2;
    }

    public int getPlayer1Points() {
        return player1Points;
    }

    public void setPlayer1Points(int player1Points) {
        this.player1Points = player1Points;
    }

    public int getPlayer2Points() {
        return player2Points;
    }

    public void setPlayer2Points(int player2Points) {
        this.player2Points = player2Points;
    }

    @Override
    public String toString() {
        return  "player1 => " + player1 + " : " + player1Points + " | player2 => " + player2 + " : " + player2Points;
    }
}
