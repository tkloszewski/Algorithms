package com.smart.tkl.euler.p195;

import com.smart.tkl.lib.utils.MathUtils;

public class SixtyDegreesIntegerTriangles {

    private final int inscribedRadiusLimit;

    public SixtyDegreesIntegerTriangles(int inscribedRadiusLimit) {
        this.inscribedRadiusLimit = inscribedRadiusLimit;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        SixtyDegreesIntegerTriangles sixtyDegreesIntegerTriangles = new SixtyDegreesIntegerTriangles(1053779);
        long count = sixtyDegreesIntegerTriangles.countTriangles();
        long time2 = System.currentTimeMillis();

        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long countTriangles() {
        long result = 0;

        int rLimit1 = (int)((double)inscribedRadiusLimit * 6.0 / Math.sqrt(3.0));
        int rLimit2 = (int)((double)inscribedRadiusLimit * 2.0 / Math.sqrt(3.0));

        for(long n = 1; n * n < rLimit1; n++) {
            if(n % 3 == 0) {
               continue;
            }
            for(long m = n + 3; m * n <= rLimit1; m += 3) {
                if(m % 2 == 0 && n % 2 == 0) {
                   continue;
                }
                if(MathUtils.GCD(m, n) == 1) {
                    long k = rLimit1 / (m * n);
                    result += k;
                }
            }
        }

        for(long n = 1; n * n < rLimit2; n++) {
            long step = n % 2 == 0 ? 2 : 1;
            for(long m = n + 1; m * n <= rLimit2; m += step) {
                if((m - n) % 3 != 0 && (n == 1 || MathUtils.GCD(m, n) == 1)) {
                    long k = rLimit2 / (m * n);
                    result += k;
                }
            }
        }

        return result;
    }


}
