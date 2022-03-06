package com.example.galery;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class MinhaLocalizacaoListner {
    public static double latitude;
    public static double longitude;


    public void onLocationChanged(@NonNull Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public void OnProviderDisable(@NonNull String provider){}

    public void OnProviderEnabled(@NonNull String provider){}

    public void onStatuschanged(String provider, int status, Bundle extras){}
}
