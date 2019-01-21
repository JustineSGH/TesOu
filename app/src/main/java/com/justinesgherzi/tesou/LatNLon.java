package com.justinesgherzi.tesou;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class LatNLon extends AppCompatActivity {

    public double Latitude;
    public double Longitude;
    public String IdUtilisateur;


    public void setIdUtilisateur(String idUtilisateur) {
        // Log.d("setUtilisateur", String.valueOf(idUtilisateur));
        IdUtilisateur = idUtilisateur;
    }

    public void setLatitude(double latitude) {
        // Log.d("setLatitude", String.valueOf(latitude));
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        // Log.d("setLongitude", String.valueOf(longitude));
        Longitude = longitude;
    }

    public String getIdUtilisateur() {
        return IdUtilisateur;
    }

    public Double getLatitude(){
        return Latitude;
    }

    public Double getLongitude(){
        return Longitude;
    }

}
