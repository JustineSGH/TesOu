package com.justinesgherzi.tesou;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;

public class BackgroundService extends Service implements LocationListener {

    private static final int NOTIF_ID = 1;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    private LocationManager monLocationManager;
    private double latitude;
    private double longitude;
    private Bdd bdd = new Bdd();
    private ArrayList<ArrayListCustom> arrayList = new ArrayList<ArrayListCustom>();
    private static int distanceUser = 5;
    private String idUser;
    private int delai;
    private int NiveauBatterie;

    /*
    service qui tourne en arrière plan quand on quitte l'application.
    Ce service envoie une notification si la distance est inférieure ou églales à 5 Km
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        monLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //reglageNotification(this);
        monLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, delai, 0, this);
        monLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, delai, 0, this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        idUser = (String) extras.get("idUser");
        startForeground();

        return Service.START_STICKY;
    }

    private void startForeground() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("unique_channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
        }
        Intent notificationIntent = new Intent(this, GoogleMapActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(NOTIF_ID, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        arrayList = bdd.getLocationOfUsers();


        if (arrayList.size() != 0) {
            Log.d("arrayList", String.valueOf(arrayList));
            for (ArrayListCustom str : arrayList) {
                if (!idUser.equals(str.getIdUser())) {
                    Location currentLocation = new Location("currentLocation");
                    currentLocation.setLatitude(latitude);
                    currentLocation.setLongitude(longitude);

                    Location newLocation = new Location("newLocation");
                    newLocation.setLatitude(str.getLatitude());
                    newLocation.setLongitude(str.getLongitude());

                    float distanceInMeters = currentLocation.distanceTo(newLocation) / 1000;
                    Log.d("distanceInMeters", String.valueOf(distanceInMeters));

                    if (distanceInMeters <= distanceUser) {
                        Log.d("distanceInMeters", String.valueOf(distanceInMeters));
                        createNotification(distanceInMeters, str.getIdUser());
                    }
                }
            }
            arrayList.clear();
        }
        /*
        reglageNotification(this);
        if (monLocationManager != null) {
            monLocationManager.removeUpdates(this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        monLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, delai, 0, this);
        monLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, delai, 0, this);
        Toast.makeText(this, "Latitude = " + latitude + " - Longitude = " + longitude, Toast.LENGTH_LONG).show();*/


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

    //Création de la notification
    private void createNotification(float distanceInMeters, String idUser) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("unique_channel", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{500, 500, 500, 500, 500});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "unique_channel")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Information")
                    .setContentText(idUser + " est à " + distanceInMeters + " Km de toi !")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, LoginActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(contentIntent);


            notificationManager.notify(1223456, mBuilder.build());
        }

    }

    /*public void reglageNotification(Context context) {
        BatteryManager bm = (BatteryManager)context.getSystemService(BATTERY_SERVICE);
        NiveauBatterie = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        delai = (int) ((100 - NiveauBatterie) * 0.8*1000);
        Log.d("titi", "Niveau Battery " + NiveauBatterie+ " delai " + delai);
    }*/

    public static void setDistanceUser(int distance) {
        distanceUser = distance;
    }
}
