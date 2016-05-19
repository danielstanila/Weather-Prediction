package dataprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataInterpreter {

    private DataInterpreter() {
    }

    public static List<List<Double>> interpretResults(File expectedResults, File actualResults) {
        List<List<Double>> expectedActualMap = new ArrayList<>();
        expectedActualMap.add(new ArrayList<>());
        expectedActualMap.add(new ArrayList<>());
        try(FileInputStream expectedResultsInputStream = new FileInputStream(expectedResults);
            FileInputStream actualResultsInputStream = new FileInputStream(actualResults);
            BufferedReader expectedReader = new BufferedReader(new InputStreamReader(expectedResultsInputStream));
            BufferedReader actualReader = new BufferedReader(new InputStreamReader(actualResultsInputStream))) {

            String expectedInputLine;
            String actualInputLine;
            while ((expectedInputLine = expectedReader.readLine()) != null && (actualInputLine = actualReader.readLine()) != null) {
                if (expectedInputLine.startsWith("#")) {
                    expectedInputLine = expectedReader.readLine();
                }
                Double expectedResult = Double.parseDouble(expectedInputLine.split(" ")[0]);
                Double actualResult = Double.parseDouble(actualInputLine);
                expectedActualMap.get(0).add(expectedResult);
                expectedActualMap.get(1).add(actualResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return expectedActualMap;
    }
}
