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
        if (Math.abs(Double.parseDouble(lat)) > 90.0)
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
        if (Math.abs(Double.parseDouble(lng)) > 180.0)
            throw new IllegalArgumentException("Invalid longitude \"" + lng + "\". " +
                    "Expected a String holding a floating point number from range -180.0 to 180.0 included.");

        this.lng = lng;
    }

    /**
     * Returns the distance in kilometers from this {@link Geo}'s geographical coordinates to the other.
     * <p>
     * Uses Haversine formula for computations.
     *
     * @param geo {@link Geo} to measure distance to
     * @return the distance in kilometers to the specific {@link Geo}
     */
    public double getDistanceTo(@NotNull Geo geo) {
        final double EARTH_RADIUS_KM = 6371.0088;

        double lat1 = Double.parseDouble(this.lat);
        double lat2 = Double.parseDouble(geo.getLat());
        final double lng1 = Double.parseDouble(this.lng);
        final double lng2 = Double.parseDouble(geo.getLng());
        final double diffLat = Math.toRadians(lat1 - lat2);
        final double diffLng = Math.toRadians(lng1 - lng2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        final double a = Math.pow(Math.sin(diffLat / 2), 2) +
                Math.pow(Math.sin(diffLng / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

}