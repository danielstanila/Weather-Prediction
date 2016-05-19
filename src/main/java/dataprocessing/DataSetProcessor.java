package dataprocessing;

import model.*;
import model.Date;

import java.io.*;
import java.time.LocalDate;
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
                String[] tokens = inputLine.split(",");

                Station station = stationMap.get(tokens[0]);

                Integer year = Integer.parseInt(tokens[1].substring(0, 4));
                Integer month = Integer.parseInt(tokens[1].substring(4, 6));
                Integer day = Integer.parseInt(tokens[1].substring(6, 8));
                Date date = new Date(day, month, year);

                WeatherParameter parameter = null;

                switch (tokens[2]) {
                    case "TMIN" :
                        parameter = WeatherParameter.MinimumTemperature;
                        break;
                    case "TMAX" :
                        parameter = WeatherParameter.MaximumTemperature;
                        break;
                    case "TAVG" :
                        parameter = WeatherParameter.AverageTemperature;
                        break;
                    case "PRCP" :
                        parameter = WeatherParameter.Precipitations;
                        break;
                    case "SNWD" :
                        parameter = WeatherParameter.SnowDepth;
                        break;
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

    public static void filterDataSet(File input, File output) {
        try(FileInputStream inputStream = new FileInputStream(input);
            FileOutputStream outputStream = new FileOutputStream(output);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String outputLine = filterEntry(inputLine);
                if (!outputLine.isEmpty()) {
                    writer.write(outputLine + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String filterEntry(String input) {
        String[] tokens = input.split(", ");
        StringBuilder output = new StringBuilder();
        if ((tokens[0].startsWith("ROE") || tokens[0].startsWith("ROM")) && !input.contains("SNWD")) {
            for (String token : tokens) {
                output.append(token).append(",");
            }
        }
        return output.toString();
    }

    public static void generateDataSet(WeatherWizard weatherWizard, WeatherParameter parameter, Date startDate, Date endDate, File output) {
        try(FileOutputStream outputStream = new FileOutputStream(output);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {

            for (Station station : weatherWizard.getStations()) {
                for (LocalDate localDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDay()); localDate.isBefore(LocalDate.of(endDate.getYear(), endDate.getMonth(), endDate.getDay())); localDate = localDate.plusDays(1)) {
                    Date date = Date.convertFromLocalDate(localDate);
                    Weather value = weatherWizard.getStationWeatherOnDate(station, date);
                    if (value != null && value.getValue(parameter) != null) {

                        StringBuilder stringBuilder = new StringBuilder("#" + station.getName() + " on " + date.toString() + "\n");
                        stringBuilder.append(value.getValue(parameter)).append(" ");

                        for (int i = 1; i < 6; i++) {
                            LocalDate previousDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
                            previousDate = previousDate.minusDays(i);

                            value = weatherWizard.getStationWeatherOnDate(station, Date.convertFromLocalDate(previousDate));
                            if (value != null && value.getValue(parameter) != null) {
                                stringBuilder.append(i).append(":").append(value.getValue(parameter)).append(" ");
                            }
                        }

                        LocalDate previousYear = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
                        previousYear = previousYear.minusYears(1);

                        value = weatherWizard.getStationWeatherOnDate(station, Date.convertFromLocalDate(previousYear));
                        if (value != null && value.getValue(parameter) != null) {
                            stringBuilder.append("6:").append(value.getValue(parameter));
                        }

                        writer.write(stringBuilder.toString() + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}