package com.smart.tkl.lib.graph.standard.visit;

import com.smart.tkl.lib.graph.standard.StandardVertex;

public interface GraphVisitor {

    VisitorResult visitFrom(StandardVertex source);
}


