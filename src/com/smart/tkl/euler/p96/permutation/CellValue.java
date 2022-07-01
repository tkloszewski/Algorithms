package com.smart.tkl.euler.p96.permutation;

import com.smart.tkl.euler.p96.element.CellKey;

public class CellValue {

    final CellKey key;
    final int value;

    public CellValue(CellKey key, int value) {
        this.key = key;
        this.value = value;
    }

    public CellKey getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "CellValue{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
