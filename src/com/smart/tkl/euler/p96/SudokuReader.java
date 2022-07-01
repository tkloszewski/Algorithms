package com.smart.tkl.euler.p96;

import com.smart.tkl.euler.p89.RomanNumberConverter;
import com.smart.tkl.euler.p96.element.SudokuSquare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SudokuReader {

    private final String filePath;

    public SudokuReader(String filePath) {
        this.filePath = filePath;
    }

    public List<SudokuSquare> read() throws IOException {
        List<SudokuSquare> result = new ArrayList<>();
        try(FileReader fr = new FileReader(filePath)) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                int[][] square = new int[9][9];
                for(int i = 0; i < 9; i++ ) {
                    line = br.readLine();
                    if(line == null) {
                        throw new IllegalArgumentException("File format is invalid for file: " + filePath);
                    }
                    line = line.trim();
                    if(!line.matches("[0-9]{9}")) {
                        throw new IllegalArgumentException("File format is invalid for line: " + line);
                    }
                    int j = 0;
                    for(char digit : line.toCharArray()) {
                        square[i][j++] = digit - '0';
                    }
                }
                result.add(new SudokuSquare(square));
                line = br.readLine();
            }
        }

        return result;
    }
}
