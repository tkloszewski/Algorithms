package com.smart.tkl.euler.p99.hk;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ExponentSorter {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));


        int N = scanner.nextInt();
        List<Exponent> exponents = new ArrayList<>(N);
        for(int i = 0; i < N; i++) {
            int B = scanner.nextInt();
            int E = scanner.nextInt();
            exponents.add(new Exponent(B, E));
        }

        int K = scanner.nextInt();

        Collections.sort(exponents);

        Exponent ex = exponents.get(K - 1);

        bufferedWriter.write(String.format("%d %d", ex.getBase(), ex.getExponent()));
        bufferedWriter.newLine();

        scanner.close();
        bufferedWriter.close();
    }
}
