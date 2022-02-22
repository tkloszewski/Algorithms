package com.smart.tkl.graph.triangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TriangleFileReader {

    private final String filePath;

    public TriangleFileReader(String filePath) {
        this.filePath = filePath;
    }

    public int[] readTrianglesNum() {
        List<Integer> list = new ArrayList<>(1000);
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                for(String part : line.split("\\s+")) {
                    Integer num = Integer.parseInt(part.trim());
                    list.add(num);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] result = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    public int[][] readTrianglesNum2D() {
        List<String> lines = new ArrayList<>(1000);
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] result = new int[lines.size()][];
        for(int i = 0; i < lines.size(); i++) {
            String[] nums = lines.get(i).split("\\s+");
            result[i] = new int[nums.length];
            for(int k = 0; k < nums.length; k++) {
                result[i][k] = Integer.parseInt(nums[k]);
            }
        }
        return result;
    }


}
