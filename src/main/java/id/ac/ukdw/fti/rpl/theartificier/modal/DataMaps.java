package id.ac.ukdw.fti.rpl.theartificier.modal;

import java.util.HashMap;

import javafx.beans.binding.DoubleExpression;

public class DataMaps {
    private String title, duration, verses, locations, namaLocation;
    private Double latitude, longitude;

    private HashMap<String, String> titleDuration = new HashMap<String, String>();


    public DataMaps(String title, String duration, String verses, String locations){
        this.title = title;
        this.duration = duration;
        this.verses = verses;
        this.locations = locations;
    }

    public DataMaps(String namaLocation, Double latitude, Double longitude){
        this.namaLocation = namaLocation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DataMaps(Double latitude, Double longitude, String duration, String title){
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.duration = duration;
        this.titleDuration.put(title, duration);
    }

    public DataMaps() {
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getVerses() {
        return verses;
    }


    public String getLocations(){
        return locations;
    }

    public String getNamaLocation() {
        return namaLocation;
    }


    public Double getLatitude() {
        return latitude;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setTitleDuration(String title, String duration) {
        this.titleDuration.put(title, duration);
    }

    public HashMap<String, String> getTitleDuration() {
        return titleDuration;
    }    
}
