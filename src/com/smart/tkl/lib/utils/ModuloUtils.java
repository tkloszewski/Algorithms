package com.smart.tkl.lib.utils;

import java.util.Optional;

public class ModuloUtils {

    public static long modInv(long a, long mod) {
        Coefficients coefficients = MathUtils.GCDExtended(a ,mod);
        if(coefficients.getGcd() != 1) {
           throw new IllegalArgumentException("No solution!!!");
        }
        long x = coefficients.getX();
        return (x % mod + mod) % mod;
    }

    public static Optional<Long> findModInv(long a, long b) {
        try {
            return Optional.of(modInv(a, b));
        }
        catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
