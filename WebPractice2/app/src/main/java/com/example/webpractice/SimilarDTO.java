package com.example.webpractice;

public class SimilarDTO {
    private String title;
    private String Scompany;
    private String price;
    private String similarUrl;

    public SimilarDTO() {
        this.title = title;
        this.price = price;
        this.Scompany = Scompany;
        this.similarUrl = similarUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setScompany(String Scompany) {
        this.Scompany = Scompany;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setSimilarUrl(String similarUrl) {
        this.similarUrl = similarUrl;
    }


    public String getTitle() { return title; }

    public String getPrice() {
        return price;
    }

    public String getScompany() {
        return Scompany;
    }

    public String getSimilarUrl() { return similarUrl;    }
}
