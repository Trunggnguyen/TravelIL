package com.example.travelil.Model;

public class Notification {
    private String userid;
    private String text;
    private String grid;
    private boolean isGr;
    private boolean isNotifire;
    private boolean isSeen;

    public Notification(String userid, String text, String grid, boolean isGr, boolean isNotifire, boolean isSeen) {
        this.userid = userid;
        this.text = text;
        this.grid = grid;
        this.isGr = isGr;
        this.isNotifire = isNotifire;
        this.isSeen = isSeen;
    }

    public Notification() {
    }

    public boolean isNotifire() {
        return isNotifire;
    }

    public void setNotifire(boolean notifire) {
        isNotifire = notifire;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public boolean isGr() {
        return isGr;
    }

    public void setGr(boolean gr) {
        isGr = gr;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
