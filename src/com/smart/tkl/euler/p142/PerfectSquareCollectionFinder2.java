package com.smart.tkl.euler.p142;

import com.smart.tkl.lib.Stack;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class PerfectSquareCollectionFinder2 {

    private static final long LIMIT = 1000000000000L;
    private static final int HYPOTENUSE_LIMIT = (int) (1000000.0 * Math.sqrt(2));

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int N = 10000;
        printSolutions(N);
        long time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public static void printSolutions(int N) {
        List<PerfectSquare> solutions = getSolutions(N);
        Set<PerfectSquare> unique = new HashSet<>();

        for(PerfectSquare solution : solutions) {
            boolean valid = testSolution(solution);
            if(!valid) {
               System.out.println("Invalid solution: " + solution);
            }
            if(!unique.add(solution)) {
               System.out.println("Not unique: " + solution);
            }
        }

        System.out.println("Solutions size: " + solutions.size());
    }

    private static boolean testSolution(PerfectSquare solution) {
        if(solution.x <= solution.y || solution.x <= solution.z || solution.y <= solution.z) {
            System.out.println("Comparison failure");
            return false;
        }

        if(!isSquare(solution.x + solution.y)) {
           System.out.println("Sum x+y is not square");
           return false;
        }
        if(!isSquare(solution.x - solution.y)) {
            System.out.println("Sum x - y is not square");
            return false;
        }
        if(!isSquare(solution.x + solution.z)) {
            System.out.println("Sum x + x is not square");
            return false;
        }
        if(!isSquare(solution.x - solution.z)) {
            System.out.println("Sum x - z is not square");
            return false;
        }
        if(!isSquare(solution.y + solution.z)) {
            System.out.println("Sum y + z is not square");
            return false;
        }
        if(!isSquare(solution.y - solution.z)) {
            System.out.println("y - z is not square");
            return false;
        }
        return true;
    }

    public static List<PerfectSquare> getSolutions(int N) {
        List<PerfectSquare> solutions = new LinkedList<>();
        Node[] hypotenuses = new Node[HYPOTENUSE_LIMIT + 1];
        Stack<PythagoreanTriple> stack = new Stack<>();
        stack.push(new PythagoreanTriple(3, 4, 5));

        while (!stack.isEmpty()) {
            PythagoreanTriple triple = stack.pop();

            Node last = hypotenuses[triple.h];
            Node newNode = new Node(triple);

            if(last == null) {
                hypotenuses[triple.h] = newNode;
            }
            else {
                newNode.prev = last;
                last.next = newNode;
                hypotenuses[triple.h] = newNode;

                List<PerfectSquare> combinedSolutions = getSolutions(newNode, N);
                solutions.addAll(combinedSolutions);
                if(solutions.size() >= N) {
                    return solutions.subList(0, N);
                }
            }

            PythagoreanTriple triple1 = nextTriple1(triple);
            if(triple1.h <= HYPOTENUSE_LIMIT) {
                stack.push(triple1);
            }
            PythagoreanTriple triple2 = nextTriple2(triple);
            if(triple2.h <= HYPOTENUSE_LIMIT) {
                stack.push(triple2);
            }
            PythagoreanTriple triple3 = nextTriple3(triple);
            if(triple3.h <= HYPOTENUSE_LIMIT) {
                stack.push(triple3);
            }
        }

        List<Node> nodes = new ArrayList<>();
        for(int i = 5; i <= HYPOTENUSE_LIMIT; i++) {
            if(hypotenuses[i] != null) {
               nodes.add(hypotenuses[i]);
            }
        }

        for(int i = 0; i < nodes.size(); i++) {
            Node node1 = nodes.get(i);
            while (node1 != null) {
                int h = node1.triple.h;
                int l1 = node1.triple.l1;
                int l2 = node1.triple.l2;
                for(int k = 2; k * h <= HYPOTENUSE_LIMIT; k++) {
                    int hypotenuse = k * h;
                    Node node2 = hypotenuses[hypotenuse];
                    if(node2 != null) {
                       PythagoreanTriple scaledTriple = new PythagoreanTriple(k * l1, k * l2, hypotenuse);
                       List<PerfectSquare> combinedSolutions = getSolutions(scaledTriple, node2, N);
                       if(!combinedSolutions.isEmpty()) {
                          solutions.addAll(combinedSolutions);
                          if(solutions.size() >= N) {
                             return solutions.subList(0, N);
                          }
                       }
                    }
                }
                node1 = node1.prev;
            }
        }

        return solutions;
    }

    private static List<PerfectSquare> getSolutions(PythagoreanTriple scaledTriple, Node last2, int N) {
        List<PerfectSquare> solutions = new LinkedList<>();

        Node prev = last2;
        while (prev != null) {
            List<PerfectSquare> partialSolutions = getSolutions(scaledTriple, prev.triple, N);
            if(!partialSolutions.isEmpty()) {
                solutions.addAll(partialSolutions);
                if(solutions.size() >= N) {
                    break;
                }
            }
            prev = prev.prev;
        }

        return solutions;
    }

    private static List<PerfectSquare> getSolutions(Node last, int N) {
        List<PerfectSquare> solutions = new LinkedList<>();
        Node prev = last.prev;
        while (prev != null) {
            List<PerfectSquare> partialSolutions = getSolutions(last.triple, prev.triple, N);
            if(!partialSolutions.isEmpty()) {
                solutions.addAll(partialSolutions);
                if(solutions.size() >= N) {
                    break;
                }
            }
            prev = prev.prev;
        }
        return solutions;
    }

    private static List<PerfectSquare> getSolutions(PythagoreanTriple triple1, PythagoreanTriple triple2, int N) {
        List<PerfectSquare> result = new LinkedList<>();

        PythagoreanTriple tripleCF = triple1.l2 > triple2.l2 ? triple1 : triple2;
        PythagoreanTriple tripleDE = triple1.l2 > triple2.l2 ? triple2 : triple1;

        long a = tripleCF.h;
        long c = tripleCF.l2;
        long f = tripleCF.l1;

        long d = tripleDE.l1;
        long e = tripleDE.l2;

        //c > f, c > d, c > e, f < d, f < e
        if(f >= d || f >= e) {
            return result;
        }

        long b2 = c * c - e * e;

        if(!isSquare(b2)) {
           d = tripleDE.l2;
           e = tripleDE.l1;
           b2 = c * c - e * e;
        }

        if(isSquare(b2)) {
            for(long k = 1; k * a <= HYPOTENUSE_LIMIT; k++) {
                long kc = k * c;
                long kd = k * d;

                if((kc + kd) % 2 != 0) {
                   continue;
                }

                long ke = k * e;
                long kf = k * f;

                long x = (kc * kc + kd * kd) / 2;
                long y = (ke * ke + kf * kf) / 2;
                long z = (kc * kc - kd * kd) / 2;

                if(x > LIMIT) {
                    break;
                }

                if(y > z) {
                    result.add(new PerfectSquare(x, y, z));
                    if (result.size() >= N) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    private static boolean isSquare(long n) {
        double sqrt = Math.sqrt(n);
        return sqrt == (long)sqrt;
    }

    private static PythagoreanTriple nextTriple1(PythagoreanTriple triple) {
        int l1 = triple.l1 - 2 * triple.l2 + 2 * triple.h;
        int l2 = 2 * triple.l1 - triple.l2 + 2 * triple.h;
        int h = 2 * triple.l1 - 2 * triple.l2 + 3 * triple.h;
        return new PythagoreanTriple(l1, l2, h);
    }

    private static PythagoreanTriple nextTriple2(PythagoreanTriple triple) {
        int l1 = -triple.l1 + 2 * triple.l2 + 2 * triple.h;
        int l2 = -2 * triple.l1 + triple.l2 + 2 * triple.h;
        int h = -2 * triple.l1 + 2 * triple.l2 + 3 * triple.h;
        return new PythagoreanTriple(l1, l2, h);
    }

    private static PythagoreanTriple nextTriple3(PythagoreanTriple triple) {
        int l1 = triple.l1 + 2 * triple.l2 + 2 * triple.h;
        int l2 = 2 * triple.l1 + triple.l2 + 2 * triple.h;
        int h = 2 * triple.l1 + 2 * triple.l2 + 3 * triple.h;
        return new PythagoreanTriple(l1, l2, h);
    }



    private static class Node {
        PythagoreanTriple triple;
        Node next, prev;

        public Node(PythagoreanTriple triple) {
            this.triple = triple;
        }
    }

    private static class PythagoreanTriple {
        int l1, l2, h;

        public PythagoreanTriple(int l1, int l2, int h) {
            this.l1 = Math.min(l1, l2);
            this.l2 = Math.max(l1, l2);
            this.h = h;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PythagoreanTriple triple = (PythagoreanTriple) o;
            return ((l1 == triple.l1 && l2 == triple.l2) ||
                    (l1 == triple.l2 && l2 == triple.l1)) && h == triple.h;
        }

        @Override
        public int hashCode() {
            return Objects.hash(l1, l2, h);
        }

        @Override
        public String toString() {
            return "{" +
                    "l1=" + l1 +
                    ", l2=" + l2 +
                    ", h=" + h +
                    '}';
        }
    }

}
