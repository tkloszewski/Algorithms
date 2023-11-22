package com.smart.tkl.euler.p181;

import java.util.ArrayList;
import java.util.List;

public class BlackWhiteGroupings {

    private final int maxBlack;
    private final int maxWhite;

    public BlackWhiteGroupings(int maxBlack, int maxWhite) {
        this.maxBlack = maxBlack;
        this.maxWhite = maxWhite;
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int maxBlack = 2;
        int maxWhite = 2;
        BlackWhiteGroupings blackWhiteGroupings = new BlackWhiteGroupings(maxBlack, maxWhite);
        long count = blackWhiteGroupings.count();
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
        System.out.println(getSolution(maxBlack, maxWhite));
    }

    public long count() {
        long[][][] groupings = new long[maxBlack + 1][maxWhite + 1][2];
        groupings[0][0][0] = 1;

        for(int blackInBuildingGroup = 0; blackInBuildingGroup <= maxBlack; blackInBuildingGroup++) {
            for(int whiteInBuildingGroup = 0; whiteInBuildingGroup <= maxWhite; whiteInBuildingGroup++) {
                if (blackInBuildingGroup != 0|| whiteInBuildingGroup != 0) {
                    for(int blackInFinalGroup = 0; blackInFinalGroup <= maxBlack; blackInFinalGroup++) {
                        for(int whiteInFinalGroup = 0; whiteInFinalGroup <= maxWhite; whiteInFinalGroup++) {
                            for(int blackLeft = blackInFinalGroup - blackInBuildingGroup, whiteLeft = whiteInFinalGroup - whiteInBuildingGroup;
                                blackLeft >=0 && whiteLeft >=0 ; blackLeft -= blackInBuildingGroup, whiteLeft -= whiteInBuildingGroup) {
                                groupings[blackInFinalGroup][whiteInFinalGroup][1] += groupings[blackLeft][whiteLeft][0];
                            }
                        }
                    }
                    for(int i = 0; i <= maxBlack; i++) {
                        for(int j = 0; j <= maxWhite; j++) {
                            if (i != 0 || j != 0) {
                                groupings[i][j][0] = groupings[i][j][1];
                            }
                        }
                    }

                }
            }
        }



        return groupings[maxBlack][maxWhite][1];
    }

    static long  getSolution(int black, int white)
    {
        long[][] sol = new long[black + 1][white + 1];
        int b, w, j, k;

        sol[0][0] = 1;

        for (b = 0; b <= black; b++)
        {
            for (w = b > 0 ? 0 : 1; w <= white; w++)
            {
                for (j = b; j <= black; j++)
                {
                    for (k = w; k <= white; k++)
                    {
                        sol[j][k] += sol[j - b][k - w];
                    }
                }
            }
        }

        int h = 3;


        return sol[black][white];
    }


}
