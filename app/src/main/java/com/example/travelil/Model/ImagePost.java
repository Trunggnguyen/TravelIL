package com.example.travelil.Model;

public class ImagePost {

    String postimage;
    String keytrgr;
    String keytrday;

    public ImagePost() {
    }

    public ImagePost(String postimage, String keytrgr, String keytrday) {
        this.postimage = postimage;
        this.keytrgr = keytrgr;
        this.keytrday = keytrday;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getKeytrgr() {
        return keytrgr;
    }

    public void setKeytrgr(String keytrgr) {
        this.keytrgr = keytrgr;
    }

    public String getKeytrday() {
        return keytrday;
    }

    public void setKeytrday(String keytrday) {
        this.keytrday = keytrday;
    }
}
