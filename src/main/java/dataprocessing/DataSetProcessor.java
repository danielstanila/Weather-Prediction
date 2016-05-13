package dataprocessing;

import model.Date;
import model.Station;
import model.Weather;
import model.WeatherParameter;

import java.io.*;
import java.util.*;

public class DataSetProcessor {

    private DataSetProcessor() {
    }

    public static Map<Station, Map<Date, Weather>> readDataSet(File input, Map<String, Station> stationMap) {
        Map<Station, Map<Date, Weather>> weatherMap = new TreeMap<>();
        try (FileInputStream inputStream = new FileInputStream(input);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] tokens = inputLine.split(" ");

                Station station = stationMap.get(tokens[0]);

                Integer year = Integer.parseInt(tokens[1].substring(0, 4));
                Integer month = Integer.parseInt(tokens[1].substring(4, 6));
                Integer day = Integer.parseInt(tokens[1].substring(6, 8));
                Date date = new Date(day, month, year);

                WeatherParameter parameter = null;

                switch (tokens[2]) {
                    case "TMIN" : {
                        parameter = WeatherParameter.MinimumTemperature;
                        break;
                    }
                    case "TMAX" : {
                        parameter = WeatherParameter.MaximumTemperature;
                        break;
                    }
                    case "TAVG" : {
                        parameter = WeatherParameter.AverageTemperature;
                        break;
                    }
                    case "PRCP" : {
                        parameter = WeatherParameter.Precipitations;
                        break;
                    }
                    case "SNWD" : {
                        parameter = WeatherParameter.SnowDepth;
                        break;
                    }
                }

                Double value = Double.parseDouble(tokens[3]) / 10;

                if (parameter != null) {
                    weatherMap.putIfAbsent(station, new TreeMap<>());
                    weatherMap.get(station).putIfAbsent(date, new Weather());

                    weatherMap.get(station).get(date).addEntry(parameter, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return weatherMap;
    }

    public static void convertDataSet(File input, File output) {
        try(FileInputStream inputStream = new FileInputStream(input);
            FileOutputStream outputStream = new FileOutputStream(output);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String outputLine = convertEntry(filterEntry(inputLine));
                if (!outputLine.isEmpty()) {
                    writer.write(outputLine + "\n");
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static String filterEntry(String input) {
        String[] tokens = input.split(", ");
        StringBuilder output = new StringBuilder();
        if (tokens[0].startsWith("ROE") || tokens[0].startsWith("ROM")) {
            for (String token : tokens) {
                output.append(token).append(", ");
            }
        }
        return output.toString();
    }

    private static String convertEntry(String input) {
        return input;
    }
}