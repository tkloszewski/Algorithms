package com.smart.tkl.euler.p97;

import com.smart.tkl.lib.utils.MathUtils;

public class LargeNonMersennePrime {
    
    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        
        long mod = 10000000000L;
        long result = MathUtils.moduloPower(2, 7830457, mod);
        result = MathUtils.moduloMultiply(28433, result, mod);
        result = (result + 1) % mod;
        
        long time2 = System.currentTimeMillis();
    
        System.out.println("Last ten digits: " + result);
        System.out.println("Time in ms: " + (time2 - time1));
    }
    
}
