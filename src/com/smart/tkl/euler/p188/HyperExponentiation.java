package com.smart.tkl.euler.p188;

import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.utils.MathUtils;

public class HyperExponentiation {
    private final int a;
    private final int b;
    private final long mod;

    public HyperExponentiation(int a, int b, long mod) {
        if(!Primes.isPrime(a)) {
            throw new IllegalArgumentException("Cannot perform hyper exponentiation on non-prime number");
        }
        this.a = a;
        this.b = b;
        this.mod = mod;
    }

    public static void main(String[] args) {
        int a = 1777;
        int b = 1855;
        long mod = 100000000;
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

    private static long hyperExp(int p, int level, long mod) {
        if(level == 1) {
           return p % mod;
        }
        else if(mod == 1) {
            return 0;
        }
        else {
           long phi = MathUtils.phi(mod);
           long exp = hyperExp(p, level - 1, phi);
           return MathUtils.moduloPower(p, exp, mod);
        }
    }
}
