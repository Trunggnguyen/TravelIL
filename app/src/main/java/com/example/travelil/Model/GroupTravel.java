package com.example.travelil.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupTravel implements Serializable {
    String name;
    String image;
    String date;
    String travel;
    String createBy;
    boolean trangthai;
    ArrayList<String> users;
    boolean end;


    public GroupTravel() {
    }

    public GroupTravel(String name, String image, String date, String travel, String createBy, boolean trangthai, ArrayList<String> users, boolean end) {
        this.name = name;
        this.image = image;
        this.date = date;
        this.travel = travel;
        this.createBy = createBy;
        this.trangthai = trangthai;
        this.users = users;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public boolean isTrangthai() {
        return trangthai;
    }

    public void setTrangthai(boolean trangthai) {
        this.trangthai = trangthai;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
}
