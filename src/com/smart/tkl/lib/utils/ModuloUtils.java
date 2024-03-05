package com.smart.tkl.lib.utils;

public class ModuloUtils {

    public static long modInv(long a, long mod) {
        Coefficients coefficients = MathUtils.GCDExtended(a ,mod);
        if(coefficients.getGcd() != 1) {
           throw new IllegalArgumentException("No solution!!!");
        }
        long x = coefficients.getX();
        return (x % mod + mod) % mod;
    }
}
