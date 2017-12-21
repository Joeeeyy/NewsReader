package com.sincha2aexample.josephjoey.newsreader.model;

/**
 * Created by JosephJoey on 8/20/2017.
 */

public class NewsItem {

    public String author;
    public String title;
    public String desc;
    public String url;
    public String utlToImage;
    public String publishedAt;

    public NewsItem(String author, String title, String desc, String url, String utlToImage, String publishedAt) {
        this.author = author;
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.utlToImage = utlToImage;
        this.publishedAt = publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUtlToImage() {
        return utlToImage;
    }

    public void setUtlToImage(String utlToImage) {
        this.utlToImage = utlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
