package com.example.laba5.API.elements;

import com.google.gson.annotations.SerializedName;

public class PolucheniePosta {
    @SerializedName("id")
    private int id;

    @SerializedName("image_id")
    private String image_id;

    @SerializedName("value")
    private int value;

    public int getId() {
        return id;
    }

    public String getImageId() {
        return image_id;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "PostGet{" +
                "id= " + id +
                ", image_id= '" + image_id + '\'' +
                ", value= " + value +
                '}';
    }
}
