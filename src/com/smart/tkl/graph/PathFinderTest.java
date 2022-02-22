package com.smart.tkl.graph;

public class PathFinderTest {

    public static void main(String[] args) {
        testGraph1();
        testGraph2();
        testGraph3();
    }

    private static void testGraph1() {
        GraphNode a = new GraphNode("A");
        GraphNode b = new GraphNode("B");
        GraphNode c = new GraphNode("C");
        GraphNode d = new GraphNode("D");
        GraphNode e = new GraphNode("E");
        GraphNode f = new GraphNode("F");

        a.getNeighbours().put(b, 2);
        a.getNeighbours().put(c, 5);

        b.getNeighbours().put(c, 8);
        b.getNeighbours().put(d, 7);

        c.getNeighbours().put(d, 2);
        c.getNeighbours().put(e, 4);

        d.getNeighbours().put(f, 1);

        e.getNeighbours().put(d, 6);
        e.getNeighbours().put(f, 3);

        PathFinder pathFinder = new PathFinder(a, f);
        PathResult pathResult = pathFinder.findPath();

        System.out.println("Graph 1 shortest path: " + pathResult);
    }

    private static void testGraph2() {
        GraphNode a = new GraphNode("A");
        GraphNode b = new GraphNode("B");
        GraphNode c = new GraphNode("C");
        GraphNode d = new GraphNode("D");
        GraphNode e = new GraphNode("E");

        a.getNeighbours().put(b, 10);
        b.getNeighbours().put(c, 20);
        c.getNeighbours().put(d, 1);
        c.getNeighbours().put(e, 30);
        d.getNeighbours().put(b, 1);

        PathFinder pathFinder = new PathFinder(a, e);
        PathResult pathResult = pathFinder.findPath();

        System.out.println("Graph 2 shortest path: " + pathResult);
    }

    private static void testGraph3() {
        GraphNode a = new GraphNode("A");
        GraphNode b = new GraphNode("B");
        GraphNode c = new GraphNode("C");
        GraphNode d = new GraphNode("D");
        GraphNode e = new GraphNode("E");
        GraphNode f = new GraphNode("F");
        GraphNode g = new GraphNode("G");
        GraphNode h = new GraphNode("H");
        GraphNode i = new GraphNode("I");

        a.getNeighbours().put(b, 2);
        a.getNeighbours().put(c, 2);

        b.getNeighbours().put(d, 1);

        c.getNeighbours().put(e, 3);

        d.getNeighbours().put(e, 1);
        d.getNeighbours().put(f, 3);

        e.getNeighbours().put(i, 10);
        e.getNeighbours().put(h, 3);
        e.getNeighbours().put(g, 1);

        f.getNeighbours().put(i, 4);

        g.getNeighbours().put(h, 1);

        h.getNeighbours().put(i, 3);


        PathFinder pathFinder = new PathFinder(a, i);
        PathResult pathResult = pathFinder.findPath();

        System.out.println("Graph 3 shortest path: " + pathResult);
    }


}
