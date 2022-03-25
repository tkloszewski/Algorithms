package com.smart.tkl.euler.p89;

import java.io.IOException;
import java.util.List;

public class RomanNumerals {
    
    private static final String PATH = "C:\\Projects\\microservices\\Algorithms\\src\\com\\smart\\tkl\\euler\\p89\\romans.txt";
    
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int diff = new RomanNumerals().solve();
        long time2 = System.currentTimeMillis();
        System.out.println("Number of characters saved: " + diff + " ms: " + (time2 - time1));
    }
    
    public int solve() {
        int totalDiff = 0;
        try {
            List<RomanNumber> romanNumbers = new RomanNumberReader(PATH).readRomanNumbers();
            for(RomanNumber original : romanNumbers) {
                RomanNumber minimized = RomanNumberConverter.toMinimized(original);
                int diff = original.getLiteral().length() - minimized.getLiteral().length();
                totalDiff += diff;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return totalDiff;
    }
    
}
