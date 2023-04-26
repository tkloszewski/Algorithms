package com.smart.tkl.lib.spoj;

import java.util.Scanner;

public class LastDigit {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfTestCases = Integer.parseInt(scanner.nextLine());
        for(int i = 0; i < numberOfTestCases; i++) {
            String input = scanner.nextLine();
            String[] params = input.split("\\s+");
            int a = Integer.parseInt(params[0]);
            int b = Integer.parseInt(params[1]);
            int lastDigit = 1;
            while (b > 0) {
                if((b & 1) == 1){
                    lastDigit = lastDigit * a % 10;
                }
                a = a * a % 10;
                b >>= 1;
            }
            System.out.println(lastDigit);
        }
    }
}
