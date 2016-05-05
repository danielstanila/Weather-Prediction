package test;

import dataprocessing.DataSetProcessor;
import process.FileContent;
import process.ProcessExecutor;

import java.io.File;

public class Test {

    private static final String PATH = "svm_light/";

    private static void testProcessExecutor() {
        ProcessExecutor executor = new ProcessExecutor();

        FileContent stdOutput;

        stdOutput = executor.execute(PATH + "svm_learn", PATH + "example1/train.dat", PATH + "example1/model").readStdOutput();
        stdOutput.forEach(System.out::println);

        System.out.println();

        stdOutput = executor.execute(PATH + "svm_classify", PATH + "example1/test.dat", PATH + "example1/model", PATH + "example1/predictions").readStdOutput();
        stdOutput.forEach(System.out::println);
    }

    private static void testDataSetConverter() {
        /*for (int i = 2010; i <= 2016; i++) {
            new DataSetProcessor(new File("data sets/" + i + ".csv"), new File("data sets/" + i + "_filtered.txt")).convertDataSet();
            System.out.println(i + " done.");
        }*/

        //new DataSetProcessor(new File("data sets/2016_filtered.txt"), new File("data sets/2010.txt")).convertDataSet();
    }

    public static void main(String[] args) {
        //testProcessExecutor();
        //testDataSetConverter();
    }
}
