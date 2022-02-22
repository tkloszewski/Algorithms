package com.smart.tkl.euler.p75;

import com.smart.tkl.utils.MathUtils;

public class SinglePythagoreanTripletCounter {

    private final int limit;

    public static void main(String[] args) {
        SinglePythagoreanTripletCounter counter = new SinglePythagoreanTripletCounter(1500000);
        System.out.println("Count perimeters: " + counter.countPerimeters());
        System.out.println("Count areas: " + counter.countAreas());
    }

    public SinglePythagoreanTripletCounter(int limit) {
        this.limit = limit;
    }

    public int countPerimeters() {
        int result = 0;
        int[] perimeters = new int[limit + 1];

        for(int m = 2; m < (int)Math.sqrt(limit >> 1); m++) {
            for(int n = 1; n < m; n++) {
                if((n + m) % 2 == 1 && MathUtils.GCD(m ,n) == 1) {
                    int a = m * m - n * n;
                    int b = 2 * m * n;
                    int c = m * m + n * n;
                    int perimeter = a + b + c;
                    while (perimeter <= limit) {
                        perimeters[perimeter]++;
                        if(perimeters[perimeter] == 1){
                           result++;
                        }
                        if(perimeters[perimeter] == 2) {
                           result--;
                        }
                        perimeter += (a + b + c);
                    }
                }
            }
        }
        return result;
    }

    public int countAreas() {
        int result = 0;
        int[] areas = new int[limit + 1];

        for(int m = 2; m < (int)Math.sqrt(limit); m++) {
            for(int n = 1; n < m; n++) {
                if((n + m) % 2 == 1 && MathUtils.GCD(m ,n) == 1) {
                    int a = m * m - n * n;
                    int b = 2 * m * n;
                    int area = (a * b) / 2 ;

                    int inc = 1, k = 1;
                    if((a * b) % 2 == 1) {
                        area = 2 * area;
                        k = 2;
                        inc = 2;
                    }
                    while (area <= limit && area > 0) {
                        areas[area]++;
                        if(areas[area] == 1){
                            result++;
                        }
                        if(areas[area] == 2) {
                            result--;
                        }
                        k += inc;
                        area = (k * k) * (a * b) / 2;
                    }
                }
            }
        }
        return result;
    }



}
