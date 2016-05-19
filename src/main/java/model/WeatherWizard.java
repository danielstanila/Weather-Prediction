package model;

import dataprocessing.DataSetProcessor;
import serialization.Serialization;

import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WeatherWizard implements Serializable {

    private static final String STATION_PATH = "data sets/stations.bin";
    private Map<Station, Map<Date, Weather>> weather;

    public WeatherWizard(String... dataSetPaths) {
        this.weather = readDataSets(dataSetPaths);
    }

    private Map<Station, Map<Date, Weather>> readDataSets(String... paths) {
        Map<String, Station> stations = new Serialization(new File(STATION_PATH)).read();
        Map<Station, Map<Date, Weather>> weatherMap = new TreeMap<>();

        for (String path : paths) {
            System.out.println("Reading " + path + "...");
            //String cleanPath = path.split("\\.")[0];
            //DataSetProcessor.filterDataSet(new File(path), new File(cleanPath + "_filtered.txt"));
            //Map<Station, Map<Date, Weather>> map = DataSetProcessor.readDataSet(new File(cleanPath + "_filtered.txt"), stations);
            Map<Station, Map<Date, Weather>> map = DataSetProcessor.readDataSet(new File(path), stations);
            for (Map.Entry<Station, Map<Date, Weather>> entry : map.entrySet()) {
                Station key = entry.getKey();
                Map<Date, Weather> newMap = new TreeMap<>();
                if (weatherMap.containsKey(key)) {
                    newMap.putAll(weatherMap.get(key));
                }
                newMap.putAll(map.get(key));
                weatherMap.put(key, newMap);
            }
            System.out.println("Done reading " + path + ".");
        }
        return weatherMap;
    }

    public Set<Station> getStations() {
        return weather.keySet();
    }

    public Station getStationByName(String name) {
        for (Station station : weather.keySet()) {
            if (station.getName().equals(name)) {
                return station;
            }
        }
        return null;
    }

    public Weather getStationWeatherOnDate(Station station, Date date) {
        return weather.get(station).get(date);
    }
}
