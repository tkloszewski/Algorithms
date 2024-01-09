package com.smart.tkl.euler.p188;

import com.smart.tkl.lib.utils.MathUtils;

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
           long gcd = MathUtils.GCD(a, mod);
           long phi = MathUtils.phi(mod);
           if(gcd == 1) {
               long exp = hyperExp(a, level - 1, phi);
               return MathUtils.moduloPower(a, exp, mod);
           }
           else {
               long exp = hyperExp(a, level - 1, phi);
               return MathUtils.moduloMultiply(MathUtils.moduloPower(a, exp, mod),
                       MathUtils.moduloPower(a, phi, mod), mod) ;
           }
        }
    }


    public static void test(long a, long b, long m) {
        long result1 = MathUtils.moduloPower(a, b, m);
        long phi = MathUtils.phi(m);

        long pow = phi + b % phi;
        long result2 = MathUtils.moduloPower(a, pow, m);

        System.out.println("Result1: " + result1);
        System.out.println("Result2: " + result2);
    }

}
