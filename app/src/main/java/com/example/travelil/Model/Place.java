package com.example.travelil.Model;

public class Place {
    private  String located;
    private  String name;
    private  String imagebanner;
    private  String imageicon;
    private  String title;
    private  String post;

    public Place(String located, String name, String imagebanner, String imageicon, String title, String post) {
        this.located = located;
        this.name = name;
        this.imagebanner = imagebanner;
        this.imageicon = imageicon;
        this.title = title;
        this.post = post;
    }

    public Place() {
    }

    public String getLocated() {
        return located;
    }

    public void setLocated(String located) {
        this.located = located;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagebanner() {
        return imagebanner;
    }

    public void setImagebanner(String imagebanner) {
        this.imagebanner = imagebanner;
    }

    public String getImageicon() {
        return imageicon;
    }

    public void setImageicon(String imageicon) {
        this.imageicon = imageicon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
