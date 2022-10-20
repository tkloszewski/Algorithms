package com.smart.tkl.euler.p206;

public class ConcealedSquare {

    public static void main(String[] args) {
        long foundNumber = -1;
        long time1 = System.currentTimeMillis();
        int step = 60;
        for(long number = 1010101030; number <= 1389026530; number += step) {
            long square = number * number;
            String sNum = String.valueOf(square);
            if(checkString(sNum)) {
               foundNumber = number;
               break;
            }
            step = step == 60 ? 40 : 60;
        }
        long time2 = System.currentTimeMillis();
        System.out.println("Found number: " + foundNumber);
        System.out.println("Solution took: " + (time2 - time1));
    }

    private static boolean checkString(String s) {
        for(int i = 0; i < 9; i++) {
            int pos = 2 * i + 1;
            if(s.charAt(pos - 1) != '1' + i) {
               return false;
            }
        }
        return true;
    }
}
