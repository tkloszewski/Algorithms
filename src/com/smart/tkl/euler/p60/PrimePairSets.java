package com.smart.tkl.euler.p60;

import com.smart.tkl.tree.Node;
import com.smart.tkl.utils.MathUtils;

import java.util.*;

public class PrimePairSets {

    private final int setSize;

    private List<Long> primes;
    private Set<Long> primesSet;

    private Set<PrimePair> primePairs = new HashSet<>();
    private Map<Long, Node<Long>> primePairsTree = new LinkedHashMap<>();
    private List<Node<Long>> minPath;

    private Long minSum = Long.MAX_VALUE;

    public static void main(String[] args) {
        PrimePairSets primePairSets = new PrimePairSets(30000, 5);
        long minSum = primePairSets.generateMinSum();
        System.out.println("Min sum: " + minSum);
    }

    public PrimePairSets(int limit, int setSize) {
        this.setSize = setSize;
        this.primes = MathUtils.generatePrimesUpTo(limit);
        this.primes.remove(2L);
        this.primes.remove(5L);
        this.primesSet = new HashSet<>(primes);
    }

    public Long getMinSum() {
        return this.minSum;
    }

    public List<Node<Long>> getMinPath() {
        return this.minPath;
    }

    public Long generateMinSum() {
        MinPathValueTreeTraversal treeTraversal = new MinPathValueTreeTraversal(this.setSize);
        for(Long prime : this.primes) {
            Node<Long> primeTree = buildPrimeTree(prime, null, null, 0L, 1);
            if(primeTree != null) {
                primePairsTree.put(prime, primeTree);
                List<Node<Long>> path = treeTraversal.visitAll(primeTree);
                if(path != null) {
                    Long sum = getSum(path);
                    if(this.minSum > sum) {
                        this.minSum = sum;
                        this.minPath = path;
                    }
                }
            }
        }
        return this.minSum;
    }

    public void printMinPath() {
        if(this.minPath != null) {
            System.out.println(pathToString(this.minPath));
        }
    }

    public void printAllPaths() {
        MinPathValueTreeTraversal treeTraversal = new MinPathValueTreeTraversal(this.setSize);
        for(Long prime : primePairsTree.keySet()) {
            List<Node<Long>> path = treeTraversal.visitAll(primePairsTree.get(prime));
            if(path != null) {
               System.out.println("Path for prime: " + prime + ":" + pathToString(path));
            }
        }
    }

    public void printPrimeTree() {
        for(Long prime : primePairsTree.keySet()) {
            System.out.println("Tree for " + prime);
            printTree(primePairsTree.get(prime));
        }
    }

    public void printPrimeTree(Long prime) {
        Node<Long> tree = primePairsTree.get(prime);
        if(tree != null) {
            System.out.println("Tree for " + prime);
            printTree(tree);
        }
    }

    private Node<Long> buildPrimeTree(Long prime, List<Long> parentRelationPrimes, Node<Long> parent, long currentSum, int level) {
        int times = this.setSize - level + 1;
        if(currentSum + times * prime > this.minSum) {
            return null;
        }

        Node<Long> current = new Node<>(prime);

        if(parent == null) {
            int primeIndex = primes.indexOf(prime) + 1;
            List<Long> relationPrimes = new ArrayList<>();
            for(int i = primeIndex; i < primes.size(); i++) {
                Long p = primes.get(i);
                if(prime + (times - 1) * p > this.minSum) {
                    break;
                }
                if(isConcatenationPrime(prime, p)) {
                    relationPrimes.add(p);
                }
            }
            if(relationPrimes.size() > 0) {
                List<Node<Long>> children = buildChildren(relationPrimes, current, currentSum, level);
                current.setChildren(children);
            }
        }
        else {
            if(level < this.setSize && parentRelationPrimes != null) {
                List<Long> relationPrimes = new ArrayList<>();
                for(Long parentRelationPrime : parentRelationPrimes) {
                    if(isConcatenationPrime(prime, parentRelationPrime)) {
                        relationPrimes.add(parentRelationPrime);
                    }
                }
                if(relationPrimes.size() > 0) {
                    List<Node<Long>> children = buildChildren(relationPrimes, current, currentSum, level);
                    current.setChildren(children);
                }
            }
        }

        return current;
    }

    private List<Node<Long>> buildChildren(List<Long> relationPrimes, Node<Long> current, long currentSum, int level) {
        List<Node<Long>> children = new ArrayList<>();
        for(int j = 0; j < relationPrimes.size(); j++) {
            Long relationPrime = relationPrimes.get(j);
            List<Long> subList = relationPrimes.subList(j + 1, relationPrimes.size());
            Node<Long> child = buildPrimeTree(relationPrime, subList, current, currentSum + current.getItem(), level + 1);
            if(child != null) {
                children.add(child);
            }
        }
        return children;
    }

    private boolean isConcatenationPrime(long p1, long p2) {
        PrimePair pair = new PrimePair(p1, p2);
        if(primePairs.contains(pair)) {
            return true;
        }
        boolean concatPrime = isConcatenationPrime(p1, p2, this.primesSet);
        if(concatPrime) {
            primePairs.add(pair);
        }
        return concatPrime;
    }

    private Long getSum(List<Node<Long>> path) {
        return path.stream().map(Node::getItem).reduce(0L, Long::sum);
    }

    private String pathToString(List<Node<Long>> path) {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for(Node<Long> node : path) {
            sb.append(node.getItem().toString()).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

    private boolean isConcatenationPrime(long p1, long p2, Set<Long> primes) {
        long c1 = concat(p1, p2);
        long c2 = concat(p2, p1);

        boolean isFirstConcatPrime = primes.contains(c1);
        if(!isFirstConcatPrime) {
            isFirstConcatPrime = MathUtils.isPrime(c1);
            if(isFirstConcatPrime) {
                primes.add(c1);
            }
        }

        boolean isSecondConcatPrime = primes.contains(c2);
        if(!isSecondConcatPrime) {
            isSecondConcatPrime = MathUtils.isPrime(c2);
            if(isSecondConcatPrime) {
                primes.add(c2);
            }
        }

        return isFirstConcatPrime && isSecondConcatPrime;
    }

    private static long concat(long p1, long p2) {
        return Long.parseLong(p1 + "" + p2);
    }

    private void printTree(Node<Long> node) {
        doPrintTree(node, "");
    }

    private void doPrintTree(Node<Long> node, String ident) {
        StringBuilder sb = new StringBuilder(ident + node.getItem().toString() + " => [");
        if(node.getChildren() != null && node.getChildren().size() > 0) {
            for(int i = 0; i < node.getChildren().size(); i++) {
                sb.append(node.getChildren().get(i).getItem().toString());
                if(i < node.getChildren().size() - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("]");
        System.out.println(sb.toString());
        if(node.getChildren() != null) {
            for(Node<Long> child : node.getChildren()) {
                doPrintTree(child, ident + " ");
            }
        }
    }

    private static class PrimePair {
        Long p1;
        Long p2;

        public PrimePair(Long p1, Long p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PrimePair primePair = (PrimePair) o;
            return (p1.equals(primePair.p1) && p2.equals(primePair.p2)) ||
                    (p1.equals(primePair.p2) && p2.equals(primePair.p1));
        }

        @Override
        public int hashCode() {
            return p1.hashCode() + p2.hashCode();
        }
    }
}
