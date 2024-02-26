package com.smart.tkl.euler.p94;

public class Triangle {
    
    final long a;
    final long b;
    final long c;
    final long perimeter;
    final double area;
    
    public Triangle(long a, long b, long c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.perimeter = a + b + c;
        this.area = 0.25 * Math.sqrt((a + b + c) * (b + c - a) * (a + c - b) * (a + b - c));
    }
    
    public Triangle(long a, long b, long c, long perimeter, double surface) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.perimeter = perimeter;
        this.area = surface;
    }
    
    @Override
    public String toString() {
        return "Triangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", perimeter=" + perimeter +
                ", area=" + area +
                '}';
    }
}
