package com.smart.tkl.lib.utils.continuedfraction;

import com.smart.tkl.lib.utils.XYPair;
import java.math.BigInteger;
import java.util.Iterator;

public class ConvergentsIterator implements Iterator<XYPair> {

    private final ContinuedFraction continuedFraction;

    private int currentIndex = 0;
    private int periodLength, periodStart;
    private BigInteger prevP, prevQ, P, Q;

    public ConvergentsIterator(ContinuedFraction continuedFraction) {
        this.continuedFraction = continuedFraction;
        this.prevP = BigInteger.ONE;
        this.prevQ = BigInteger.ZERO;
        this.P = BigInteger.valueOf(continuedFraction.getBase());
        this.Q = BigInteger.ONE;
        this.periodLength = continuedFraction.getPeriodSize();
        this.periodStart = continuedFraction.getPeriodIndex();
    }

    @Override
    public boolean hasNext() {
        return continuedFraction.isPeriodic() || currentIndex < continuedFraction.getCoefficients().size() - 1;
    }

    @Override
    public XYPair next() {
        XYPair result = new XYPair(P, Q);
        advance();
        return result;
    }

    private void advance() {
        BigInteger coefficient = BigInteger.valueOf(continuedFraction.getCoefficient(currentIndex));
        BigInteger oldP = P;
        BigInteger oldQ = Q;
        P = P.multiply(coefficient).add(prevP);
        Q = Q.multiply(coefficient).add(prevQ);

        prevP = oldP;
        prevQ = oldQ;

        if(periodStart != -1 && currentIndex >= periodStart) {
            currentIndex = periodStart + (currentIndex + 1 - periodStart) % periodLength;
        }
        else {
            currentIndex++;
        }
    }
}
