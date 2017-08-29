package uk.co.ashawijekoon.whatson.models;

/**
 * Created by Asha Wijekoon on 28/08/2017.
 */

public class Location {
    private double lat;
    private double lng;
    private String name;

    public Location(double lat, double lng, String name) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
