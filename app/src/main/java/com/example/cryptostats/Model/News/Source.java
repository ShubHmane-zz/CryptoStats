package com.example.cryptostats.Model.News;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Source {

    @SerializedName("name")
    @Expose
    private final String name;


    public String getName() {
        return name;
    }

    public Source(String name) {
        this.name = name;
    }
}
