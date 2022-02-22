package com.smart.tkl.tree.binary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BinarySearchTree<V extends Comparable<V>> implements BinaryTree<V>  {

    protected BiNode<V> root;
    protected int size;

    private final static TraversalOrder DEFAULT_TRAVERSAL_ORDER = TraversalOrder.IN_ORDER_DEPTH_FIRST;

    private final Map<TraversalOrder, BinaryTreeTraversal<V>> traversalMap = new HashMap<TraversalOrder, BinaryTreeTraversal<V>>(){{
        put(TraversalOrder.IN_ORDER_DEPTH_FIRST, new InOrderTreeTraversal<>());
        put(TraversalOrder.PRE_ORDER_DEPTH_FIRST, new PreOrderTreeTraversal<>());
        put(TraversalOrder.POST_ORDER_DEPTH_FIRST, new PostOrderTreeTraversal<>());
        put(TraversalOrder.BREADTH_FIRST, new BreadthFirstBinaryTreeTraversal<>());
    }};

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    public BinarySearchTree(BiNode<V> root) {
        this.root = root;
        this.size = 1;
    }

    @Override
    public BiNode<V> getRoot() {
        return this.root;
    }

    @Override
    public BiNode<V> insert(V item) {
        BiNode<V> newNode = new BiNode<>(item);
        insert(newNode);
        return newNode;
    }

    @Override
    public void insertAfter(BiNode<V> node, BiNode<V> newNode) {
        if(node.right == null) {
            node.right = newNode;
            newNode.parent = node;
        }
        else {
            BiNode<V> successor = getSuccessor(node);
            successor.left = newNode;
            newNode.parent = successor;
        }
        size++;
    }

    @Override
    public void insertBefore(BiNode<V> node, BiNode<V> newNode) {
        if(node.left == null) {
            node.left = newNode;
            newNode.parent = node;
        }
        else {
            BiNode<V> predecessor = getPredecessor(node.getLeft());
            predecessor.right = newNode;
            newNode.parent = predecessor;
        }
        size++;
    }

    @Override
    public void delete(V item) {
        if(this.root == null) {
           return;
        }
        BiNode<V> found = find(item);
        if(found != null) {
            delete(found);
        }
    }

    @Override
    public void delete(BiNode<V> node) {
        if(node.left == null) {
           subtreeShift(node, node.right);
        }
        else if(node.right == null) {
            subtreeShift(node, node.left);
        }
        else {
            BiNode<V> successor = getSuccessor(node);
            if (successor != null) {
                swapItem(node, successor);
                delete(successor);
            }
        }

        size--;
    }

    @Override
    public List<BiNode<V>> getAll() {
        BinaryTreeTraversal<V> treeTraversal = traversalMap.get(DEFAULT_TRAVERSAL_ORDER);
        return treeTraversal.visitAll(this.root);
    }

    @Override
    public List<BiNode<V>> getAll(TraversalOrder order) {
        BinaryTreeTraversal<V> treeTraversal = traversalMap.get(order);
        return treeTraversal.visitAll(this.root);
    }

    @Override
    public BiNode<V> getSuccessor(BiNode<V> node) {
        BiNode<V> result = null;
        if(node.right != null) {
            result = getFirst(node.right);
        }
        else {
            if(node.parent != null) {
                BiNode<V> child = node;
                BiNode<V> parent = child.parent;
                while (parent != null && parent.left != child) {
                    child = parent;
                    parent = parent.parent;
                }
                result = parent;
            }
        }
        return result;
    }

    @Override
    public BiNode<V> getPredecessor(BiNode<V> node) {
        BiNode<V> result = null;
        if(node.left != null) {
            result = getLast(node.left);
        }
        else {
            if(node.parent != null) {
                BiNode<V> child = node;
                BiNode<V> parent = child.parent;
                while (parent != null && parent.right != child) {
                    child = parent;
                    parent = parent.parent;
                }
                result = parent;
            }
        }
        return result;
    }

    @Override
    public BiNode<V> find(V key) {
        return find(this.root, key);
    }

    @Override
    public BiNode<V> findAt(int i) {
        return findAt(this.root, i);
    }

    @Override
    public BiNode<V> getFirst() {
        return getFirst(this.root);
    }

    @Override
    public BiNode<V> getLast() {
        return getLast(this.root);
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0 || this.root == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int getMaxLevel() {
        return treeHeight(this.root);
    }

    protected void insert(BiNode<V> node) {
        BiNode<V> current = this.root;
        BiNode<V> found = null;
        while (current != null) {
            found = current;
            int compareResult = node.item.compareTo(current.item);
            if(compareResult < 0) {
                current = current.left;
            }
            else {
                current = current.right;
            }
        }
        node.parent = found;
        if(found == null) {
            this.root = node;
        }
        else if(node.item.compareTo(found.item) < 0){
            found.left = node;
        }
        else {
            found.right = node;
        }
        size++;
    }

    protected BiNode<V> find(BiNode<V> node, V key) {
        if(node == null) {
            return null;
        }
        int compareResult = key.compareTo(node.item);
        if(compareResult == 0) {
            return node;
        }
        if(compareResult < 0) {
            return find(node.left, key);
        }
        return find(node.right, key);
    }

    protected BiNode<V> findAt(BiNode<V> node, int i) {
        if(node == null) {
            return null;
        }
        int leftSize = countNodes(node.left);
        if(i == leftSize) {
            return node;
        }
        if(i < leftSize) {
            return findAt(node.left, i - 1);
        }
        return findAt(node.right, i - leftSize - 1);
    }

    protected void subtreeShift(BiNode<V> toBeReplaced, BiNode<V> toMoveUp) {
        if(toBeReplaced.parent == null) {
            this.root = toMoveUp;
        }
        else if(toBeReplaced == toBeReplaced.parent.left) {
            toBeReplaced.parent.left = toMoveUp;
        }
        else {
            toBeReplaced.parent.right = toMoveUp;
        }
        if(toMoveUp != null) {
            toMoveUp.parent = toBeReplaced.parent;
        }
        toBeReplaced.parent = null;
        toBeReplaced.left = null;
        toBeReplaced.right = null;
    }

    protected BiNode<V> getFirst(BiNode<V> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    protected BiNode<V> getLast(BiNode<V> node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    protected int countNodes(BiNode<V> node) {
        if(node == null) {
            return 0;
        }
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }

    protected int treeHeight(BiNode<V> node) {
        if(node == null) {
            return -1;
        }

        int leftHeight, rightHeight;

        if(node.left != null && node.left.height != null) {
            leftHeight = node.left.height;
        }
        else {
            leftHeight = treeHeight(node.left);
        }

        if(node.right != null && node.right.height != null) {
            rightHeight = node.right.height;
        }
        else {
            rightHeight = treeHeight(node.right);
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }

    protected void swapItem(BiNode<V> node1, BiNode<V> node2) {
        V nodeItem = node1.item;
        node1.item = node2.item;
        node2.item = nodeItem;
    }
}
