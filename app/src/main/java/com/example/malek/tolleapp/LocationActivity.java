package com.example.malek.tolleapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.service.voice.VoiceInteractionSession;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LocationActivity extends AppCompatActivity  {
    private Button button;
    static final int REQUEST_LOCATION = 1;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        button = findViewById(R.id.backToMain2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMainActivity();
                textView.setText("");
            }
        });

        button = findViewById(R.id.shareLocationButton);

        textView = findViewById(R.id.locationText);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            //this method gets the longitude and latitude and append them to the text view
            @Override
            public void onLocationChanged(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                textView.setText("");
                textView.append("Longitude: " + longitude + "`\n" + "Latitude: " + latitude);
            }
            //this method is automatically generated when creating a new location listener
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
            //this method is automatically generated when creating a new location listener
            @Override
            public void onProviderEnabled(String s) {

            }

            //If GPS is disabled an Intent is fired to send the user to activate the gps
            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        //This is created manually by the SDK to check if permissions are granted to use INTERNET, ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION
        //IF not then the permissions are added, else the method getLocation() is called
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET},REQUEST_LOCATION);

            return;
        }else {
            getLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int [] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                break;
        }
        }

        //This method gets the location of the user
    private void getLocation(){
        button.setOnClickListener(new View.OnClickListener() {
            //SUPPRESSLINT is added so that the location Manager doesnt check for permissions again otherwise it displays an error
            @SuppressLint("MissingPermission")
            //The onClick event calls the locationManager which has 4 parameters: Provider (gps), minTime for how long it has to wait till refresh
            //minDistance which is a second trigger to refresh gps coordinates if the distance is >0 (here not needed) and finally our location listener
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
            }
        });

    }



    //this method is called when clicking on the button zurück to go back to main
    private void backToMainActivity(){
        Intent intent = new Intent(this,MainInDekoActivity.class);
        startActivity(intent);
    }
}

