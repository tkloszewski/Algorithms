package com.smart.tkl.euler.p126.hk;

import com.smart.tkl.euler.p124.OrderedRadicals2;
import com.smart.tkl.euler.p96.element.SudokuCell;
import com.smart.tkl.lib.primes.Primes;
import com.smart.tkl.lib.utils.Pair;
import com.smart.tkl.lib.utils.congruence.QuadraticCongruenceSolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CuboidLayersSolver {

    private static int checks1, checks2;
    static double  maxRatio = 0;

    private final int maxCubes;
    private final int[] cuboids;

    public CuboidLayersSolver(int maxCubes) {
        this.maxCubes = maxCubes;
        this.cuboids = new int[maxCubes + 1];
    }

    public static void main(String[] args) {
        int N = 1000000;
        long time1 = System.currentTimeMillis();
        int count = count(N);
        long time2 = System.currentTimeMillis();
        System.out.println("Fast count:  " + count);
        System.out.println("Fast count time in ms: " + (time2 - time1));

        /*System.out.println(count(22));
        System.out.println(count(46));
        System.out.println(count(78));
        System.out.println(count(114));
        System.out.println(count(154));*/

        time1 = System.currentTimeMillis();
        test3(N);
        time2 = System.currentTimeMillis();
        System.out.println("Time in ms: " + (time2 - time1));
    }

    private static int count(int N) {
        if(N % 2 != 0) {
            return 0;
        }
        int count = 0;
        int lMax = (int)(Math.sqrt(N - 2) / 2.0);

        int zeroCount = 0;

        for(int l = 1; l <= lMax; l++) {
            int n = N - 4 * (l - 1) * (l - 2);
            double sqrtDelta1 = Math.sqrt(144 * (l - 1) * (l - 1) + 24  * n);
            int aMin = (int) (sqrtDelta1 / 12.0 - l + 1);

            //System.out.println("aMin: " + aMin);

            int a = (n - 2 - 8 * (l - 1)) / (4 * l);
            int k = 0;
            while (a >= aMin) {
                int nb = n - 4 * (l - 1) * a;
                int f1 = 4 * a + 8 * (l - 1);
                double sqrtDelta2 = Math.sqrt(f1 * f1 + 8 * nb);
                int minB = (int)((-f1 + sqrtDelta2) / 4.0);
                minB = Math.max(1, minB);
                int maxB = (n - 2 * a - 4 * (l - 1) * (a + 1)) / ( 2 * a  + 2 + 4 * (l - 1));
                maxB = Math.min(a, maxB);



                long A = a + 2L * l - 2;
                long F = A * A + nb / 2;
                long sqrtF = (long) Math.sqrt(F);

                int minC = (n - 2 * a * maxB - 4 * (l - 1) * (a + maxB)) / (2 * (a + maxB) + 4 * (l - 1));

                int step = 1;
                if(a % 2 == 0 && (nb / 2) % 2 == 1) {
                    step = 2;
                    if((A  + minC) % 2 == 0) {
                       minC++;
                    }
                }


                /*for(long f = A + minC; f <= sqrtF; f += step) {
                    if(F % f == 0) {
                        long b = F / f - A;
                        if(b <= maxB) {
                      //     count++;
                        }
                    }
                }*/



                for(int b = minB; b <= maxB; b++) {
                    int nc = n - 2 * a * b - 4 * (l - 1) * (a + b);
                    int denominator = 2 * (a + b) + 4 * (l - 1);



                    if(nc % denominator == 0) {
                        int c = nc / denominator;
                        if(c <= b) {
                            count++;
                        }
                    }
                    if(nc < denominator) {
                       System.out.println("nc < denominator!!!");
                    }
                }



                if(k == 0) {
                    a = (n - 4 - 12 * (l - 1)) / (4 * l + 2);
                    k++;
                }
                else if(k == 1) {
                    a = (n - 6 - 16 * (l - 1)) / (4 * l + 4);
                    k++;
                }
                else if(k == 2) {
                    a--;
                    k++;
                }
                else if(k == 3) {
                    a = (n - 8 - 20 * (l - 1)) / (4 * l + 6);
                    k++;
                }
                else {
                  a--;
                }

            }

        }


       // System.out.println("Fast solutions: " + cnt);
        return count;
    }

    public static int count2(int N) {
        int result = 0;
        int lMax = (int)(Math.sqrt(N - 2) / 2.0);

        for(int l = 2; l <= lMax; l++) {
            int k = 2 * (l - 1);
            int u = 3;
            int v = N / 2 - k * u;
            while (v >= u) {
                int V = u * u - 4 * v;
                for(int i = 0; i < 100; i++) {
                    result++;
                    if(result % 2 == 0) {
                        u++;
                    }
                }
                u++;
                v = v - k;
            }
        }

        System.out.println("Result: " + result);

        return result;
    }





    private static int countCubesInLayer(int n, int a, int b, int c) {
        return 2 * (a * b + a * c + b * c) + 4 * (n - 1) * (a + b + c)
                + 4 * (n - 2) * (n - 1);
    }

    private static void test2() {
        int N = 1000000;
        int maxB = (int)Math.sqrt(N);
        for(int b = 1; b <= maxB; b++) {
            for(int c = 1; c <= b; c++) {

            }
        }
    }

    static int test() {
        int  z = 500000;
        int[] r = new int[z + 1];
        int aMax = z / 4;
        int a, b, c, k, x;
        for(a = 1; a <= z; ++a) {
            for (b = 1; b <= a; ++b) {
                if (z < a * b)
                    break;
                for (c = 1; c <= b; ++c) {
                    x = a * b + a * c + b * c;
                    k = 2 * (a + b + c);
                    while (x <= z) {
                        r[x]++;
                        x += k;
                        k += 4;
                    }
                }
            }
        }
        for(k = 1; k <= z; ++k)
            if(r[k] == 10)
                break;

        // System.out.println(Arrays.toString(r));
        System.out.println("k: " + 2 * k);
        System.out.println(r[z]);
        return 2 * k;
    }

    private static void test3(int N) {
        int[] stats = new int[N / 2 + 1];
        FactorStat[] st = new FactorStat[N / 2 + 1];
        int cnt = 0;
        int mxa = (N - 2) / 4;
        for(int a = 1; a <= mxa; a++) {
            int mxb = Math.min(a, (N / 2 - a) / (a + 1));
            for(int b = 1; b <= mxb; b++) {
                int mxc = Math.min(b, (N / 2 - a * b) / (a + b));
                for(int c = 1; c <= mxc; c++) {
                    int f1 = a * b + b * c + a * c;
                    int f2 = a + b + c;
                    if(st[f1] == null) {
                       st[f1] = new FactorStat(a, b, c);
                    }
                    else {

                    }
                    stats[f1]++;
                    cnt++;
                }
            }
        }

        System.out.println("Operations: " + cnt);

        int maxStat = 0;
        int emptyCount = 0;
        for(int i = 3; i < stats.length; i++){
            int stat = stats[i];
            if(stat == 1000) {
                emptyCount++;
                System.out.println("stat: " + i);
            }
        }

        System.out.println("Empty count: " + emptyCount);
        System.out.println("Max stat: " + maxStat);
        System.out.println("Cnt: " + cnt);
    }

    private static class FactorStat {
        int a;
        int b;
        int c;

        int count;
        Map<Integer, Integer> sums = new HashMap<>();

        public FactorStat(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
            count = 1;
            sums.put(a + b + c, 1);
        }

        void incr(int sum) {
            count++;
        }
    }

}
