package com.smart.tkl.euler.p99;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExponentialReader {
    
    private final String filePath;
    
    public ExponentialReader(String path) {
        this.filePath = path;
    }
    
    public List<Exponential> readExponentials() throws IOException {
        List<Exponential> result = new ArrayList<>(1000);        
        
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            int lineNum = 1;
            while ((line = br.readLine()) != null) {
                line = line.trim().replaceAll("\"","");
                String[] parts = line.split(",");
                if(parts.length == 2) {
                   int base = Integer.parseInt(parts[0].trim());
                   int exponent = Integer.parseInt(parts[1].trim());
                   result.add(new Exponential(base, exponent, lineNum)); 
                }
                lineNum++;
            }
        }
        
        return result;
    }
}
