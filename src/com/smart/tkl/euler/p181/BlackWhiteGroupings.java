package com.smart.tkl.euler.p181;

public class BlackWhiteGroupings {

    private final int maxBlack;
    private final int maxWhite;
    private final long[][][] groupings;

    public BlackWhiteGroupings(int maxBlack, int maxWhite) {
        this.maxBlack = maxBlack;
        this.maxWhite = maxWhite;
        this.groupings = initiateGroupings();
    }

    public static void main(String[] args) {
        long time1 = System.currentTimeMillis();
        int maxBlack = 60;
        int maxWhite = 40;
        BlackWhiteGroupings blackWhiteGroupings = new BlackWhiteGroupings(maxBlack, maxWhite);
        long count = blackWhiteGroupings.count(maxBlack, maxWhite);
        long time2 = System.currentTimeMillis();
        System.out.println("Count: " + count);
        System.out.println("Time in ms: " + (time2 - time1));
    }

    public long count(int maxBlack, int maxWhite) {
        return groupings[maxBlack][maxWhite][1];
    }

    private long[][][] initiateGroupings() {
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
        return groupings;
    }
}
