package com.example.shubham_v.foodie.ModelClass;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by shubham_v on 06-12-2016.
 */

public class RestLatLong {

    private String restoName;
    private LatLng restoLocation;

    public RestLatLong(String restoName, LatLng restoLocation) {
        this.restoName = restoName;
        this.restoLocation = restoLocation;
    }

    public String getRestoName() {
        return restoName;
    }

    public void setRestoName(String restoName) {
        this.restoName = restoName;
    }

    public LatLng getRestoLocation() {
        return restoLocation;
    }

    public void setRestoLocation(LatLng restoLocation) {
        this.restoLocation = restoLocation;
    }
}
