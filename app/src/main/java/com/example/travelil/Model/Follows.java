package com.example.travelil.Model;

public class Follows {
    String following, follower;

    public Follows() {
    }

    public Follows(String following, String follower) {
        this.following = following;
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }
}
