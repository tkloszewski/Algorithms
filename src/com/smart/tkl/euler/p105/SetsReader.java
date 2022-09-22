package com.smart.tkl.euler.p105;

import com.smart.tkl.euler.p102.Triangle2D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetsReader {

    private final String filePath;

    public SetsReader(String filePath) {
        this.filePath = filePath;
    }

    public List<List<Integer>> readSets() throws IOException {
        List<List<Integer>> result = new ArrayList<>();
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                List<Integer> set = new ArrayList<>();
                for(String part : parts) {
                    set.add(Integer.parseInt(part.trim()));
                }
                if(!set.isEmpty()) {
                    result.add(set);
                }
            }

        }
        return result;
    }
}
