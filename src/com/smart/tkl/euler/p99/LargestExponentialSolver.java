package com.smart.tkl.euler.p99;

import java.io.IOException;
import java.util.List;

public class LargestExponentialSolver {
    
    private static final String PATH = "C:\\Projects\\personal\\Algorithms\\src\\com\\smart\\tkl\\euler\\p99\\p099_base_exp.txt";
    
    public static void main(String[] args) {
        LargestExponentialSolver largestExponentialSolver = new LargestExponentialSolver();    
        try {
            Exponential largest = largestExponentialSolver.solve();
            System.out.println("Largest exponential: " + largest);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    public Exponential solve() throws IOException  {
        ExponentialReader exponentialReader = new ExponentialReader(PATH);
        List<Exponential> exponentials = exponentialReader.readExponentials();
        ExponentialComparator exponentialComparator = new ExponentialComparator();
        
        Exponential largestExponential = new Exponential(1, 1, 0);
        
        for(Exponential exponential : exponentials) {
           int compareResult = exponentialComparator.compare(largestExponential, exponential);           
           if(compareResult < 0) {
              largestExponential = exponential; 
           }
        }          
        
        return largestExponential;        
    }    
}
