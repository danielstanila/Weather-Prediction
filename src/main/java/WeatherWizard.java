import dataprocessing.DataSetProcessor;
import model.Date;
import model.Station;
import model.Weather;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class WeatherWizard {

    private Map<String, Station> stations;

    public WeatherWizard() {
        this.stations = populateStationSet(new File("data sets/stations.txt"));
    }

    public Map<String, Station> getStations() {
        return stations;
    }

    private Map<String, Station> populateStationSet(File input) {
        Map<String, Station> stations = new TreeMap<>();
        try(FileInputStream inputStream = new FileInputStream(input);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                String[] tokens = inputLine.split(", ");
                stations.put(tokens[0], new Station(tokens[4],
                        Double.parseDouble(tokens[1]),
                        Double.parseDouble(tokens[2]),
                        Double.parseDouble(tokens[3])));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return stations;
    }

    public static void main(String[] args) {
        WeatherWizard ww = new WeatherWizard();

        Map<String, Station> stations = ww.getStations();

        Map<Station, Map<Date, Weather>> greatMap = new TreeMap<>();

        for (int i = 2010; i <= 2016; i++) {
            Map<Station, Map<Date, Weather>>  map = DataSetProcessor.readDataSet(new File("data sets/" + i + "_filtered.txt"), stations);

            for (Map.Entry<Station, Map<Date, Weather>> entry : map.entrySet()) {
                Station key = entry.getKey();
                Map<Date, Weather> newMap = new TreeMap<>();
                if (greatMap.containsKey(key))
                    newMap.putAll(greatMap.get(key));
                newMap.putAll(map.get(key));
                greatMap.put(entry.getKey(), newMap);
            }
        }

        try(OutputStream stream = new FileOutputStream(new File("test.txt"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream))) {
            for (Map.Entry<Station, Map<Date, Weather>> entry1 : greatMap.entrySet()) {
                writer.write(entry1.getKey() + " ->\n");
                for (Map.Entry<Date, Weather> entry2 : entry1.getValue().entrySet()) {
                    writer.write(entry2.getKey() + " -> ");
                    writer.write(entry2.getValue() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
