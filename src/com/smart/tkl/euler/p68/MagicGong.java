package com.smart.tkl.euler.p68;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MagicGong {

    private int[][] gon;
    private int size;

    public MagicGong(int size) {
        this.gon = new int[size][2];
        this.size = size;
    }

    public static void main(String[] args) {
        MagicGong magicGong = new MagicGong(7);
        List<Gong> gongs = magicGong.findMagicRings();
        System.out.println(gongs.size());
        for(Gong gong: gongs) {
            System.out.println("Magic ring: " + gong);
        }
    }

    public List<Gong> findMagicRings() {
        List<Gong> result = new ArrayList<>(2 * size);

        int maxNum = 2 * size;

        this.gon[0][0] = maxNum;

        int[] nums = new int[maxNum + 1];
        nums[maxNum] = 1;

        int max3ElementsSum = maxNum + maxNum - 1 + maxNum - 2;
        int min3ElementsSum = 1 + 2 + 3;
        int sumOffAllElements = size * (1 + 2 * size);
        int minSum = (sumOffAllElements + min3ElementsSum) / size;
        if(size * minSum < sumOffAllElements + min3ElementsSum) {
            minSum++;
        }
        int maxSum = (sumOffAllElements + max3ElementsSum) / size;

        for(int sum = minSum; sum <= maxSum; sum++) {
            solve(1,  sum, 0, nums, result);
        }

        this.gon[0][1] = maxNum;

        for(int sum = minSum; sum <= maxSum; sum++) {
            solve(1,  sum, 1, nums, result);
        }

        return result;
    }

    private void solve(int ringNum, int sum, int offset, int[] nums, List<Gong> gongs) {
        if(ringNum == 2 * size) {
            int min = 2 * size + 1, ind = 0;
            for (int i = 0; i < size; i++) {
                if (gon[i][0] < min) {
                    min = gon[i][0];
                    ind = i;
                }
            }
            Gong gong = new Gong(size);
            for (int i = ind, k = 0; k < size; i = (i + 1) % size, k++) {
                int[] group = new int[3];
                group[0] = gon[i][0];
                group[1] = gon[i][1];
                group[2] = gon[(i + 1) % size][1];
                gong.addGroup(group);
            }
            gongs.add(gong);

            return;
        }

        boolean sumExceeded = false;

        for(int num = 1; num < 2 * size && !sumExceeded; num++) {
            if(nums[num] == 1) {
                continue;
            }
            nums[num] = 1;

            int idx1 = ringNum / 2;
            int idx2 = (ringNum + offset) % 2;

            this.gon[idx1][idx2] = num;

            boolean buildNextGroup = true;
            for (int j = 0; j < size; j++) {
                if (gon[j][0] != 0 && gon[j][1] != 0 && gon[(j + 1) % size][1] != 0) {
                    int s = gon[j][0] + gon[j][1] + gon[(j + 1) % size][1];
                    if (s != sum) {
                        buildNextGroup = false;
                        if((j + 1) == idx1 && s > sum) {
                            sumExceeded = true;
                        }
                        break;
                    }
                }
            }


            if(buildNextGroup) {
                solve(ringNum + 1, sum, offset, nums, gongs);
            }

            nums[num] = 0;
            this.gon[idx1][idx2] = 0;

        }
    }

    private static class Gong {
        final int size;
        final List<int[]> groups;

        public Gong(int size) {
            this.size = size;
            this.groups = new ArrayList<>(size);
        }

        public void addGroup(int[] group) {
            this.groups.add(group);
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("[");
            for(int i = 0; i < groups.size(); i++) {
                result.append(Arrays.toString(groups.get(i)));
                if (i < groups.size() - 1) {
                    result.append(";");
                }
            }
            result.append("]");
            return result.toString();
        }
    }
}
