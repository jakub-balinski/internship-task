package com.balinski.internship.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotNull;

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
    public void setLat(@NotNull String lat) {
        if (Math.abs(Float.parseFloat(lat)) > 90f)
            throw new IllegalArgumentException("Invalid latitude \"" + lat + "\". " +
                    "Expected a String holding a floating point number from range -90.0 to 90.0 included.");

        this.lat = lat;
    }

    @JsonProperty("lng")
    public String getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(@NotNull String lng) {
        if (Math.abs(Float.parseFloat(lng)) > 180f)
            throw new IllegalArgumentException("Invalid longitude \"" + lng + "\". " +
                    "Expected a String holding a floating point number from range -180.0 to 180.0 included.");

        this.lng = lng;
    }

    //in kilometers, accuracy is within a few meters
    public float getDistanceTo(@NotNull Geo other) {
        final float EARTH_RADIUS_KM = 6372.8f;

        //Haversine formula
        float lat1 = Float.parseFloat(this.lat);
        float lat2 = Float.parseFloat(other.getLat());
        final float lng1 = Float.parseFloat(this.lng);
        final float lng2 = Float.parseFloat(other.getLng());
        final float diffLat = (float) Math.toRadians(lat1 - lat2);
        final float diffLng = (float) Math.toRadians(lng1 - lng2);
        lat1 = (float) Math.toRadians(lat1);
        lat2 = (float) Math.toRadians(lat2);

        final float a = (float) (Math.pow(Math.sin(diffLat / 2), 2) +
                Math.pow(Math.sin(diffLng / 2), 2) * Math.cos(lat1) * Math.cos(lat2));
        final float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));

        return EARTH_RADIUS_KM * c;
    }

}