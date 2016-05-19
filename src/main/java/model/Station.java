package model;

import java.io.Serializable;

public class Station implements Comparable<Station>, Serializable {

    private String name;
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public Station(String name, Double latitude, Double longitude, Double altitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    @Override
    public int compareTo(Station o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (name != null ? !name.equals(station.name) : station.name != null) return false;
        if (latitude != null ? !latitude.equals(station.latitude) : station.latitude != null) return false;
        if (longitude != null ? !longitude.equals(station.longitude) : station.longitude != null) return false;
        return altitude != null ? altitude.equals(station.altitude) : station.altitude == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (altitude != null ? altitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", altitude=" + altitude +
                '}';
    }
}
