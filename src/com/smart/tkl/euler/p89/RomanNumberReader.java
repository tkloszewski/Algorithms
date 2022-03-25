package com.smart.tkl.euler.p89;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RomanNumberReader {
    
    private final String filePath;
    
    public RomanNumberReader(String path) {
        this.filePath = path;
    }
    
    public List<RomanNumber> readRomanNumbers() throws IOException {
        List<RomanNumber> result = new ArrayList<>(1000);
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                result.add(RomanNumberConverter.toRomanNumber(line.trim()));
            }
        }
        return result;
    }
}
