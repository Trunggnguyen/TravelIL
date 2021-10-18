package com.example.travelil.Model;

public class Choice {
    private String image;
    private  String text;

    public Choice(String image, String text) {
        this.image = image;
        this.text = text;
    }

    public Choice() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
