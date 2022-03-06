package com.example.galery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class Location extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

    public void buscarInformacaoGPS(View view){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(Location.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            ActivityCompat.requestPermissions(Location.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
            ActivityCompat.requestPermissions(Location.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE},1);
            return;
        }
        LocationManager mLocManager = (LocationManager) getSystemService(Location.this.LOCATION_SERVICE);
        LocationListener mLocListener = (LocationListener) new MinhaLocalizacaoListner();

        mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

        if (mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            String texto = "Latitude: " + MinhaLocalizacaoListner.latitude+ "\n" +
                    "Longitude" + MinhaLocalizacaoListner.longitude + "\n";
            Toast.makeText(Location.this,"texto", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Location.this, "gps desabilitado", Toast.LENGTH_LONG).show();
        }
        this.MostraGoogleMaps(MinhaLocalizacaoListner.latitude, MinhaLocalizacaoListner.longitude);
    }

    public void MostraGoogleMaps(double latitude, double longitude){
        WebView wv = findViewById(R.id.webv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.google.com.br/maps/search/?api=18query=" + latitude + "," + longitude);
    }

    //voltaPraTelaPrincipal
    public void volta(View view){
        startActivity(new Intent(Location.this, Phonto.class));
    }
//endVoltaPraTelaPrincipal
}