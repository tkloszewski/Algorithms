package com.smart.tkl.euler.p167;

import java.util.Arrays;

public class PeriodicUlamSequence {

    final int[] sequence;
    final int period;
    final int startPos;
    final long difference;

    public PeriodicUlamSequence(int[] sequence, int period, int startPos) {
        this.sequence = sequence;
        this.period = period;
        this.startPos = startPos;
        this.difference = sequence[startPos + period] - sequence[startPos];
    }

    public long valueAt(long k) {
        if(k < 1) {
           throw new IndexOutOfBoundsException();
        }
        if(k - 1 < startPos) {
           return sequence[(int)(k - 1)];
        }
        long numOfPeriods = (k - startPos - 1) / period;
        int offset = (int)((k - startPos - 1) % period);
        return sequence[startPos] + numOfPeriods * difference + sequence[startPos + offset] - sequence[startPos];
    }

    @Override
    public String toString() {
        return "PeriodicUlamSequence{" +
                "period=" + period +
                ", startPos=" + startPos +
                ", difference=" + difference +
                '}';
    }

    public String toStringWithSequence() {
        return "PeriodicUlamSequence{" +
                "sequence=" + Arrays.toString(sequence) +
                ", period=" + period +
                ", startPos=" + startPos +
                ", difference=" + difference +
                '}';
    }
}
