package com.example.laba5.API.elements;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeDislike {
    @SerializedName("id")
    private int id;

    public LikeDislike(int vote_id) {
        this.id = vote_id;
    }

    public int getVote_id() {
        return id;
    }
}
