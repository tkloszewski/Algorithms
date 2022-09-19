package com.smart.tkl.euler.p102;

import com.smart.tkl.euler.p89.RomanNumberConverter;
import com.smart.tkl.euler.p94.Triangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TriangleReader {

    private final String filePath;

    public TriangleReader(String filePath) {
        this.filePath = filePath;
    }

    public List<Triangle2D> readTriangles() throws IOException  {
        List<Triangle2D> result = new ArrayList<>();
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if(parts.length == 6) {
                    result.add(new Triangle2D(
                            Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
                            Integer.parseInt(parts[2]), Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]), Integer.parseInt(parts[5])));
                }
            }
        }

        return result;
    }
}
