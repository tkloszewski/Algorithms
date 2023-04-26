package com.smart.tkl.lib.graph.standard;

public interface ShortestPathFinder {

     StandardPath find(StandardVertex source, StandardVertex dest);

     SingleSourceShortestPathResult find(StandardVertex source);

}
