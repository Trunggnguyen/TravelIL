package com.example.travelil.Model;

public class PlaceAdd {
    String idplace ,image1, imageavata,imageavata11, name, place, dacdiem,cost,longitude,latitude, placedetail,timestart, timeend, loaihinh;
    int idloaihinh;


    public PlaceAdd(String idplace, String image1, String imageavata, String imageavata11, String name, String place, String dacdiem, String cost, String longitude, String latitude, String placedetail, String timestart, String timeend, String loaihinh, int idloaihinh) {
        this.idplace = idplace;
        this.image1 = image1;
        this.imageavata = imageavata;
        this.imageavata11 = imageavata11;
        this.name = name;
        this.place = place;
        this.dacdiem = dacdiem;
        this.cost = cost;
        this.longitude = longitude;
        this.latitude = latitude;
        this.placedetail = placedetail;
        this.timestart = timestart;
        this.timeend = timeend;
        this.loaihinh = loaihinh;
        this.idloaihinh = idloaihinh;
    }

    public String getTimestart() {
        return timestart;
    }

    public void setTimestart(String timestart) {
        this.timestart = timestart;
    }

    public String getTimeend() {
        return timeend;
    }

    public void setTimeend(String timeend) {
        this.timeend = timeend;
    }

    public String getLoaihinh() {
        return loaihinh;
    }

    public void setLoaihinh(String loaihinh) {
        this.loaihinh = loaihinh;
    }

    public int getIdloaihinh() {
        return idloaihinh;
    }

    public void setIdloaihinh(int idloaihinh) {
        this.idloaihinh = idloaihinh;
    }

    public PlaceAdd() {

    }

    public String getIdplace() {
        return idplace;
    }

    public void setIdplace(String idplace) {
        this.idplace = idplace;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImageavata() {
        return imageavata;
    }

    public void setImageavata(String imageavata) {
        this.imageavata = imageavata;
    }

    public String getImageavata11() {
        return imageavata11;
    }

    public void setImageavata11(String imageavata11) {
        this.imageavata11 = imageavata11;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDacdiem() {
        return dacdiem;
    }

    public void setDacdiem(String dacdiem) {
        this.dacdiem = dacdiem;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPlacedetail() {
        return placedetail;
    }

    public void setPlacedetail(String placedetail) {
        this.placedetail = placedetail;
    }



}
