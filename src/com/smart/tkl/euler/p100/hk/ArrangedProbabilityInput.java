package com.smart.tkl.euler.p100.hk;

public class ArrangedProbabilityInput {

    final long P;
    final long Q;
    final long limit;

    public ArrangedProbabilityInput(long p, long q, long limit) {
        P = p;
        Q = q;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return P + " " + Q + " " + limit;
    }
}
