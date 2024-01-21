package com.smart.tkl.euler.p816;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShortestDistanceAmongPoints {

    private final int numberOfPoints; //number of points

    public ShortestDistanceAmongPoints(int n) {
        this.numberOfPoints = n;
    }

    public static void main(String[] args) {
        ShortestDistanceAmongPoints shortestDistanceAmongPoints = new ShortestDistanceAmongPoints(14);
        double shortestDistance = shortestDistanceAmongPoints.findShortestDistance();
        System.out.println("Shortest distance: " + shortestDistance);

        long time1 = System.currentTimeMillis();
        shortestDistance = new ShortestDistanceAmongPoints(2000000).findShortestDistance();
        long time2 = System.currentTimeMillis();
        System.out.println("Shortest distance: " + new DecimalFormat("#########.#########").format(shortestDistance));
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public double findShortestDistance() {
        List<Point> points = listOfSortedPoints();
        return findShortestDistance(0, points.size() - 1, points);
    }

    private double findShortestDistance(int left, int right, List<Point> points) {
           if(right - left <= 3) {
              return shortestDistance(left, right, points);
           }
           int middle = (left + right) / 2;
           double distance1 = findShortestDistance(left, middle, points);
           double distance2 = findShortestDistance(middle, right, points);

           Point middlePoint = points.get(middle);

           double d = Math.min(distance1, distance2);

           List<Point> strip = new ArrayList<>();
           for(int i = left; i <= right; i++) {
               Point point = points.get(i);
               if(point.x - middlePoint.x > d) {
                  break;
               }
               if(Math.abs(point.x - middlePoint.x) < d) {
                  strip.add(point);
               }
           }

           double minStrip = shortestDistanceInStrip(d, strip);
           return Math.min(d, minStrip);
    }

    private double shortestDistanceInStrip(double minDistance, List<Point> strip) {
        double min = minDistance;

        strip.sort(Comparator.comparingLong(p -> p.y));

        for(int i = 1; i < strip.size(); i++) {
            Point p1 = strip.get(i);
            for(int j = i - 1; j >= 0; j--) {
                Point p2 = strip.get(j);
                if(p1.y - p2.y >= minDistance) {
                   break;
                }
                min = Math.min(distance(p1, p2), min);
            }
        }

        return min;
    }

    private double shortestDistance(int from, int to, List<Point> points) {
        double minDistance = Double.MAX_VALUE;
        for(int i = from; i <= to; i++) {
            Point point1 = points.get(i);
            for(int j = i + 1; j <= to; j++) {
                Point point2 = points.get(j);
                double distance = distance(point1, point2);
                if(distance < minDistance) {
                   minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    private List<Point> listOfSortedPoints() {
        List<Point> points = new ArrayList<>(numberOfPoints);

        final long mod = 50515093;
        long s0 = 290797;
        while (points.size() < numberOfPoints) {
            long s1 = ((s0 % mod) * (s0 % mod)) % mod;
            points.add(new Point(s0, s1));
            s0 = ((s1 % mod) * (s1 % mod)) % mod;
        }

        points.sort(Comparator.comparingLong(p -> p.x));

        return points;
    }

    private static double distance(Point p1, Point p2) {
        long dx = p2.x - p1.x;
        long dy = p2.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static class Point {
        final long x, y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }
    }
}
