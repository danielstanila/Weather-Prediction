package gui;

import model.WeatherWizard;
import serialization.Serialization;

import java.io.File;

public class WeatherController {

    private WeatherWizard trainData;
    private WeatherWizard testData;

    private WeatherPanel weatherPanel;

    public WeatherController() {
        processData();
    }

    private void processData() {
        trainData = new Serialization(new File("data sets/traindata.bin")).read();
        if (trainData == null) {
            final int n = 24;
            String[] dataPaths = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                dataPaths[i] = "data sets/" + (1990 + i) + "_filtered.txt";
            }
            trainData = new WeatherWizard(dataPaths);
            new Serialization(new File("data sets/traindata.bin")).write(trainData);
        }

        testData = new Serialization(new File("data sets/testdata.bin")).read();
        if (testData == null) {
            final int n = 1;
            String[] dataPaths = new String[n + 1];
            for (int i = 0; i <= n; i++) {
                dataPaths[i] = "data sets/" + (2015 + i) + "_filtered.txt";
            }
            testData = new WeatherWizard(dataPaths);
            new Serialization(new File("data sets/testdata.bin")).write(trainData);
        }

        System.out.println("ok");
    }

    private void generateDataSets() {

    }
}
