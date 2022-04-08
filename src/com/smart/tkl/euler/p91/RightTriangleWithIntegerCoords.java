package com.smart.tkl.euler.p91;

import com.smart.tkl.utils.MathUtils;

public class RightTriangleWithIntegerCoords {
    
    private int limit;
    
    public RightTriangleWithIntegerCoords(int limit) {
        this.limit = limit;
    }
    
    public static void main(String[] args) {
        RightTriangleWithIntegerCoords triangleWithIntegerCoords = new RightTriangleWithIntegerCoords(50);
        int count = triangleWithIntegerCoords.count();
        int fastCount = triangleWithIntegerCoords.fastCount(); 
        System.out.println("Count: " + count);
        System.out.println("Fast count: " + fastCount);        
    }
    
    public int count() {
        int result = 0;
        for(int x1 = 0; x1 <= limit; x1++) {
            for(int y1 = 0; y1 <= limit; y1++) {                
                if (x1 != 0 || y1 != 0) {
                    for(int x2 = 0; x2 <= limit; x2++) {
                        for(int y2 = 0; y2 <= limit; y2++) {                            
                            if((x2 != 0 || y2 != 0) && (x1 != x2 || y1 != y2)) {
                               int a = x1 * x1 + y1 * y1;
                               int b = x2 * x2 + y2 * y2;
                               int c = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
                               if(a + b == c || a + c == b || b + c == a) {
                                 result++;  
                               }
                            }
                        }
                    }
                }
            }
        }
        return result / 2;
    }
    
    public int fastCount() {
        int result = 3 * limit * limit;
        for (int x = 1; x <= limit; x++) {
            for (int y = 1; y <= limit; y++) {
                int fact = (int)MathUtils.GCD(x, y);
                int downCount = Math.min((limit - x) * fact / y, y * fact / x);
                int upCount = Math.min(x * fact / y, (limit - y) * fact / x);                
                result += downCount;
                result += upCount;
            }
        }        
        
        return result;
    }
}
