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
        System.out.println(hyperExp(2, 3, 10));
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
                long exp;
                if((a == 2 && level <= 4) || (a == 3 && level <= 3) || (level == 2 && a <= 15)) {
                   exp = calcDirect(a, level);
                   if(exp < phi) {
                      return moduloPower(a, exp, mod);
                   }
                }
                exp = hyperExp(a, level - 1, phi);
                return moduloMultiply(moduloPower(a, exp, mod),
                        moduloPower(a, phi, mod), mod);
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

    private static Result hyperExp2(long a, long level, long mod) {
        if(level <= 1) {
           return new Result(moduloPower(a, level, mod), false);
        }
        else if(mod == 1) {
            return new Result(0, true);
        }
        else {
            long phi = phi(mod);
            Result result = hyperExp2(a, level - 1, phi);
            if(result.flag || result.val * Math.log10(a) >= Math.log10(mod)) {
               return new Result(moduloPower(a, result.val + phi, mod), true);
            }
            else {
                return new Result(moduloPower(a, result.val, mod), false);
            }
        }
    }

    private static class Result {
        private final long val;
        private final boolean flag;

        public Result(long val, boolean flag) {
            this.val = val;
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "val=" + val +
                    ", flag=" + flag +
                    '}';
        }
    }
}
