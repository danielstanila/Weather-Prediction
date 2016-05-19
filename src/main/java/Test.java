import dataprocessing.DataSetProcessor;
import model.WeatherParameter;
import model.WeatherWizard;
import process.FileContent;
import process.ProcessExecutor;
import serialization.Serialization;

import java.io.File;

public class Test {

    private static final String PATH = "svm_light/";

    private static void testProcessExecutor() {
        ProcessExecutor executor = new ProcessExecutor();

        FileContent stdOutput;

        stdOutput = executor.execute(PATH + "svm_learn", "-z", "r", PATH + "example1/train.dat", PATH + "example1/model").readStdOutput();
        stdOutput.forEach(System.out::println);

        System.out.println();

        stdOutput = executor.execute(PATH + "svm_classify", PATH + "example1/test.dat", PATH + "example1/model", PATH + "example1/predictions").readStdOutput();
        stdOutput.forEach(System.out::println);
    }

    public static void main(String[] args) {
        Serialization serialization = new Serialization(new File("data sets/weather.bin"));
        WeatherWizard ww = serialization.read();
        //WeatherWizard ww = new model.WeatherWizard("data sets/2010.csv", "data sets/2011.csv", "data sets/2012.csv", "data sets/2013.csv", "data sets/2014.csv", "data sets/2015.csv");
        serialization.write(ww);
        DataSetProcessor.generateDataSet(ww, WeatherParameter.AverageTemperature, new File("data sets/train.dat"));
    }
}
