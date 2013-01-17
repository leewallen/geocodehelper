package com.codebytes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Coordinates {
    private Double latitude = 0.;
    private Double longitude = 0.;
    private String locationName = "";

    public Coordinates(){
    }

    public Coordinates(Double lat, Double lon){
        this.latitude = lat;
        this.longitude = lon;
    }

    public Coordinates(String lat, String lon){
        this.latitude = Double.parseDouble(lat);
        this.longitude= Double.parseDouble(lon);
    }


    public Coordinates(String lat, String lon, String locationName){
        this.latitude = Double.parseDouble(lat);
        this.longitude= Double.parseDouble(lon);
        setLocationName(locationName);
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLatitude(){
        return this.latitude;
    }

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public Double getLongitude(){
        return this.longitude;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    @Override
    public boolean equals(Object o){
        if(o.getClass() != this.getClass()) return false;

        Coordinates c = (Coordinates) o;

        if(c.getLatitude().equals(getLatitude()) && c.getLongitude().equals(getLongitude()))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return this.getLatitude().hashCode() ^ this.getLongitude().hashCode() ^ this.getLocationName().hashCode();
    }
}
