package com.smart.tkl.euler.p237;

import com.smart.tkl.lib.linear.MatrixUtils;
import java.util.LinkedList;

public class GridWalkingToursNWSE {

    private final int n;

    public GridWalkingToursNWSE(int n) {
        this.n = n;
    }

    public static void main(String[] args) {
        GridWalkingToursNWSE grid = new GridWalkingToursNWSE(11);
        long count = grid.count();
        System.out.println("Count: " + count);
    }

    public long bruteForceCount() {
        return bruteForce(0, 0, 0, new LinkedList<>(), new int[this.n][4]);
    }

    public long count() {
        long[][] u = {
                {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0},
        };


        long[][] v = {
                {1},
                {1},
                {1},
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
                {0},
                {1}
        };

        u = MatrixUtils.pow(u , this.n - 1);
        v = MatrixUtils.multiply(u, v);

        return v[12][0];
    }

    public long bruteForce(int x, int y, int visited, LinkedList<Coord> path, int[][] grid) {
        long result = 0;
        if(x == this.n - 1 && y == 3) {
            if(visited == 4 * this.n - 1) {
                return 1;
            }
            else {
                return 0;
            }
        }

        grid[x][y] = 1;
        path.add(new Coord(x, y));

        if(x > 0 && grid[x - 1][y] == 0) {
            result += bruteForce(x - 1, y, visited + 1, path, grid);
        }
        if(x < this.n - 1 && grid[x + 1][y] == 0) {
            result += bruteForce(x + 1, y, visited + 1, path, grid);
        }
        if(y > 0 && grid[x][y - 1] == 0) {
            result += bruteForce(x, y - 1, visited + 1, path, grid);
        }
        if(y < 3 && grid[x][y + 1] == 0) {
            result += bruteForce(x, y + 1, visited + 1, path, grid);
        }

        grid[x][y] = 0;
        path.removeLast();

        return result;
    }

    private static class Coord {
        final int x, y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
