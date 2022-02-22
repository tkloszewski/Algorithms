package com.smart.tkl.tree.binary;

import java.util.List;

public interface BinaryTreeTraversal<V extends Comparable<V>> {

    List<BiNode<V>> visitAll(BiNode<V> root);
}
