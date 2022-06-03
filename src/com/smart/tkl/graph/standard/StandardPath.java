package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.List;

public class StandardPath {

    protected final List<StandardVertex> path;
    protected final BigDecimal cost;
    protected final PathType type;

    public static final StandardPath NONE = new StandardPath(List.of(), BigDecimal.ZERO, PathType.EMPTY);
    public static final StandardPath NEGATIVE_INFINITY = new StandardPath(List.of(), BigDecimal.ZERO, PathType.NEGATIVE_INFINITY);
    public static final StandardPath INFINITY = new StandardPath(List.of(), BigDecimal.valueOf(Double.MAX_VALUE), PathType.POSITIVE_INFINITY);

    public StandardPath(List<StandardVertex> path, BigDecimal cost) {
        this.path = path;
        this.cost = cost;
        this.type = PathType.NORMAL;
    }

    private StandardPath(List<StandardVertex> path, BigDecimal cost, PathType type) {
        this.path = path;
        this.cost = cost;
        this.type = type;
    }

    @Override
    public String toString() {
        return "StandardPath{" +
                "path=" + path +
                ", cost=" + cost +
                ", type=" + type +
                '}';
    }
}
