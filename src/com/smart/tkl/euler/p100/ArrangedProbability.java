package com.smart.tkl.euler.p100;

public class ArrangedProbability {
    
    private final long limit;
    
    public ArrangedProbability(long limit) {
        this.limit = limit;
    }
    
    public static void main(String[] args) {
        ArrangedProbability arrangedProbability = new ArrangedProbability((long)Math.pow(10, 12));
        System.out.println("First value: " + arrangedProbability.solve());
    }
    
    public Arrangement solve() {
        long x = 15, n = 21;        
        while (n <= limit) {
           long temp = 3 * x + 2 * n - 2;
           n = 4 * x + 3 * n - 3;
           x = temp;
        }
        
        return new Arrangement(x, n);
    }
    
    private static class Arrangement {
        final long x, n;
    
        public Arrangement(long x, long n) {
            this.x = x;
            this.n = n;
        }
    
        @Override
        public String toString() {
            return "Arrangement{" +
                    "x=" + x +
                    ", n=" + n +
                    '}';
        }
    }
    
}
