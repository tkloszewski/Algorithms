package com.smart.tkl.euler.p182;

import com.smart.tkl.lib.utils.MathUtils;

public class RSAEncryption {

    private final long p;
    private final long q;
    private final long phi;

    public RSAEncryption(long p, long q) {
        this.p = p;
        this.q = q;
        this.phi = (p - 1) * (q - 1);
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        long p = 1009, q = 3643;
        RSAEncryption rsaEncryption = new RSAEncryption(p, q);
        long sum = rsaEncryption.sumAllKeys();
        long time2 = System.currentTimeMillis();
        System.out.println("Sum: " + sum);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long sumAllKeys() {
        long result = 0;
        long min = p * q;
        for(int e = 2; e < phi; e++) {
            if(MathUtils.GCD(e, phi) == 1) {
                long solutions = (1 + MathUtils.GCD(e - 1, p - 1)) * (1 + MathUtils.GCD(e - 1, q - 1));
                if (solutions == min) {
                    result += e;
                } else if (solutions < min) {
                    min = solutions;
                    result = e;
                }
            }
        }
        return result;
    }
}
