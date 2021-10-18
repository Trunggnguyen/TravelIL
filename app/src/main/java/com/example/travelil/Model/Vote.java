package com.example.travelil.Model;

public class Vote {
    private  String comment;
    private String pulisher ;
    private float vote;


    public Vote(String comment, String pulisher, float vote) {
        this.comment = comment;
        this.pulisher = pulisher;
        this.vote = vote;
    }

    public Vote() {
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

    public float getVote() {
        return vote;
    }

    public void setVote(float vote) {
        this.vote = vote;
    }
}
