package com.smart.tkl.euler.p98;

import com.smart.tkl.euler.p89.RomanNumber;
import com.smart.tkl.euler.p89.RomanNumberConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordReader {
    
    private final String filePath;
    
    public WordReader(String path) {
        this.filePath = path;
    }
    
    public List<String> readWords() throws IOException {
        List<String> result = new ArrayList<>(2000);
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim().replaceAll("\"","");
                for(String word : line.split(",")) {
                    result.add(word.trim());
                }
            }
        }
        return result;
    }
}
