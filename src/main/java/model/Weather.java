package model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Weather implements Serializable {

    private Map<WeatherParameter, Double> parameterMap;

    public Weather() {
        this.parameterMap = new TreeMap<>();
    }

    public void addEntry(WeatherParameter parameter, Double value) {
        parameterMap.put(parameter, value);
    }

    public Double getValue(WeatherParameter parameter) {
        return parameterMap.get(parameter);
    }

    @Override
    public String toString() {
        return "Weather{" +
                "parameterMap=" + parameterMap +
                '}';
    }
}
