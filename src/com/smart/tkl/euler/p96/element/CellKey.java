package com.smart.tkl.euler.p96.element;

import java.util.Objects;

public class CellKey {

    public final int i, j;

    public CellKey(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellKey cellKey = (CellKey) o;
        return i == cellKey.i &&
                j == cellKey.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "[" + i + "]["
                 + j + "]";

    }
}
