package com.smart.tkl.euler.p98;

import java.util.List;

public class HashedPermutation {
    
    long value;
    long hash;
    List<Integer> digits;
    
    public HashedPermutation(long value, long hash, List<Integer> digits) {
        this.value = value;
        this.hash = hash;
        this.digits = digits;
    }
    
    @Override
    public String toString() {
        return "HashedPermutation{" +
                "value=" + value +
                ", hash=" + hash +
                ", digits=" + digits +
                '}';
    }
}
