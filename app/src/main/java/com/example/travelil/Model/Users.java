package com.example.travelil.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Users implements Serializable, Parcelable {
    String uid;
    String name;
    String email;
    String imageURI;
    String status;

    public Users() {
    }

    public Users(String uid, String name, String email, String imageURI, String status) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.imageURI = imageURI;
        this.status = status;
    }

    protected Users(Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
        imageURI = in.readString();
        status = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public Object[] toObject(){
        return new Object[]{
         name,
         email,
         imageURI,
         status
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(imageURI);
        dest.writeString(status);
    }
}
