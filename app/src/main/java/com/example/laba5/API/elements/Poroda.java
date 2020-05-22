package com.example.laba5.API.elements;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Poroda {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public String getPoroda() {
        return name;
    }
}
