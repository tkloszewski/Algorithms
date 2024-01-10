package com.smart.tkl.euler.p188;

import static com.smart.tkl.lib.utils.MathUtils.GCD;
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
        System.out.println(hyperExp(4, 2, 1000L));
    }

    public long hyperExp() {
        return hyperExp(this.a, this.b, this.mod);
    }

    private static long hyperExp(long a, long level, long mod) {
        if(a == 1) {
            return mod == 1 ? 0 : 1;
        }
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
                boolean smaller = checkIfSmaller(a, level - 1, phi);
                if(smaller) {
                   long b = calcDirect(a, level - 1);
                   return moduloPower(a, b, mod);
                }
                else {
                    long b = hyperExp(a, level - 1, phi);
                    return moduloPower(a, b + phi, mod);
                }
            }
        }
    }

    private static long calcDirect(long a, long level) {
        long result = a;
        for(int i = 2; i <= level; i++) {
            result = (long)Math.pow(a, result);
        }
        return result;
    }

    private static boolean checkIfSmaller(long a, long level, long phi) {
        if(a >= phi) {
           return false;
        }
        double left = Math.log10(a);
        double right = Math.log10(phi);

        while (level > 1) {
            left *= a;
            right = Math.log10(right);

            if(left >= right) {
                return false;
            }

            level--;
        }

        return true;
    }
}
