package com.smart.tkl.euler.p188;

import static com.smart.tkl.lib.utils.MathUtils.GCD;
import static com.smart.tkl.lib.utils.MathUtils.moduloMultiply;
import static com.smart.tkl.lib.utils.MathUtils.moduloPower;
import static com.smart.tkl.lib.utils.MathUtils.phi;

public class HyperExponentiation {
    private final long a;
    private final long b;
    private final long mod;

    public HyperExponentiation(long a, long b, long mod) {
        this.a = a;
        this.b = b;
        this.mod = mod;
    }

    public static void main(String[] args) {
        long a = (long)Math.pow(10, 18);
        long b = (long)Math.pow(10, 18);
        long mod = (long) Math.pow(10, 18);
        long time1 = System.currentTimeMillis();
        HyperExponentiation hyperExponentiation = new HyperExponentiation(a, b, mod);
        long value = hyperExponentiation.hyperExp();
        long time2 = System.currentTimeMillis();
        System.out.println("Value: " + value);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long hyperExp() {
        return hyperExp(this.a, this.b, this.mod);
    }

    private static long hyperExp(long a, long level, long mod) {
        if(a % mod == 0) {
           return 0;
        }
        if(level == 1) {
           return a % mod;
        }
        else if(mod == 1) {
            return 0;
        }
        else {
           long gcd = GCD(a, mod);
           long phi = phi(mod);
           if(gcd == 1) {
               long exp = hyperExp(a, level - 1, phi);
               return moduloPower(a, exp, mod);
           }
           else {
               long exp = hyperExp(a, level - 1, phi);
               return moduloMultiply(moduloPower(a, exp, mod),
                       moduloPower(a, phi, mod), mod) ;
           }
        }
    }
}
