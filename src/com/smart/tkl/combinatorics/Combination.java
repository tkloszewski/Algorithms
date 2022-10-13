package com.smart.tkl.combinatorics;

import java.util.*;

public class Combination<T> {

    private Set<T> data;

    public Combination (T[] table) {
        data = new LinkedHashSet<>();
        data.addAll(Arrays.asList(table.clone()));
    }

    public Combination (T[] table, int endIdx) {
        data = new HashSet<>();
        data.addAll(Arrays.asList(table).subList(0, endIdx + 1));
    }

    public Set<T> getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combination<?> that = (Combination<?>) o;
        return data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(T item : data) {
            sb.append(item).append(" ");
        }
        return sb.toString();
    }
}
