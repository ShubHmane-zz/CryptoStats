package com.example.cryptostats.Model.News;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("author")
    @Expose
    private final String author;
    @SerializedName("title")
    @Expose
    private final String title;
    @SerializedName("description")
    @Expose
    private final String description;
    @SerializedName("urlToImage")
    @Expose
    private final String urlToImage;
    @SerializedName("publishedAt")
    @Expose
    private final String publishedAt;

    @SerializedName("source")
    private final Source source;

    public NewsModel(String author, String title, String description, String urlToImage, String publishedAt, Source source) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getUrlToImage() {
        return urlToImage;
    }



    public String getPublishedAt() {
        return publishedAt;
    }



    public Source getSource() {
        return source;
    }


}
