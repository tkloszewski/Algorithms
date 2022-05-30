package com.smart.tkl.graph.standard.visit;

import com.smart.tkl.graph.standard.StandardVertex;

public interface GraphVisitor {

    VisitorResult visitFrom(StandardVertex source);
}


