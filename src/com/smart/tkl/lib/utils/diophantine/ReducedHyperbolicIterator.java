package com.smart.tkl.lib.utils.diophantine;

import com.smart.tkl.lib.utils.XYPair;
import java.math.BigInteger;
import java.util.Iterator;

public class ReducedHyperbolicIterator implements Iterator<XYPair> {

    private final BigInteger x0;
    private final BigInteger y0;
    private final BigInteger f1, f2, f3;

    private int index = 0;
    private BigInteger x, y;

    /*Iterator for recurrence solutions to hyperbolic equation ax^2 + cy^2 = N
    * where x0 and y0 are base solutions and u0 and v0 are base solutions to
    * Pells equation u^2 - Dv^2 = 4 where D = -4ac */
    public static ReducedHyperbolicIterator ofPositive(BigInteger x0, BigInteger y0, BigInteger u0, BigInteger v0, long a, long c) {
        BigInteger f1 = u0.divide(BigInteger.TWO);
        BigInteger f2 = v0.multiply(BigInteger.valueOf(c));
        BigInteger f3 = v0.multiply(BigInteger.valueOf(a));
        return new ReducedHyperbolicIterator(x0, y0, f1, f2, f3);
    }

    public static ReducedHyperbolicIterator ofNegative(BigInteger x0, BigInteger y0, BigInteger u0, BigInteger v0, long a, long c) {
        BigInteger f1 = u0.divide(BigInteger.TWO);
        BigInteger f2 = v0.multiply(BigInteger.valueOf(c)).negate();
        BigInteger f3 = v0.multiply(BigInteger.valueOf(a)).negate();
        return new ReducedHyperbolicIterator(x0, y0, f1, f2, f3);
    }

    private ReducedHyperbolicIterator(BigInteger x0, BigInteger y0, BigInteger f1, BigInteger f2, BigInteger f3) {
        this.x0 = x0;
        this.y0 = y0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 =  f3;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    /*x = u0/2 * x - v0 * c * y = f1 * x - f2 * y
     * y = a * v0 * x + u0/2 * y = f3 * x + f1 * y
     * */
    @Override
    public XYPair next() {
        XYPair result;
        if(index == 0) {
           x = x0;
           y = y0;
           result = new XYPair(x0, y0);
        }
        else {
           BigInteger nextX = f1.multiply(x).subtract(f2.multiply(y));
           BigInteger nextY = f3.multiply(x).add(f1.multiply(y));
           x = nextX;
           y = nextY;
           result = new XYPair(x, y);
        }

        index++;

        return result;
    }
}
