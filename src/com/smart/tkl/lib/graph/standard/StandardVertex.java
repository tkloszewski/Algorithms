package com.smart.tkl.lib.graph.standard;

import java.util.Objects;

public class StandardVertex {

    private final String id;

    public StandardVertex(String id) {
        this.id = id;
    }

    public StandardVertex(int id) {
       this(String.valueOf(id));
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardVertex that = (StandardVertex) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                '}';
    }
}
