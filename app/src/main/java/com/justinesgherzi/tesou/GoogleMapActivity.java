package com.justinesgherzi.tesou;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends AppCompatActivity implements LocationListener {

    private static final int ID_DEMANDE_PERMISSION = 123;
    private LocationManager monLocationManager;

    private GoogleMap maGoogleMap;
    private MapFragment monMapFragment;
    String IdUtilisateur;
    Bdd bdd = new Bdd();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_google_map);

        IdUtilisateur = getIntent().getStringExtra("IdUtilisateur");

        FragmentManager monFragmentManager = getFragmentManager();
        monMapFragment = (MapFragment) monFragmentManager.findFragmentById(R.id.carteGoogleMap);
        verifierPermission();
    }

    private void verifierPermission(){
        if( !(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            } ,ID_DEMANDE_PERMISSION);
            return;
        }
        monLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        monLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        monLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        chargerMap();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        verifierPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(monLocationManager != null){
            monLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == ID_DEMANDE_PERMISSION){

        }
    }

    private double latitude;
    private double longitude;

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Toast.makeText(this, "Latitude = "+ latitude + " - Longitude = " + longitude, Toast.LENGTH_LONG).show();
        // maGoogleMap.clear();
        chargerMap();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void chargerMap(){
        monMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                maGoogleMap = googleMap;

                bdd.PostDataInBdd(IdUtilisateur, longitude, latitude);

                LatLng coordinate = new LatLng(latitude, longitude);
                maGoogleMap.addMarker(new MarkerOptions().position(coordinate).title("Hello-world"));
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
                maGoogleMap.animateCamera(location);
            }
        });
    }
}
