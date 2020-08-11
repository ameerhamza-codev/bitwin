package com.codev.bitwinapp;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

class Location {

    String streetAddress;
    String Url;
    String formattedLocation;
    String lat,lang;

    FusedLocationProviderClient fusedLocationProviderClient;

    public Location() {
    }

    public String getFormattedLocation() {



        return formattedLocation;
    }
}
