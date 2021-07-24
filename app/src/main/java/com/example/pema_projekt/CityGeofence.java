package com.example.pema_projekt;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.location.Geofence;


public class CityGeofence {

    /**
     * We need this class to create a city object to save the coordinates of the geofences in the firebase
     */

    private float longitude;
    private float latitude;
    private int rad;
    private Geofence geofence;

    private String name;

    public CityGeofence(){

    }

    /**
     * Constructor of the CityGeofence object.
     *
     * @param longitudeInput the longitude of the coordinates
     * @param latitudeInput the latitude of the coordinates
     * @param radius the radius of the geofence
     */
    public CityGeofence(float longitudeInput, float latitudeInput, int radius, String cityName){
        this.latitude = latitudeInput;
        this.longitude = longitudeInput;
        this.rad = radius;
        this.name = cityName;
        this.geofence = new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId(name)

                .setCircularRegion(
                        latitude,
                        longitude,
                        rad
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();

    }

    public Geofence getGeofence() {
        return geofence;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
