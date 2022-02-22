package com.smart.tkl.euler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixFileReader {

    private final String filePath;

    public MatrixFileReader(String filePath) {
        this.filePath = filePath;
    }

    public int[][] readMatrix() throws IOException {
        List<String> lines = new ArrayList<>(1000);
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        int[][] result = new int[lines.size()][];
        for(int i = 0; i < lines.size(); i++) {
            String[] nums = lines.get(i).split(",");
            result[i] = new int[nums.length];
            for(int k = 0; k < nums.length; k++) {
                result[i][k] = Integer.parseInt(nums[k]);
            }
        }
        return result;
    }
}
