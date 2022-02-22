package com.smart.tkl.dynamic.example;

public class Examples2 {

    public static void main(String[] args) {
        LCM lcm = new LCM("THEIR", "HABIT");
        System.out.println("LCM: " + lcm.get());
        System.out.println("LCM: " + new LCM("HIEROGLYPHOLOGY", "MICHAELANGELO").get());
        System.out.println("LIS: " + new LIS("CARBOHYDRATE").get());
        System.out.println("LIS: " + new LIS("EMPATHY").get());

        CoinGame coinGame = new CoinGame(new int[]{5, 10, 100, 25}, CoinGame.Player.YOU);
        System.out.println("YOU total: " + coinGame.getMaxTotal(CoinGame.Player.YOU));

    }


}
