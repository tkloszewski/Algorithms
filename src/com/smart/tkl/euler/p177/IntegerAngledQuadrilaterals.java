package com.smart.tkl.euler.p177;

import java.util.HashSet;
import java.util.Set;

public class IntegerAngledQuadrilaterals {

    private static final double EPS = 1e-9;
    private static final Set<Long> HASHED_VALUES = new HashSet<>();
    private static final double[] SINE = new double[181];
    private static final double[] COSINE = new double[181];

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        precompute();
        long count = count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private static void precompute() {
        for(int i = 1; i <= 180; i++) {
            SINE[i] = sin(i);
            COSINE[i] = cos(i);
        }
    }

    public static long count() {
        long result = 0;

        for(int angle1 = 1; angle1 <= 45; angle1++) {
            for(int angle2 = angle1; angle2 <= 180 - 3 * angle1; angle2++) {
                for(int angle3 = angle1; angle3 <= 180 - angle2 - 2 * angle1; angle3++) {
                    int angle8 = 180 - angle1 - angle2 - angle3;
                    for(int angle4 = angle1; angle4 <= 180 - angle1 - angle2 - angle3; angle4++) {
                        int angle5 = 180 - angle2 - angle3 - angle4;
                        double AC = SINE[angle3 + angle4] / SINE[angle5];
                        double AD = SINE[angle3] / SINE[angle8];
                        double DC = Math.sqrt(AC * AC + AD * AD - 2 * AC * AD * COSINE[angle1]);
                        double calculatedAngle = acos((DC * DC + AC * AC - AD * AD) / (2 * DC * AC));
                        if(isWithinLimit(calculatedAngle)) {
                            int angle6 = (int)Math.round(calculatedAngle);
                            if(angle6 < angle1) {
                                break;
                            }
                            int angle7 = angle2 + angle3 - angle6;

                            long hash1 = toHash(angle1, angle2, angle3, angle4);
                            long hash2 = toHash(angle7, angle8, angle1, angle2);
                            long hash3 = toHash(angle5, angle6, angle7, angle8);
                            long hash4 = toHash(angle3, angle4, angle5, angle6);
                            long hash5 = toHash(angle4, angle3, angle2, angle1);
                            long hash6 = toHash(angle6, angle5, angle4, angle3);
                            long hash7 = toHash(angle8, angle7, angle6, angle5);
                            long hash8 = toHash(angle2, angle1, angle8, angle7);

                            long[] hashes = new long[]{hash1, hash2, hash3, hash4, hash5, hash6, hash7, hash8};

                            boolean unique = true;
                            for(long hash : hashes) {
                                if(HASHED_VALUES.contains(hash)) {
                                   unique = false;
                                   break;
                                }
                            }
                            if(unique) {
                               HASHED_VALUES.add(hash1);
                               result++;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    private static long toHash(int a1, int a2, int a3, int a4) {
        return ((long) a1 << 24 ) | ((long) a2 << 16 ) | ((long) a3 << 8 ) | a4;
    }

    private static boolean isWithinLimit(double angle) {
        long rounded = Math.round(angle);
        double diff = Math.abs(angle - rounded);
        return diff <= EPS;
    }

    private static double acos(double cos) {
        return ((Math.acos(cos) / Math.PI) * 180.0);
    }

    private static double sin(int angle) {
        return Math.sin(toRadians(angle));
    }

    private static double cos(int angle) {
        return Math.cos(toRadians(angle));
    }

    private static double toRadians(int angle) {
        return Math.PI * ((double) angle)/ 180.0;
    }
}
