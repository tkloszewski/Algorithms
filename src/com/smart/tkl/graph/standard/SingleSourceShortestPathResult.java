package com.smart.tkl.graph.standard;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class SingleSourceShortestPathResult {

    private final StandardVertex source;
    private final Map<StandardVertex, StandardPath> pathMap;

    public SingleSourceShortestPathResult(StandardVertex source, Map<StandardVertex, StandardPath> pathMap) {
        this.source = source;
        this.pathMap = pathMap;
    }


    public StandardPath getPath(StandardVertex dest) {
        return pathMap.get(dest);
    }

    public StandardVertex getSource() {
        return source;
    }
}
