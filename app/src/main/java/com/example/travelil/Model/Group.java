package com.example.travelil.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    String name;
    String image;
    String createBy;
    ArrayList<Users> users;

    public Group(String name, String image, String createBy, ArrayList<Users> users) {
        this.name = name;
        this.image = image;
        this.createBy = createBy;
        this.users = users;
    }

    public Group() {
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }
}
