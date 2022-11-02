package com.smart.tkl.euler.p128;

import com.smart.tkl.primes.PrimesSieve;
import com.smart.tkl.utils.MathUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HexagonalTileDiff {

    private final int sequenceLimit;

    public HexagonalTileDiff(int limit) {
        this.sequenceLimit = limit;
    }

    public static void main(String[] args) {
        HexagonalTileDiff hexagonalTileDiff = new HexagonalTileDiff(2000);
        long tileNumber = hexagonalTileDiff.solve();
        System.out.println("Tile number: " + tileNumber);
    }


    public long solve() {
     /*There are only two tiles in a layer k for which a difference between neighbours
       can be 3 primes.
       Differences for first value => 6k - 1, 6k + 1, 12k + 5
       Differences for last value => 6k - 1, 6k + 5, 12k - 7 and they are not in first layer
     * */
        List<Long> elements = new ArrayList<>();
        elements.add(1L);
        int elementsCount = 1;
        int layer = 1;
        while (elementsCount < this.sequenceLimit) {
            long diff1 = 6 * layer - 1;
            long diff2 = 6 * layer + 1;
            long diff3 = 12 * layer + 5;
            long diff4 = 6 * layer + 5;
            long diff5 = 12 * layer - 7;

            if(isPrime(diff1) && isPrime(diff2) && isPrime(diff3)) {
                elementsCount++;
                long firstValue = getFirstValueForLayer(layer);
                elements.add(firstValue);
                if(elementsCount == this.sequenceLimit) {
                    break;
                }
            }

            if(layer != 1 && isPrime(diff1) && isPrime(diff4) && isPrime(diff5)) {
                elementsCount++;
                long lastValue = getLastValueForLayer(layer);
                elements.add(lastValue);
                if(elementsCount == this.sequenceLimit) {
                    break;
                }
            }
            layer++;
        }
        return elements.get(elements.size() - 1);
    }

    private boolean isPrime(long n) {
        return MathUtils.isPrime(n);
    }

    private List<Long> getDifferences(long number) {
        List<Long> diffs = new ArrayList<>(6);
        for(long neighbour : getNeighbours(number)){
            diffs.add(Math.abs(number - neighbour));
        }
        return diffs;
    }

    private List<Long> getNeighbours(long number) {
        if(number == 1) {
            return List.of(2L, 3L, 4L, 5L, 6L, 7L);
        }

        List<Long> result = new ArrayList<>(6);

        int layer = getLayer(number);
        long firstValue = getFirstValueForLayer(layer);
        long lastValue = getLastValueForLayer(layer);

        long previous = getPrevious(number, firstValue, lastValue);
        long next = getNext(number, firstValue, lastValue);

        /*Side is number from 0 to 5*/
        int sideNumber = (int)((number - firstValue) / layer);
        if((number - firstValue) % layer == 0) {
            //this is corner number. 3 outers and 1 inner
            long outerCorner =  getOuterCorner(layer, sideNumber);
            long firstOuterValue = getFirstValueForLayer(layer + 1);
            long lastOuterValue = getLastValueForLayer(layer + 1);
            long prevOuterCorner = getPrevious(outerCorner, firstOuterValue, lastOuterValue);
            long nextOuterCorner = getNext(outerCorner, firstOuterValue, lastOuterValue);
            long innerCorner = getInnerCorner(layer, sideNumber);

            result.add(prevOuterCorner);
            result.add(outerCorner);
            result.add(nextOuterCorner);
            result.add(next);
            result.add(innerCorner);
            result.add(previous);
        }
        else {

            long pos = (number - firstValue) % layer;
            long outer1 = getOuter(layer, sideNumber, pos);
            long outer2 = outer1 + 1;
            long firstInnerValue = getFirstValueForLayer(layer - 1);
            long lastInnerValue = getLastValueForLayer(layer - 1);
            long inner1 = getInner(layer, sideNumber, pos);
            long inner2 = getNext(inner1, firstInnerValue, lastInnerValue);

            result.add(outer1);
            result.add(outer2);
            result.add(next);
            result.add(inner1);
            result.add(inner2);
            result.add(previous);
        }
        return result;
    }

    private long getInner(int layer, int sideNumber, long pos) {
        if(layer == 1) {
            return 1;
        }

        return 2 + 3 * (layer - 1) * (layer - 2) + (layer - 1) * sideNumber + pos - 1;
    }

    private long getOuter(int layer, int sideNumber,long pos) {
        return 2 + 3 * layer * (layer + 1) + (layer + 1) * sideNumber + pos;
    }

    private long getInnerCorner(int layer, int sideNumber) {
        if(layer == 1) {
            return 1;
        }
        return 2 + (layer - 1) * (3 * layer - 6 + sideNumber);
    }

    private long getOuterCorner(int layer, int sideNumber) {
        return 2 + (layer + 1) * (3 * layer + sideNumber);
    }

    private long getPrevious(long number, long firstValue, long lastValue) {
        return number - 1 < firstValue ? lastValue : number - 1;
    }

    private long getNext(long number, long firstValue, long lastValue) {
        return number + 1 > lastValue ? firstValue : number + 1;
    }

    private long getFirstValueForLayer(int layer) {
        return 2L + 3L * layer * (layer - 1);
    }

    private long getLastValueForLayer(int layer) {
        return 1L + 3L * layer * (1 + layer);
    }

    private int getLayer(long n) {
        if(n == 1) {
            return 0;
        }
        double delta = Math.sqrt(9 + 12 * (n - 2));
        return (int)((3.0 + delta) / 6.0);
    }
}
