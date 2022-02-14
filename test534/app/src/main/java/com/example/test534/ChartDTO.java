package com.example.test534;

public class ChartDTO {

    private String title;
    private String name;
    private String rankNum;
    private String imageUrl;
    private String price;

    public ChartDTO() {
        this.title = title;
        this.name = name;
        this.rankNum = rankNum;
        this.imageUrl = imageUrl;
        this.price = price;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


}