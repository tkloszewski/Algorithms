package com.smart.tkl.tree;

import java.util.List;

public interface TreeTraversal {

    <V extends Comparable<V>> List<Node<V>> visitAll(Node<V> root);
}
