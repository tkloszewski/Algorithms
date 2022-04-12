package com.smart.tkl.euler.p94;

public class Triangle {
    
    private final double a;
    private final double b;
    private final double c;
    private final double perimeter;
    private final double area;
    
    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.perimeter = a + b + c;
        this.area = 0.25 * Math.sqrt((a + b + c) * (b + c - a) * (a + c - b) * (a + b - c));
    }
    
    public Triangle(double a, double b, double c, double perimeter, double surface) {
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
