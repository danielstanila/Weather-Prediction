package gui;

import dataprocessing.DataInterpreter;
import dataprocessing.DataSetProcessor;
import model.Date;
import model.WeatherParameter;
import model.WeatherWizard;
import process.FileContent;
import process.ProcessExecutor;
import serialization.Serialization;

import java.io.File;
import java.util.List;
import java.util.Map;

public class WeatherController {

    private WeatherWizard trainData;
    private WeatherWizard testData;

    public WeatherController() {
        processData();
        generateDataSets();
        applyRegression();
    }

    private void processData() {
        trainData = new Serialization(new File("data sets/traindata.bin")).read();
        if (trainData == null) {
            final int n = 4;
            String[] dataPaths = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                dataPaths[i] = "data sets/" + (2010 + i) + "_filtered.txt";
            }
            trainData = new WeatherWizard(dataPaths);
            new Serialization(new File("data sets/traindata.bin")).write(trainData);
        } else {
            System.out.println("Done reading train data from .bin file.");
        }

        testData = new Serialization(new File("data sets/testdata.bin")).read();
        if (testData == null) {
            final int n = 2;
            String[] dataPaths = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                dataPaths[i] = "data sets/" + (2014 + i) + "_filtered.txt";
            }
            testData = new WeatherWizard(dataPaths);
            new Serialization(new File("data sets/testdata.bin")).write(testData);
        } else {
            System.out.println("Done reading test data from .bin file.");
        }
    }

    private void generateDataSets() {
        for (WeatherParameter parameter : WeatherParameter.values()) {
            DataSetProcessor.generateDataSet(trainData, parameter, new Date(1, 1, 2011), new Date(31, 12, 2014), new File("svm_light/" + parameter.toString() + "/train.dat"));
            System.out.println("Done writing " + parameter.toString() + " train.dat file.");
            DataSetProcessor.generateDataSet(testData, parameter, new Date(1, 1, 2015), new Date(31, 12, 2015), new File("svm_light/" + parameter.toString() + "/test.dat"));
            System.out.println("Done writing " + parameter.toString() + " test.dat file.");
        }
    }

    private void applyRegression() {

        for (WeatherParameter parameter : WeatherParameter.values()) {
            //Hack fix because it takes too long to parse Snow Depth or Precipitation data
            if (!parameter.equals(WeatherParameter.SnowDepth) && !parameter.equals(WeatherParameter.Precipitations)) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        ProcessExecutor executor = new ProcessExecutor();
                        FileContent stdOutput;

                        stdOutput = executor.execute("svm_light/svm_learn", "-z", "r", "-w", "0.5", "svm_light/" + parameter + "/train.dat", "svm_light/" + parameter + "/model").readStdOutput();
                        stdOutput.forEach(System.out::println);
                        stdOutput = executor.execute("svm_light/svm_classify", "svm_light/" + parameter + "/test.dat", "svm_light/" + parameter + "/model", "svm_light/" + parameter + "/predictions").readStdOutput();
                        stdOutput.forEach(System.out::println);
                    }
                };
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Done");
    }

    protected void interpretResults(WeatherParameter parameter) {
        List<List<Double>> map = DataInterpreter.interpretResults(new File("svm_light/" + parameter + "/test.dat"), new File("svm_light/" + parameter + "/predictions"));
        new GraphWindow(parameter.toString(), map.get(0).stream().toArray(Double[]::new), map.get(1).stream().toArray(Double[]::new));
    }
}
