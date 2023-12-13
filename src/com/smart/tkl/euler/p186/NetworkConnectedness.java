package com.smart.tkl.euler.p186;

import com.smart.tkl.lib.Stack;
import java.util.LinkedHashSet;

public class NetworkConnectedness {

    private final int pmNumber;
    private final int threshold;
    private final LinkedHashSet<Integer>[] dials = new LinkedHashSet[1000000];
    private final boolean[] visited = new boolean[1000000];

    private final NumberNode[] nodes = new NumberNode[1000000];

    public static void main(String[] args) {
        int pmNumber = 524287;
        double percentage = 0.99;

        NetworkConnectedness networkConnectedness = new NetworkConnectedness(pmNumber, percentage);

        long time1 = System.currentTimeMillis();
        int calls = networkConnectedness.getCalls();
        long time2 = System.currentTimeMillis();
        System.out.println("Calls1: " + calls);
        System.out.println("Time in ms: " + (time2 - time1));

        time1 = System.currentTimeMillis();
        int calls2 = networkConnectedness.getCalls2();
        time2 = System.currentTimeMillis();
        System.out.println("Calls2: " + calls2);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public NetworkConnectedness(int number, double percentage) {
        this.pmNumber = number;
        this.threshold = (int)(1000000.0 * percentage);
    }

    public int getCalls() {
        int[] buffer = initiateBuffer();
        int calls = 0, k = 1;
        int friends = 0;
        while (friends < threshold) {
            int n1 = getAndSetValue(buffer, k);
            int n2 = getAndSetValue(buffer, k + 1);
            if(n1 == n2) {
                k += 2;
                continue;
            }

            if(dials[n1] == null) {
                dials[n1] = new LinkedHashSet<>();
            }
            if(dials[n2] == null) {
                dials[n2] = new LinkedHashSet<>();
            }

            if((n1 == pmNumber || n2 == pmNumber) && !visited[pmNumber]) {
                dials[n1].add(n2);
                dials[n2].add(n1);
                int visitedCount = n1 == pmNumber ? visit(n2) : visit(n1);
                visited[pmNumber] = true;

                friends += visitedCount;
            }
            else {
                dials[n1].add(n2);
                dials[n2].add(n1);
                int visitedCount;
                if(visited[pmNumber]) {
                   if(visited[n1] && !visited[n2]) {
                      visitedCount = visit(n2);
                      friends += visitedCount;
                   }
                   else if(!visited[n1] && visited[n2]) {
                       visitedCount = visit(n1);
                       friends += visitedCount;
                   }
                }
            }

            k += 2;
            calls++;
        }

        return calls;
    }

    public int getCalls2() {
        int[] buffer = initiateBuffer();
        int calls = 0;
        int friends = 0;
        for(int k = 1; friends < threshold; k += 2) {
            int n1 = getAndSetValue(buffer, k);
            int n2 = getAndSetValue(buffer, k + 1);
            if(n1 == n2) {
                continue;
            }
            boolean joined = join(n1, n2);
            if(joined) {
               NumberNode numberNode = findRepresentative(pmNumber);
               friends = numberNode.count;
            }
            calls++;
        }
        return calls;
    }

    private int visit(int number) {
        int visitedCount = 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(number);

        while (!stack.isEmpty()) {
            int n = stack.pop();
            if(!visited[n]) {
               visited[n] = true;
               visitedCount++;
               for(int friend : dials[n]) {
                   if(!visited[friend]) {
                       stack.push(friend);
                   }
               }
            }
        }

        return visitedCount;
    }

    private static int getAndSetValue(int[] buffer, int k) {
        if(k >= 1 && k <= 55) {
            int newValue = getInitialValue(k);
            buffer[k - 1] = newValue;
            return newValue;
        }
        int pos1 = (k - 1) % 55;
        int pos2 = pos1 - 24;
        if(pos2 < 0) {
            pos2 = 55 + pos2;
        }
        int newValue = (buffer[pos1] + buffer[pos2]) % 1000000;
        buffer[pos1] = newValue;
        return newValue;
    }

    private static int[] initiateBuffer() {
        int[] buffer = new int[55];
        for(int k = 1; k <= 55; k++) {
            buffer[k - 1] = getInitialValue(k);
        }
        return buffer;
    }

    private static int getInitialValue(int k) {
        long v = 100003 - 200003L * k + 300007L * k * k * k;
        return (int) (v % 1000000);
    }

    private boolean join(int number1, int number2) {
        NumberNode rep1 = findRepresentative(number1);
        NumberNode rep2 = findRepresentative(number2);
        if(rep1.number == rep2.number) {
           return false;
        }

        if(rep1.count > rep2.count) {
            rep2.parent = rep1;
            rep1.count += rep2.count;
        }
        else {
           rep1.parent = rep2;
           rep2.count += rep1.count;
        }

        return true;
    }

    private NumberNode findRepresentative(int number) {
        NumberNode node = this.nodes[number];
        if(node == null) {
           node = new NumberNode(number);
           nodes[number] = node;
           return node;
        }
        NumberNode rep = node;
        NumberNode parent = node.parent;
        if(parent != null) {
           while (parent != null) {
                 rep = parent;
                 parent = parent.parent;
           }
           node.parent = rep;
        }
        return rep;
    }

    private static class NumberNode {
        private NumberNode parent;
        private int number;
        private int count;

        public NumberNode(int number) {
            this.number = number;
            this.count = 1;
        }
    }

}
