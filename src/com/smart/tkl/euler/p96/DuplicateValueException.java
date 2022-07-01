package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p96.element.CellKey;

public class DuplicateValueException extends RuntimeException {

    private final CellKey cellKey;
    private final Integer value;

    public DuplicateValueException(CellKey cellKey, Integer value) {
        this.cellKey = cellKey;
        this.value = value;
    }

    public CellKey getCellKey() {
        return cellKey;
    }

    public Integer getValue() {
        return value;
    }
}
