package com.smart.tkl.dynamic.example;

public class CoinGame {

    public enum Player {YOU, ME};

    private Player firstPlayer;
    private int[] coins;

    public CoinGame(int[] coins, Player firstMove) {
        this.coins = coins;
        this.firstPlayer = firstMove;
    }

    public int getMaxTotal(Player player) {
        return doGetMaxTotal(0, this.coins.length - 1, this.firstPlayer, player);
    }

    private int doGetMaxTotal(int i, int j, Player currentPlayer, Player chosenPlayer) {
        int result;
        int iValue = currentPlayer.equals(chosenPlayer) ? this.coins[i] : 0;
        int jValue = currentPlayer.equals(chosenPlayer) ? this.coins[j] : 0;

        Player nextPlayer = currentPlayer.equals(Player.YOU) ? Player.ME : Player.YOU;

        if(i == j) {
           result = iValue;
        }
        else {
            int value1 = iValue + doGetMaxTotal(i + 1, j, nextPlayer, chosenPlayer);
            int value2 = jValue + doGetMaxTotal(i, j - 1, nextPlayer, chosenPlayer);
            result = currentPlayer.equals(chosenPlayer) ? Math.max(value1, value2) : Math.min(value1, value2);
        }
        return result;
    }

}
