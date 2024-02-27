package com.smart.tkl.euler.p97;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Random;

public class LastDigits {

    private final long mod;
    private final long[][] input;

    public LastDigits(long mod, long[][] input) {
        this.mod = mod;
        this.input = input;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        final int limit = 500000;
        Random random = new Random();
        int bound = 1000000000;
        long[][] input = new long[limit][4];
        long mod = (long)Math.pow(10, 12);
        for(int i = 0; i < limit; i++) {
            for(int j = 0; j < 4; j++) {
                input[i][j] = random.nextInt(bound) + 1;
            }
        }
        long time12 = System.currentTimeMillis();
        System.out.println("Time intermediate: " + (time12 - time1));
        LastDigits lastDigits = new LastDigits(mod, input);
        long result = lastDigits.calc();

        DecimalFormat decimalFormat = new DecimalFormat("000000000000");
        System.out.println(decimalFormat.format(mod));

        long time2 = System.currentTimeMillis();
        System.out.println("Result: " + result);
        System.out.println("Time in ms: " + (time2 - time1));


    }

    public long calc() {
        long result = 0;

        BigInteger modBg = BigInteger.valueOf(mod);

        for(long[] row : input) {
            long a = row[0];
            long b = row[1];
            long c = row[2];
            long d = row[3];

            BigInteger rowResult = BigInteger.valueOf(b).modPow(BigInteger.valueOf(c), modBg);
            rowResult = BigInteger.valueOf(a).multiply(rowResult).add(BigInteger.valueOf(d)).mod(modBg);

            result += rowResult.longValue();
            result = result % mod;
        }

        return result;
    }
}
