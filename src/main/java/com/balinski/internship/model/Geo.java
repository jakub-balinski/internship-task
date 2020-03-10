package com.balinski.internship.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "lat",
        "lng"
})
public class Geo {

    @JsonProperty("lat")
    private String lat;
    @JsonProperty("lng")
    private String lng;

    @JsonProperty("lat")
    public String getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public String getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(String lng) {
        this.lng = lng;
    }

    public double computeDistanceTo(Geo other) {
        double diffLat = Double.parseDouble(this.getLat()) - Double.parseDouble(other.getLat());
        double diffLng = Double.parseDouble(this.getLng()) - Double.parseDouble(other.getLng());

        return diffLat*diffLat + diffLng*diffLng;
    }

}