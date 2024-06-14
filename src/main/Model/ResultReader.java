package main.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The ResultReader class is responsible for reading player results from a file.
 */
public class ResultReader {

    /**
     * Reads all results from the results file and returns them as an ArrayList of Result objects.
     * 
     * @return an ArrayList of Result objects containing all player results
     * @throws FileNotFoundException if the results file cannot be found
     */
    public static ArrayList<Result> getAllResult() throws FileNotFoundException {
        var results = new ArrayList<Result>();
        BufferedReader reader = new BufferedReader(new FileReader("src/Results/result.txt"));
        try {
            String line = reader.readLine();
            while (line != null) {
                var oneResult = line.split(" ");
                results.add(new Result(Integer.parseInt(oneResult[1]), oneResult[0]));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
