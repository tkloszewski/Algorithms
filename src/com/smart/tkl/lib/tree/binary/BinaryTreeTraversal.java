package com.smart.tkl.lib.tree.binary;

import java.util.List;

public interface BinaryTreeTraversal<V extends Comparable<V>> {

    List<BiNode<V>> visitAll(BiNode<V> root);
}
