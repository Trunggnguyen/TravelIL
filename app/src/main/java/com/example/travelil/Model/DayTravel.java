package com.example.travelil.Model;

import java.io.Serializable;

public class DayTravel implements Serializable {
    Boolean day;

    public DayTravel() {
    }

    public DayTravel(Boolean day) {
        this.day = day;
    }

    public Boolean getDay() {
        return day;
    }

    public void setDay(Boolean day) {
        this.day = day;
    }
}
