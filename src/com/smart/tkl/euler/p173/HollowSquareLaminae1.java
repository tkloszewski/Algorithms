package com.smart.tkl.euler.p173;

public class HollowSquareLaminae1 {

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long limit = 1000000000000L;
      //  long count = countAll(limit);
        long count = countFast(limit);
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static long countAll(long limit) {
        long oddCount = countOddSize(limit);
        long evenCount = countEvenSize(limit);

        System.out.println("Count odd: " + oddCount);
        System.out.println("Count even: " + evenCount);

        return oddCount + evenCount;
    }

    private static long countOddSize(long limit) {
        return count(8, limit);
    }

    private static long countEvenSize(long limit) {
        return count(12, limit);
    }

    private static long count(long initialTiles, long limit) {
        long result = 0;
        for(long layer = 1, totalTiles = initialTiles, tilesInLayer = initialTiles; tilesInLayer <= limit; layer++, tilesInLayer += 8, totalTiles += tilesInLayer) {
            if(totalTiles <= limit) {
                result += layer;
            }
            else {
                long maxNumOfLayers = getMaxNumOfLayers(tilesInLayer, limit);
                result += maxNumOfLayers;
            }
        }
        return result;
    }

    private static long getMaxNumOfLayers(long tilesInLayer, long limit) {
      double b = tilesInLayer + 4;
      double sqrtDelta = Math.sqrt(b * b - 16 * limit);
      return (long) ((b - sqrtDelta) / 8.0);
    }

    public static long countFast(long N) {
        long result = 0;
        long tMax = (long)((Math.sqrt(1 + N) - 1) / 2);


        for(long t = 1; t <= tMax; t++) {
            long count = N / (4 * t) - t;
            result += count;
        }

        return result;
    }

}
