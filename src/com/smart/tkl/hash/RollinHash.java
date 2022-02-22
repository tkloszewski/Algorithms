package com.smart.tkl.hash;

public class RollinHash {

    private final int size;
    private final int aConstant = 26;
    private long hash = 0;

    public RollinHash(int size) {
        this.size = size;
    }

    public void append(char c) {
        int chValue = c - 'A' + 1;
        hash = (hash * aConstant + chValue) ;
    }

    public void skip(char c) {
        int subtractValue = (c - 'A' + 1) * (int)Math.pow(aConstant, size - 1);
        hash = (hash - subtractValue) ;
    }

    public long getHash() {
        return this.hash;
    }
}
