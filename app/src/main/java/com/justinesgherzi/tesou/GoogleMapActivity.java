package com.justinesgherzi.tesou;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GoogleMapActivity extends AppCompatActivity implements LocationListener {

    private static final int ID_DEMANDE_PERMISSION = 123;
    private LocationManager monLocationManager;

    private GoogleMap maGoogleMap;
    private MapFragment monMapFragment;
    private double latitude;
    private double longitude;
    private String IdUtilisateur;
    private Bdd bdd = new Bdd();
    private ArrayList<ArrayListCustom> arrayList = new ArrayList<ArrayListCustom>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_google_map);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnContextClickListener(new View.OnContextClickListener() {
            @Override
            public boolean onContextClick(View v) {
                Log.d("test", "test au click");
                return true;
            }
        });

        IdUtilisateur = getIntent().getStringExtra("IdUtilisateur");

        FragmentManager monFragmentManager = getFragmentManager();
        monMapFragment = (MapFragment) monFragmentManager.findFragmentById(R.id.carteGoogleMap);
        verifierPermission();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LoginActivity loginActivity = new LoginActivity();
        switch (item.getItemId()) {
            case R.id.logout:
                finish();
                //loginActivity.supprimerFichier();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

                arrayList = bdd.getLocationOfUsers();

                for(ArrayListCustom str: arrayList){
                    // Log.d("titi-arrayList ", "User : " + str.getIdUser()  + " Latidude : " + str.getLatitude() + " Longitude : " + str.getLongitude());

                    Location currentLocation = new Location("currentLocation");
                    currentLocation.setLatitude(latitude);
                    currentLocation.setLongitude(longitude);

                    Location newLocation = new Location("newLocation");
                    newLocation.setLatitude(str.getLatitude());
                    newLocation.setLongitude(str.getLongitude());

                    float distanceInMeters = currentLocation.distanceTo(newLocation) / 1000;

                    LatLng coordinate = new LatLng(str.getLatitude(), str.getLongitude());
                    maGoogleMap.addMarker(new MarkerOptions()
                            .position(coordinate)
                            .title(str.getIdUser())
                            .snippet(String.valueOf(distanceInMeters) + "km")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))).showInfoWindow();


                    //CameraUpdate location = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
                    //maGoogleMap.animateCamera(location);
                }
                Date currentDate = Calendar.getInstance().getTime();

                bdd.PostDataInBdd(IdUtilisateur, longitude, latitude, currentDate);
                LatLng myCoordinate = new LatLng(latitude, longitude);
                maGoogleMap.addMarker(new MarkerOptions()
                        .position(myCoordinate)
                        .title(IdUtilisateur)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).showInfoWindow();


            }
        });
    }


}
