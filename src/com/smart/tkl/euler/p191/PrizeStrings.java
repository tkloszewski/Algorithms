package com.smart.tkl.euler.p191;

import java.math.BigInteger;

public class PrizeStrings {

    private final int period;
    private final BigInteger[][] states;

    private static final int L0_A0 = 0;
    private static final int L0_A1 = 1;
    private static final int L1_A0 = 2;
    private static final int L1_A1 = 3;
    private static final int L2_A0 = 4;
    private static final int L2_A1 = 5;

    public PrizeStrings(int period) {
        this.period = period;
        this.states = new BigInteger[period + 1][6];
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int period = 30;
        PrizeStrings prizeStrings = new PrizeStrings(period);
        long time2 = System.currentTimeMillis();
        System.out.println(prizeStrings.count());
        System.out.println("Time in ms: " + (time2 - time1));
    }

    /* 0 -> no latency in last day and no absence
     *  1 -> no latency in last day and one absence
     *  2 -> latency in last day and no absence
     *  3 -> latency in last day and one absence
     *  4 -> latency in the last two days and no absence
     *  5 -> latency in the last two days and absence
     *        A         L       O
      L0A0: | LOA1  |  L1A0  | L0A0
      L0A1: |  -    |  L1A1  | L0A1
      L1A0: | LOA1  |  L2A0  | L0A0
      L1A1: |  -    |  L2A1  | L0A1
      L2A0: | LOA1  |   -    | L0A0
      L2A1: |  -    |   -    | L0A1
     * */
    public BigInteger count() {
        BigInteger result = BigInteger.ZERO;

        states[1][L0_A0] = BigInteger.ONE;
        states[1][L0_A1] = BigInteger.ONE;
        states[1][L1_A0] = BigInteger.ONE;
        states[1][L1_A1] = BigInteger.ZERO;
        states[1][L2_A0] = BigInteger.ZERO;
        states[1][L2_A1] = BigInteger.ZERO;

        for(int days = 2; days <= period; days++) {
            states[days][L0_A0] = states[days - 1][L0_A0].add(states[days - 1][L1_A0]).add(states[days - 1][L2_A0]);

            states[days][L0_A1] = states[days - 1][L0_A0].add(states[days - 1][L0_A1])
                    .add(states[days - 1][L1_A0]).add(states[days - 1][L1_A1])
                    .add(states[days - 1][L2_A0]).add(states[days - 1][L2_A1]);

            states[days][L1_A0] = states[days - 1][L0_A0];

            states[days][L1_A1] = states[days - 1][L0_A1];

            states[days][L2_A0] = states[days - 1][L1_A0];

            states[days][L2_A1] = states[days - 1][L1_A1];
        }

        for(int state = 0; state <= 5; state++) {
            result = result.add(states[period][state]);
        }

        return result;
    }
}
