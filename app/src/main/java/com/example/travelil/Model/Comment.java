package com.example.travelil.Model;

public class Comment {
    private  String comment;
    private String pulisher ;
    private String idcmt;

    public Comment(String comment, String pulisher, String idcmt) {
        this.comment = comment;
        this.pulisher = pulisher;
        this.idcmt = idcmt;
    }

    public Comment() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPulisher() {
        return pulisher;
    }

    public void setPulisher(String pulisher) {
        this.pulisher = pulisher;
    }

    public String getIdcmt() {
        return idcmt;
    }

    public void setIdcmt(String idcmt) {
        this.idcmt = idcmt;
    }
}
