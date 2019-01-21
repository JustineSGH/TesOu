package com.justinesgherzi.tesou;

public class ArrayListCustom {
    private String idUser;
    private double latitude;
    private double longitude;

    public ArrayListCustom(String idUser, double latitude, double longitude) {
        this.idUser = idUser;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIdUser() {
        return idUser;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
