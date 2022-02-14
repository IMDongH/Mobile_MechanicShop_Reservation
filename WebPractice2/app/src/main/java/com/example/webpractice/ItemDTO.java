package com.example.webpractice;

public class ItemDTO {
    private String title;
    private String price;
    private String company;
    private String hashTag;
    private String imageUrl;


    public ItemDTO() {
        this.title = title;
        this.price = price;
        this.company = company;
        this.hashTag = hashTag;
        this.imageUrl = imageUrl;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCompany(String company) {
        this.price = company;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getTitle() { return title; }

    public String getPrice() {
        return price;
    }

    public String getCompany() {
        return company;
    }

    public String getHashTag() {
        return hashTag;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
